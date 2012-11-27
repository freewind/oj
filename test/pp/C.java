package pp;

import openjava.mop.Environment;
import openjava.mop.MOPException;
import openjava.mop.MetaInfo;
import openjava.mop.OJClass;
import openjava.mop.OJMethod;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.MethodDeclaration;
import openjava.ptree.ModifierList;
import openjava.ptree.Parameter;
import openjava.ptree.ParameterList;
import openjava.ptree.StatementList;
import openjava.ptree.TypeName;

public class C extends OJClass {

	public void translateDefinition() throws MOPException {
		ModifierList ml;
		TypeName tn;
		ParameterList pl;
		StatementList sl;
		Environment env;
		MethodDeclaration md;

		ml = new ModifierList();
		tn = new TypeName("boolean");
		pl = new ParameterList(new Parameter(new TypeName("int"), "ind"));

		env = getEnvironment();
		sl = makeStatementList(env, "System.out.println(ind); return true;");

		md = new MethodDeclaration(ml, tn, "myMeth", pl, null, sl);
		addMethod(new OJMethod(getEnvironment(), getDeclaringClass(), md));
	}

	public C(Environment p0, OJClass p1, ClassDeclaration p2) {
		super(p0, p1, p2);
	}

	public C(Class p0, MetaInfo p1) {
		super(p0, p1);
	}
}
