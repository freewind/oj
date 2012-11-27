/*
 * ParseTreeVisitor.java
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

import openjava.ptree.AllocationExpression;
import openjava.ptree.ArrayAccess;
import openjava.ptree.ArrayAllocationExpression;
import openjava.ptree.ArrayInitializer;
import openjava.ptree.AssignmentExpression;
import openjava.ptree.BinaryExpression;
import openjava.ptree.Block;
import openjava.ptree.BreakStatement;
import openjava.ptree.CaseGroup;
import openjava.ptree.CaseGroupList;
import openjava.ptree.CaseLabel;
import openjava.ptree.CaseLabelList;
import openjava.ptree.CastExpression;
import openjava.ptree.CatchBlock;
import openjava.ptree.CatchList;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ClassDeclarationList;
import openjava.ptree.ClassLiteral;
import openjava.ptree.CompilationUnit;
import openjava.ptree.ConditionalExpression;
import openjava.ptree.ConstructorDeclaration;
import openjava.ptree.ConstructorInvocation;
import openjava.ptree.ContinueStatement;
import openjava.ptree.DoWhileStatement;
import openjava.ptree.EmptyStatement;
import openjava.ptree.Expression;
import openjava.ptree.ExpressionList;
import openjava.ptree.ExpressionStatement;
import openjava.ptree.FieldAccess;
import openjava.ptree.FieldDeclaration;
import openjava.ptree.ForStatement;
import openjava.ptree.IfStatement;
import openjava.ptree.InstanceofExpression;
import openjava.ptree.LabeledStatement;
import openjava.ptree.Leaf;
import openjava.ptree.List;
import openjava.ptree.Literal;
import openjava.ptree.MemberDeclaration;
import openjava.ptree.MemberDeclarationList;
import openjava.ptree.MemberInitializer;
import openjava.ptree.MethodCall;
import openjava.ptree.MethodDeclaration;
import openjava.ptree.ModifierList;
import openjava.ptree.NonLeaf;
import openjava.ptree.Parameter;
import openjava.ptree.ParameterList;
import openjava.ptree.ParseTree;
import openjava.ptree.ParseTreeException;
import openjava.ptree.ParseTreeObject;
import openjava.ptree.ReturnStatement;
import openjava.ptree.SelfAccess;
import openjava.ptree.Statement;
import openjava.ptree.StatementList;
import openjava.ptree.SwitchStatement;
import openjava.ptree.SynchronizedStatement;
import openjava.ptree.ThrowStatement;
import openjava.ptree.TryStatement;
import openjava.ptree.TypeName;
import openjava.ptree.UnaryExpression;
import openjava.ptree.Variable;
import openjava.ptree.VariableDeclaration;
import openjava.ptree.VariableDeclarator;
import openjava.ptree.VariableInitializer;
import openjava.ptree.WhileStatement;

/**
 * The class <code>ParseTreeVisitor</code> is a Visitor role
 * in the Visitor pattern and visits <code>ParseTree</code> objects
 * as the role of Element.
 * <p>
 * For example
 * <pre>
 * </pre>
 * <p>
 *
 * @author   Michiaki Tatsubori
 * @version  1.0
 * @since    $Id: ParseTreeVisitor.java,v 1.2 2003/02/19 02:55:00 tatsubori Exp $
 * @see openjava.ptree.ParseTree
 */
public abstract class ParseTreeVisitor {

	public void visit(ParseTree p) throws ParseTreeException {
		p.accept(this);
	}
	public void visit(ParseTreeObject p) throws ParseTreeException {
		p.accept(this);
	}
	public void visit(NonLeaf p) throws ParseTreeException {
		p.accept(this);
	}
	public void visit(Leaf p) throws ParseTreeException {
		p.accept(this);
	}
	public void visit(MemberDeclaration p) throws ParseTreeException {
		p.accept(this);
	}
	public void visit(Statement p) throws ParseTreeException {
		p.accept(this);
	}
	public void visit(Expression p) throws ParseTreeException {
		p.accept(this);
	}
	public void visit(VariableInitializer p) throws ParseTreeException {
		p.accept(this);
	}
	public void visit(List p) throws ParseTreeException {
		p.accept(this);
	}

