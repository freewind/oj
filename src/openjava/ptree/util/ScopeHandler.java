/*
 * ScopeHandler.java
 *
 * comments here.
 *
 * @author   Michiaki Tatsubori
 * @version  %VERSION% %DATE%
 * @see      java.lang.Object
 *
 * COPYRIGHT 1998 by Michiaki Tatsubori, ALL RIGHTS RESERVED.
 */
package openjava.ptree.util;

import java.util.Stack;

import openjava.mop.AnonymousClassEnvironment;
import openjava.mop.ClassEnvironment;
import openjava.mop.ClosedEnvironment;
import openjava.mop.Environment;
import openjava.mop.FileEnvironment;
import openjava.mop.OJClass;
import openjava.ptree.AllocationExpression;
import openjava.ptree.Block;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;
import openjava.ptree.ConstructorDeclaration;
import openjava.ptree.DoWhileStatement;
import openjava.ptree.Expression;
import openjava.ptree.ForStatement;
import openjava.ptree.IfStatement;
import openjava.ptree.MemberDeclaration;
import openjava.ptree.MemberDeclarationList;
import openjava.ptree.MemberInitializer;
import openjava.ptree.MethodDeclaration;
import openjava.ptree.ParseTreeException;
import openjava.ptree.Statement;
import openjava.ptree.SwitchStatement;
import openjava.ptree.SynchronizedStatement;
import openjava.ptree.TryStatement;
import openjava.ptree.WhileStatement;

/**
 * The class <code>ScopeHandler</code>
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: ScopeHandler.java,v 1.2 2003/02/19 02:55:00 tatsubori Exp $
 * @see openjava.ptree.ParseTree
 * @see openjava.ptree.util.ParseTreeVisitor
 * @see openjava.ptree.util.EvaluationShuttle
 */
public abstract class ScopeHandler extends EvaluationShuttle {
	private Stack env_nest = new Stack();

	public ScopeHandler(Environment base_env) {
		super(base_env);
	}

	protected final void pushClosedEnvironment() {
		push(new ClosedEnvironment(getEnvironment()));
	}

	protected final void push(Environment env) {
		env_nest.push(getEnvironment());
		setEnvironment(env);
	}

	protected final void pop() {
		setEnvironment((Environment) env_nest.pop());
	}

	/* in walking down through parse tree */

	/* compilation unit */
	public CompilationUnit evaluateDown(CompilationUnit ptree)
		throws ParseTreeException {
		ClassDeclaration pubclazz = ptree.getPublicClass();
		String name =
			(pubclazz != null) ? pubclazz.getName() : "<no public class>";
		FileEnvironment fenv =
			new FileEnvironment(getEnvironment(), ptree, name);

		push(fenv);

		return ptree;
	}

	/* class declaration */
	public ClassDeclaration evaluateDown(ClassDeclaration ptree)
		throws ParseTreeException {
		/* records this class */
		if (getEnvironment() instanceof ClosedEnvironment) {
			recordLocalClass(ptree);
		}

		/* creates a new class environment */
		ClassEnvironment env =
			new ClassEnvironment(getEnvironment(), ptree.getName());
		MemberDeclarationList mdecls = ptree.getBody();
		for (int i = 0; i < mdecls.size(); ++i) {
			MemberDeclaration m = mdecls.get(i);
			if (!(m instanceof ClassDeclaration))
				continue;
			ClassDeclaration inner = (ClassDeclaration) m;
			env.recordMemberClass(inner.getName());
		}
		push(env);

		return ptree;
	}

	private void recordLocalClass(ClassDeclaration ptree) {
		String classname = ptree.getName();
		Environment outer_env = getEnvironment();
		String qname = outer_env.toQualifiedName(classname);
		if (outer_env.lookupClass(qname) != null)
			return;
		try {
			OJClass out_clazz =
				outer_env.lookupClass(outer_env.currentClassName());
			/***** this will be recorded in global env */
			//OJClass clazz = OJClass.forParseTree(outer_env, out_clazz, ptree);
			OJClass clazz = new OJClass(outer_env, out_clazz, ptree);
			outer_env.record(classname, clazz);
		} catch (Exception ex) {
			System.err.println("unknown error: " + ex);
			return;
		}
	}

