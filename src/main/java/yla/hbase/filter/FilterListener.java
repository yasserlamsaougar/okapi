// Generated from C:/Users/ylamsaou/IdeaProjects/minicluster-service/src/main/java\Filter.g4 by ANTLR 4.7
package yla.hbase.filter;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FilterParser}.
 */
public interface FilterListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FilterParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(FilterParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FilterParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(FilterParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FilterParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterOperator(FilterParser.OperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link FilterParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitOperator(FilterParser.OperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link FilterParser#literal_value}.
	 * @param ctx the parse tree
	 */
	void enterLiteral_value(FilterParser.Literal_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link FilterParser#literal_value}.
	 * @param ctx the parse tree
	 */
	void exitLiteral_value(FilterParser.Literal_valueContext ctx);
}