	public abstract void visit(AllocationExpression p)
		throws ParseTreeException;
	public abstract void visit(ArrayAccess p) throws ParseTreeException;
	public abstract void visit(ArrayAllocationExpression p)
		throws ParseTreeException;
	public abstract void visit(ArrayInitializer p) throws ParseTreeException;
	public abstract void visit(AssignmentExpression p)
		throws ParseTreeException;
	public abstract void visit(BinaryExpression p) throws ParseTreeException;
	public abstract void visit(Block p) throws ParseTreeException;
	public abstract void visit(BreakStatement p) throws ParseTreeException;
	public abstract void visit(CaseGroup p) throws ParseTreeException;
	public abstract void visit(CaseGroupList p) throws ParseTreeException;
	public abstract void visit(CaseLabel p) throws ParseTreeException;
	public abstract void visit(CaseLabelList p) throws ParseTreeException;
	public abstract void visit(CastExpression p) throws ParseTreeException;
	public abstract void visit(CatchBlock p) throws ParseTreeException;
	public abstract void visit(CatchList p) throws ParseTreeException;
	public abstract void visit(ClassDeclaration p) throws ParseTreeException;
	public abstract void visit(ClassDeclarationList p)
		throws ParseTreeException;
	public abstract void visit(ClassLiteral p) throws ParseTreeException;
	public abstract void visit(CompilationUnit p) throws ParseTreeException;
	public abstract void visit(ConditionalExpression p)
		throws ParseTreeException;
	public abstract void visit(ConstructorDeclaration p)
		throws ParseTreeException;
	public abstract void visit(ConstructorInvocation p)
		throws ParseTreeException;
	public abstract void visit(ContinueStatement p) throws ParseTreeException;
	public abstract void visit(DoWhileStatement p) throws ParseTreeException;
	public abstract void visit(EmptyStatement p) throws ParseTreeException;
	public abstract void visit(ExpressionList p) throws ParseTreeException;
	public abstract void visit(ExpressionStatement p)
		throws ParseTreeException;
	public abstract void visit(FieldAccess p) throws ParseTreeException;
	public abstract void visit(FieldDeclaration p) throws ParseTreeException;
	public abstract void visit(ForStatement p) throws ParseTreeException;
	public abstract void visit(IfStatement p) throws ParseTreeException;
	public abstract void visit(InstanceofExpression p)
		throws ParseTreeException;
	public abstract void visit(LabeledStatement p) throws ParseTreeException;
	public abstract void visit(Literal p) throws ParseTreeException;
	public abstract void visit(MemberDeclarationList p)
		throws ParseTreeException;
	public abstract void visit(MemberInitializer p) throws ParseTreeException;
	public abstract void visit(MethodCall p) throws ParseTreeException;
	public abstract void visit(MethodDeclaration p) throws ParseTreeException;
	public abstract void visit(ModifierList p) throws ParseTreeException;
	public abstract void visit(Parameter p) throws ParseTreeException;
	public abstract void visit(ParameterList p) throws ParseTreeException;
	public abstract void visit(ReturnStatement p) throws ParseTreeException;
	public abstract void visit(SelfAccess p) throws ParseTreeException;
	public abstract void visit(StatementList p) throws ParseTreeException;
	public abstract void visit(SwitchStatement p) throws ParseTreeException;
	public abstract void visit(SynchronizedStatement p)
		throws ParseTreeException;
	public abstract void visit(ThrowStatement p) throws ParseTreeException;
	public abstract void visit(TryStatement p) throws ParseTreeException;
	public abstract void visit(TypeName p) throws ParseTreeException;
	public abstract void visit(UnaryExpression p) throws ParseTreeException;
	public abstract void visit(Variable p) throws ParseTreeException;
	public abstract void visit(VariableDeclaration p)
		throws ParseTreeException;
	public abstract void visit(VariableDeclarator p) throws ParseTreeException;
	public abstract void visit(WhileStatement p) throws ParseTreeException;

}