	/* class body contents */
	public MemberDeclaration evaluateDown(MethodDeclaration ptree)
		throws ParseTreeException {
		pushClosedEnvironment();
		return ptree;
	}

	public MemberDeclaration evaluateDown(ConstructorDeclaration ptree)
		throws ParseTreeException {
		pushClosedEnvironment();
		return ptree;
	}

	public MemberDeclaration evaluateDown(MemberInitializer ptree)
		throws ParseTreeException {
		pushClosedEnvironment();
		return ptree;
	}

	/* statements */
	public Statement evaluateDown(Block ptree) throws ParseTreeException {
		pushClosedEnvironment();
		return ptree;
	}

	public Statement evaluateDown(SwitchStatement ptree)
		throws ParseTreeException {
		pushClosedEnvironment();
		return ptree;
	}

	public Statement evaluateDown(IfStatement ptree)
		throws ParseTreeException {
		pushClosedEnvironment();
		return ptree;
	}

	public Statement evaluateDown(WhileStatement ptree)
		throws ParseTreeException {
		pushClosedEnvironment();
		return ptree;
	}

	public Statement evaluateDown(DoWhileStatement ptree)
		throws ParseTreeException {
		pushClosedEnvironment();
		return ptree;
	}

	public Statement evaluateDown(ForStatement ptree)
		throws ParseTreeException {
		pushClosedEnvironment();
		return ptree;
	}

	public Statement evaluateDown(TryStatement ptree)
		throws ParseTreeException {
		pushClosedEnvironment();
		return ptree;
	}

	public Statement evaluateDown(SynchronizedStatement ptree)
		throws ParseTreeException {
		pushClosedEnvironment();
		return ptree;
	}

	public Expression evaluateDown(AllocationExpression ptree)
		throws ParseTreeException {
		MemberDeclarationList cbody = ptree.getClassBody();
		if (cbody != null) {
			String baseName = ptree.getClassType().toString();
			push(
				new AnonymousClassEnvironment(
					getEnvironment(),
					baseName,
					cbody));
		} else {
			pushClosedEnvironment();
		}
		return ptree;
	}

	/* in walking down through parse tree */

	/* class declaration */
	public CompilationUnit evaluateUp(CompilationUnit ptree)
		throws ParseTreeException {
		pop();
		return ptree;
	}

	/* class declaration */
	public ClassDeclaration evaluateUp(ClassDeclaration ptree)
		throws ParseTreeException {
		pop();
		return ptree;
	}

	/* class body contents */
	public MemberDeclaration evaluateUp(MethodDeclaration ptree)
		throws ParseTreeException {
		pop();
		return ptree;
	}

	public MemberDeclaration evaluateUp(ConstructorDeclaration ptree)
		throws ParseTreeException {
		pop();
		return ptree;
	}

	public MemberDeclaration evaluateUp(MemberInitializer ptree)
		throws ParseTreeException {
		pop();
		return ptree;
	}

	/* statements */
	public Statement evaluateUp(Block ptree) throws ParseTreeException {
		pop();
		return ptree;
	}

	public Statement evaluateUp(SwitchStatement ptree)
		throws ParseTreeException {
		pop();
		return ptree;
	}

	public Statement evaluateUp(IfStatement ptree) throws ParseTreeException {
		pop();
		return ptree;
	}

	public Statement evaluateUp(WhileStatement ptree)
		throws ParseTreeException {
		pop();
		return ptree;
	}

	public Statement evaluateUp(DoWhileStatement ptree)
		throws ParseTreeException {
		pop();
		return ptree;
	}

	public Statement evaluateUp(ForStatement ptree) throws ParseTreeException {
		pop();
		return ptree;
	}

	public Statement evaluateUp(TryStatement ptree) throws ParseTreeException {
		pop();
		return ptree;
	}

	public Statement evaluateUp(SynchronizedStatement ptree)
		throws ParseTreeException {
		pop();
		return ptree;
	}

	public Expression evaluateUp(AllocationExpression ptree)
		throws ParseTreeException {
		pop();
		return ptree;
	}

}
