/*
 * Compiler.java
 * 
 *  
 */
package openjava.ojc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

import openjava.mop.Environment;
import openjava.mop.FileEnvironment;
import openjava.mop.MetaInfo;
import openjava.mop.OJClass;
import openjava.mop.OJClassNotFoundException;
import openjava.mop.OJSystem;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ClassDeclarationList;
import openjava.ptree.CompilationUnit;
import openjava.ptree.ParseTreeException;
import openjava.ptree.util.ExpansionApplier;
import openjava.ptree.util.MemberAccessCorrector;
import openjava.ptree.util.SourceCodeWriter;
import openjava.ptree.util.TypeNameQualifier;
import openjava.tools.DebugOut;
import openjava.tools.parser.ParseException;
import openjava.tools.parser.Parser;

public class Compiler {
    final CommandArguments arguments;
    final File files[];
    final FileEnvironment[] file_env;
    final CompilationUnit[] comp_unit;

    JavaCompiler java_compiler;
    CompilationUnit[] added_cu = null;

    Compiler(CommandArguments arguments) {
        super();
        this.arguments = arguments;
        this.files = arguments.getFiles();
        this.file_env = new FileEnvironment[files.length];
        this.comp_unit = new CompilationUnit[files.length];

        DebugOut.setDebugLevel(arguments.getDebugLevel());
        initPrimitiveTypes();
        try {
            this.java_compiler = arguments.getJavaCompiler();
            OJSystem.setJavaCompiler(this.java_compiler);
        } catch (Exception e) {
            System.err.println("illegal java compiler : " + e);
        }
    }

    public void run() {
        try {
            configSpecifiedMetaBind();
        } catch (Exception e) {
            System.err.println(e);
        }

        initFileEnv();

        System.err.println("Generating parse tree.");
        generateParseTree();
        System.err.println("..done.");

        System.err.println("Initializing parse tree.");
        initParseTree();
        System.err.println("..done.");

        System.err.println("Translating callee side");
        translateCalleeSide();
        System.err.println("..done.");

        System.err.println("Translating caller side");
        if (arguments.callerTranslation()) {
            translateCallerSide();
            System.err.println("..done.");
        } else {
            System.err.println("..skipped.");
        }

        generateAdditionalCompilationUnit();

        System.err.println("Printing parse tree.");
        outputToFile();
        System.err.println("..done.");

        System.err.println("Compiling into bytecode.");
        javac();
        System.err.println("..done.");

        System.err.flush();
    }

