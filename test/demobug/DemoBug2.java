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

public class DemoBug2 extends OJClass {

	public void translateDefinition() throws MOPException {

		String body =
			"{\n"
				+ "\t String Michi=\"My name is Tatsubori ... Michi Tatsubori\";\n"
				+ "\t System.out.println(\"Here's the famous sentence from 009 : \"+Michi);\n"
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

	public DemoBug2(
		Environment outer_env,
		OJClass declarer,
		ClassDeclaration ptree) {
		super(outer_env, declarer, ptree);
	}

	public DemoBug2(Class javaclass, MetaInfo minfo) {
		super(javaclass, minfo);
	}
}
