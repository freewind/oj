package demobug;

import openjava.mop.Environment;
import openjava.mop.MOPException;
import openjava.mop.MetaInfo;
import openjava.mop.OJClass;
import openjava.mop.OJMethod;
import openjava.mop.OJModifier;
import openjava.mop.OJSystem;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.StatementList;

public class DemoBug1 extends OJClass {

	public void translateDefinition() throws MOPException {

		// Must load some classes
		String[] toFind = new String[] { "Bug1", "Bug1$Bug2", "Bug1.Bug2" };
		for (int i = 0; i < toFind.length; ++i) {
			try {
				forName("examples.test." + toFind[i]);
			} catch (openjava.mop.OJClassNotFoundException ex) {
				System.err.println(toFind[i] + " not found !");
			}
		}
		String body =
			"{\n"
				+ "\t int i=Bug1.Bug2.bug;"
				+ "\t Integer I=new Integer(i);\n"
				+ "\t System.out.println(\"Here's the famous value : \"+I);\n"
				+ "}\n";

		OJMethod buggy =
			new OJMethod(
				this,
				OJModifier.forModifier(OJModifier.PUBLIC),
				OJSystem.VOID,
				"Buggy",
				new OJClass[] {
		}, null, null);
		StatementList StBody =
			new StatementList(makeStatement(buggy.getEnvironment(), body));
		buggy.setBody(StBody);
		addMethod(buggy);

	}

	public DemoBug1(
		Environment outer_env,
		OJClass declarer,
		ClassDeclaration ptree) {
		super(outer_env, declarer, ptree);
	}

	public DemoBug1(Class javaclass, MetaInfo minfo) {
		super(javaclass, minfo);
	}
};