    void configSpecifiedMetaBind() throws Exception {
        String[] cfile = arguments.getOptions("-default-meta");
        for (int i = 0; i < cfile.length; ++i) {
            BufferedReader reader =
                new BufferedReader(new FileReader(cfile[i]));
            String line;
            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                try {
                    String mclazz = tokenizer.nextToken();
                    String bclazz = tokenizer.nextToken();
                    OJSystem.metabind(bclazz, mclazz);
                } catch (java.util.NoSuchElementException e) {
                    /** ignore this line */
                }
            }
            reader.close();
        }
    }

    void initFileEnv() {
        for (int i = 0; i < files.length; ++i) {
            file_env[i] =
                new FileEnvironment(OJSystem.env, null, null, files[i]);
        }
    }

    void generateParseTree() {
        for (int i = 0; i < files.length; ++i) {
            DebugOut.println("parsing file " + files[i]);
            try {
                comp_unit[i] = parse(files[i]);
                file_env[i].setCompilationUnit(comp_unit[i]);

                ClassDeclarationList typedecls =
                    comp_unit[i].getClassDeclarations();
                for (int j = 0; j < typedecls.size(); ++j) {
                    ClassDeclaration clazz_decl = typedecls.get(j);
                    /***********************************************************
					 * this may be ambiguos. there's OJClass.forParseTree()
					 */
                    OJClass c = makeOJClass(file_env[i], clazz_decl);
                    if (c.getModifiers().isPublic()) {
                        /* public/main class */
                        DebugOut.println("main class " + c.getName());
                    } else {
                        /* non-public class */
                        DebugOut.println("local class " + c.getName());
                    }
                    OJSystem.env.record(c.getName(), c);
                    /** * should consider private * */
                    recordInnerClasses(c);
                }
            } catch (Exception ex) {
                System.err.println("errors during parsing. " + ex);
                ex.printStackTrace();
            }
            DebugOut.println("file environment : ");
            DebugOut.println(file_env[i]);
        }

        DebugOut.println("global environment : ");
        DebugOut.println(OJSystem.env);
    }

    private static void recordInnerClasses(OJClass c) {
        OJClass[] inners = c.getDeclaredClasses();
        for (int i = 0; i < inners.length; ++i) {
            OJSystem.env.record(inners[i].getName(), inners[i]);
            recordInnerClasses(inners[i]);
        }
    }

    void generateAdditionalCompilationUnit() {
        OJClass[] added = OJSystem.addedClasses();
        this.added_cu = new CompilationUnit[added.length];
        for (int i = 0; i < added.length; ++i) {
            ClassDeclarationList cdecls;
            try {
                cdecls = new ClassDeclarationList(added[i].getSourceCode());
            } catch (Exception e) {
                System.err.println("errors during generating " + added[i]);
                e.printStackTrace();
                continue;
            }
            String pack = added[i].getPackage();
            added_cu[i] = new CompilationUnit(pack, null, cdecls);
        }
    }

    /** -> to move to OJClass.forParseTree() * */
    private OJClass makeOJClass(Environment env, ClassDeclaration cdecl) {
        OJClass result;
        String qname = env.toQualifiedName(cdecl.getName());
        Class meta = OJSystem.getMetabind(qname);
        try {
            Constructor constr =
                meta.getConstructor(
                    new Class[] {
                        Environment.class,
                        OJClass.class,
                        ClassDeclaration.class });
            Object[] args = new Object[] { env, null, cdecl };
            result = (OJClass) constr.newInstance(args);
        } catch (Exception ex) {
            System.err.println(
                "errors during gererating a metaobject for " + qname);
            ex.printStackTrace();
            result = new OJClass(env, null, cdecl);
        }

        return result;
    }

    void initDebug() {
    }

    void outputToFile() {
        for (int i = 0; i < comp_unit.length; ++i) {
            if (comp_unit[i] == null)
                continue;

            File outfile = null;
            try {
                outfile =
                    getOutputFile(files[i], file_env[i], comp_unit[i], ".java");
                FileWriter fout = new FileWriter(outfile);
                PrintWriter out = new PrintWriter(fout);
                SourceCodeWriter writer = new SourceCodeWriter(out);
                writer.setDebugLevel(0);
                comp_unit[i].accept(writer);
                out.flush();
                out.close();
            } catch (IOException e) {
                System.err.println("fails to create " + outfile);
            } catch (ParseTreeException e) {
                System.err.println("errors during printing " + outfile);
                e.printStackTrace();
            }

            try {
                outfile =
                    getOutputFile(
                        files[i],
                        null,
                        comp_unit[i],
                        MetaInfo.SUFFIX + ".java");
                String qname = file_env[i].toQualifiedName(baseName(files[i]));
                OJClass clazz = OJClass.forName(qname);
                FileWriter fout = new FileWriter(outfile);
                clazz.writeMetaInfo(fout);
                fout.flush();
                fout.close();
            } catch (OJClassNotFoundException e) {
                //System.err.println("The class object not found for " +
                // outfile);
            } catch (IOException e) {
                System.err.println("fails to create " + outfile);
            } catch (ParseTreeException e) {
                System.err.println("errors during printing " + outfile);
                e.printStackTrace();
            }
        }

        for (int i = 0; i < added_cu.length; ++i) {
            File outfile = null;
            try {
                outfile = getOutputFile(null, null, added_cu[i], ".java");
                FileWriter fout = new FileWriter(outfile);
                PrintWriter out = new PrintWriter(fout);
                SourceCodeWriter writer = new SourceCodeWriter(out);
                writer.setDebugLevel(0);
                added_cu[i].accept(writer);
                out.flush();
                out.close();
            } catch (IOException e) {
                System.err.println("fails to create " + outfile);
            } catch (ParseTreeException e) {
                System.err.println("errors during printing " + outfile);
                e.printStackTrace();
            }
        }
    }

    private static final String class2path(String cname) {
        return cname.replace('.', File.separatorChar);
    }

    private static final String unknownClassName = "OJ_Unknown";
    private static int nonpubclassid = 0;
    private File getOutputFile(
        File fin,
        FileEnvironment file_env,
        CompilationUnit comp_unit,
        String suffix)
        throws ParseTreeException {
        String pack = comp_unit.getPackage();
        String sname;
        ClassDeclaration cd = comp_unit.getPublicClass();
        if (cd != null) {
            sname = cd.getName();
        } else if (file_env != null) {
            String path = file_env.getFile().getPath();
            int suffix_index = path.lastIndexOf('.');
            String javaPath = path.substring(0, suffix_index) + suffix;
            return new File(javaPath);
        } else {
            sname = unknownClassName + nonpubclassid++;
        }
        return getOutputFile(fin, pack, sname, suffix);
    }

    private File getOutputFile(
        File fin,
        String pack,
        String name,
        String suffix) {
        File dir; /* directory to put the file in */
        String distbase = arguments.getOption("d");
        if (distbase == null && fin != null) {
            /* the same directory where source file is */
            String dirname = fin.getParent();
            if (dirname == null)
                dirname = ".";
            dir = new File(dirname);
        } else {
            if (distbase == null)
                distbase = ".";
            File basedir = new File(distbase);
            if (pack == null || pack.equals("")) {
                dir = basedir;
            } else {
                dir = new File(basedir, class2path(pack));
                if ((!dir.isDirectory()) && (!dir.mkdirs())) {
                    System.err.println("fail to create dir : " + dir);
                    dir = new File(".");
                }
            }
        }
        return new File(dir, name + suffix);
    }

    private static String baseName(File file) {
        String name = file.getName();
        int index = name.lastIndexOf('.');
        if (index == -1)
            return name;
        return name.substring(0, index);
    }

    void initParseTree() {
        for (int i = 0; i < comp_unit.length; ++i) {
            if (comp_unit[i] == null) {
                System.err.println(files[i] + " is skipped.");
                continue;
            }
            try {
                if (arguments.callerTranslation()
                    && arguments.qualifyNameFirst()) {
                    comp_unit[i].accept(new TypeNameQualifier(file_env[i]));
                }
                MemberAccessCorrector corrector =
                    new MemberAccessCorrector(file_env[i]);
                comp_unit[i].accept(corrector);
            } catch (ParseTreeException e) {
                System.err.println("Encountered errors during analysis.");
                e.printStackTrace();
            }
        }
    }

    void translateCalleeSide() {
        Hashtable under_c = OJSystem.underConstruction;
        for (int i = 0; i < comp_unit.length; ++i) {
            if (comp_unit[i] == null) {
                System.err.println(files[i] + " is skipped.");
                continue;
            }
            ClassDeclarationList cdecls = comp_unit[i].getClassDeclarations();
            for (int j = 0; j < cdecls.size(); ++j) {
                ClassDeclaration cdecl = cdecls.get(j);
                String qname = file_env[i].toQualifiedName(cdecl.getName());
                OJClass clazz;
                try {
                    clazz = OJClass.forName(qname);
                } catch (OJClassNotFoundException ex) {
                    System.err.println("no " + qname + " : " + ex);
                    return;
                }
                translateClassDecls(under_c, clazz, file_env[i]);
            }
        }

        try {
            while (!under_c.isEmpty()) {
                OJClass clazz = (OJClass) under_c.keys().nextElement();
                resolveOrder(clazz);
            }
        } catch (InterruptedException e) {
            System.err.println("translation failed : " + e);
        }
    }

    private void translateClassDecls(
        Hashtable table,
        OJClass clazz,
        Environment env) {
        table.put(clazz, new TranslatorThread(env, clazz));

        OJClass[] inners = clazz.getDeclaredClasses();
        for (int i = 0; i < inners.length; ++i) {
            translateClassDecls(table, inners[i], clazz.getEnvironment());
        }
    }

    private void resolveOrder(OJClass clazz) throws InterruptedException {
        /* lock for the given class metaobject */
        Object lock = new Object();
        Hashtable under_c = OJSystem.underConstruction;

        synchronized (clazz) {
            OJSystem.orderingLock = lock;
            ((TranslatorThread) under_c.get(clazz)).start();

            /*
			 * wait until the translation of the class is suspended or
			 * finished.
			 */
            clazz.wait();
        }

        /*
		 * OJSystem.waited is set to null if the translation is finished.
		 * Otherwise the translation needs the translation of the class set in
		 * OJSystem.waited. It can be suspended for several times.
		 */
        while (OJSystem.waited != null) {
            resolveOrder(OJSystem.waited);

            synchronized (clazz) {
                OJSystem.orderingLock = lock;
                synchronized (lock) {
                    lock.notifyAll();
                }
                clazz.wait();
            }
        }

        under_c.remove(clazz);
    }

    void translateCallerSide() {
        for (int i = 0; i < comp_unit.length; ++i) {
            if (comp_unit[i] == null) {
                System.err.println(files[i] + " is skipped.");
                continue;
            }
            try {
                ExpansionApplier expander = new ExpansionApplier(file_env[i]);
                comp_unit[i].accept(expander);
            } catch (ParseTreeException e) {
                System.err.println(
                    "Encountered errors during translating caller side.");
                e.printStackTrace();
            }
        }
    }

    private static CompilationUnit parse(File file) {
        Parser parser;

        try {
            parser = new Parser(new java.io.FileInputStream(file));
        } catch (java.io.FileNotFoundException e) {
            System.err.println("File " + file + " not found.");
            return null;
        }

        CompilationUnit result;
        try {
            result = parser.CompilationUnit(OJSystem.env);
        } catch (ParseException e) {
            System.err.println("Encountered errors during parse.");
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    private static void initPrimitiveTypes() {
        OJSystem.initConstants();
        /*
		 * OJClass.forClass( boolean . class ); OJClass.forClass( byte . class );
		 * OJClass.forClass( char . class ); OJClass.forClass( short . class );
		 * OJClass.forClass( int . class ); OJClass.forClass( long . class );
		 * OJClass.forClass( float . class ); OJClass.forClass( double . class );
		 * OJClass.forClass( void . class );
		 */
    }

    /**
	 * Compiles the generated files into byte codes
	 */
    private void javac() {
        ArrayList argsList = new ArrayList();

        String[] options = arguments.getOptions("C");
        for (int i = 0; i < options.length; ++i) {
            argsList.add(options[i]);
        }

        try {
            for (int i = 0; i < files.length; ++i) {
                addCompiledFile(
                    argsList,
                    files[i],
                    file_env[i],
                    comp_unit[i],
                    ".java");
                addCompiledFile(
                    argsList,
                    files[i],
                    null,
                    comp_unit[i],
                    MetaInfo.SUFFIX + ".java");
            }
            for (int i = 0; i < added_cu.length; ++i) {
                addCompiledFile(argsList, files[i], null, added_cu[i], ".java");
                addCompiledFile(
                    argsList,
                    files[i],
                    null,
                    added_cu[i],
                    MetaInfo.SUFFIX + ".java");
            }

            String[] args = new String[argsList.size()];
            for (int i = 0; i < args.length; ++i) {
                args[i] = (String) argsList.get(i);
            }

            java_compiler.compile(args);
        } catch (Exception e) {
            System.err.println("errors during compiling into bytecode.");
            e.printStackTrace();
        }
    }

    private void addCompiledFile(
        ArrayList argsList,
        File file,
        FileEnvironment fenv,
        CompilationUnit cu,
        String suffix) {
        try {
            File javafile = getOutputFile(file, fenv, cu, suffix);
            if (javafile.exists())
                argsList.add(javafile.getPath());
        } catch (ParseTreeException ex) {
            System.err.println(ex.toString());
            return;
        }
    }
}
