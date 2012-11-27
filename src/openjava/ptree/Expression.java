/*
 * Expression.java 1.0
 *
 *
 * Jun 20, 1997
 * Sep 29, 1997
 *
 * @see openjava.ptree.ParseTree
 * @version 1.0 last updated:  Sep 29, 1997
 * @author  Teruo -bv- Koyanagi
 */
package openjava.ptree;


import openjava.mop.Environment;
import openjava.mop.OJClass;



/**
 * The Expression interface presents common interface
 * to access Expression node of parse tree
 *
 * this interface is implements by
 * <pre>
 *   UnaryExpression
 *   BinaryExpression
 *   ConditionalExpression
 *   AssignmentExpression
 *   CastExpression
 *   AllocationExpression
 *   ArrayAllocationExpression
 *   Variable
 *   MethodCall
 *   SpecialName
 *   Literal
 *   ClassLiteral
 *   ArrayAccess
 *   FieldAccess
 * </pre>
 *
 * @see openjava.ptree.ParseTree
 * @see openjava.ptree.NonLeaf
 * @see openjava.ptree.VariableInitializer
 */
public interface Expression extends ParseTree, VariableInitializer
{
    public OJClass getType( Environment env )
	throws Exception;
}
