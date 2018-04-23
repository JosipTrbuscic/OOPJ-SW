package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple parser used for parsing string as a sequence of 
 * conditional expressions. Sequence is internally stored as
 * a list.
 * @author Josip Trbuscic
 */
public class QueryParser {
	/**
	 * Lexer used for lexicographical analysis of a query
	 */
	private QueryLexer lexer;

	/**
	 * List where conditional expressions of a query are stored
	 */
	private List<ConditionalExpression> queryList;
	
	/**
	 * Constructs new parser which will parse given 
	 * string as a query
	 * @param query - string representation of a query
	 */
	public QueryParser(String query) {
		if(query == null) throw new NullPointerException("Query cannot be null");
		lexer = new QueryLexer(query);
		queryList = new ArrayList<>();
		parseQuery();
	}
	
	/**
	 * Returns {@code true} if query is direct
	 * @return {@code true} if query is direct, 
	 * 			{@code false} otherwise
	 */
	public boolean isDirectQuery() {
		if(queryList.size() == 1 && 
				queryList.get(0).getFieldGetter() == FieldValueGetters.JMBAG &&
				queryList.get(0).getComparisonOperator() == ComparisonOperators.EQUALS ){
			return true;
		}
		return false;
	}
	
	/**
	 * Returns queried JMBAG if query is direct
	 * @return queried JMBAG
	 * @throws IllegalStateException if query is not direct
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) throw new IllegalStateException("Query is not direct");
		
		return queryList.get(0).getStringLiteral();
	}
	
	/**
	 * Returns query as a list of conditional
	 * expressions
	 * @return list of conditional expressions
	 */
	public List<ConditionalExpression> getQuery(){
		return queryList;
	}
	
	/**
	 * Parses the given query by grouping 
	 * conditional expressions and 
	 * storing them in a list
	 */
	private void parseQuery() {
		while(lexer.hasNext()) {
			try {
				IFieldValueGetter fieldGetter = getValidFieldGetter(lexer.getNextAttributeName());
				IComparisonOperator comparisonOperator = getValidcomparisonOperator(lexer.getNextOperator());
				String literal = lexer.getNextLiteral();
				
				queryList.add(new ConditionalExpression(fieldGetter, literal, comparisonOperator));
				
				if(lexer.hasNext() && !lexer.nextLogicOperator().toLowerCase().equals("and")) {
					throw new QueryParserException("Invalid querry expression");
				}
			} catch (QueryLexerException ex){
				throw new QueryParserException("Invalid querry expression");
			}
			
			
		}
	}
	
	/**
	 * Returns {@link IFieldValueGetter} if given string 
	 * is valid attribute of student record
	 * @param s - string to be checked
	 * @return {@link IFieldValueGetter} if given string 
	 *			 is valid attribute
	 * @throws QueryLexerException if given string 
	 * 			is not valid attribute of student record
	 */
	private IFieldValueGetter getValidFieldGetter(String s) {
		if(s.equals("jmbag")) {
			return FieldValueGetters.JMBAG;
		}else if(s.equals("firstName")) {
			return FieldValueGetters.FIRST_NAME;
		}else if(s.equals("lastName")) {
			return FieldValueGetters.LAST_NAME;
		}else {
			throw new QueryParserException("Invalid attribute name");
		}
	}
	
	/**
	 * Returns {@link IComparisonOperator} if given string 
	 * is valid operator
	 * @param s - string to be checked
	 * @return {@link IComparisonOperator} if given string 
	 *			 is operator
	 * @throws QueryLexerException if given string 
	 * 			is not valid operatord
	 */
	private IComparisonOperator getValidcomparisonOperator(String s) {
		if(s.equals("<")) {
			return ComparisonOperators.LESS;
		}else if(s.equals("<=")) {
			return ComparisonOperators.LESS_OR_EQUALS;
		}else if(s.equals("=")) {
			return ComparisonOperators.EQUALS;
		}else if(s.equals(">")){
			return ComparisonOperators.GREATER;
		}else if(s.equals(">=")){
			return ComparisonOperators.GREATER_OR_EQUALS;
		}else if(s.equals("!=")){
			return ComparisonOperators.NOT_EQUALS;
		}else if(s.equals("LIKE")){
			return ComparisonOperators.LIKE;
		}else {
			throw new QueryParserException("Invalid operator");
		}
		
	}
}
