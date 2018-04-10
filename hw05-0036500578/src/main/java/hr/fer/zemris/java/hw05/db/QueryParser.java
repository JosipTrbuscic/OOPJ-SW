package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QueryParser {
	private QueryLexer lexer;
	private List<ConditionalExpression> queryList;
	
	public QueryParser(String query) {
		if(query == null) throw new NullPointerException("Query cannot be null");
		lexer = new QueryLexer(query);
		queryList = new ArrayList<>();
		parseQuery();
	}
	
	public boolean isDirectQuery() {
		return queryList.size() == 1;
	}
	
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) throw new IllegalStateException("Query is not direct");
		
		return queryList.get(0).getStringLiteral();
	}
	
	public List<ConditionalExpression> getQuery(){
		return queryList;
	}
	
	private void parseQuery() {
		while(lexer.hasNext()) {
			IFieldValueGetter fieldGetter = getValidFieldGetter(lexer.getNextAttributeName());
			IComparisonOperator comparisonOperator = getValidcomparisonOperator(lexer.getNextOperator());
			String literal = lexer.getNextLiteral();
			
			queryList.add(new ConditionalExpression(fieldGetter, literal, comparisonOperator));
			
			if(lexer.hasNext() && !lexer.nextLogicOperator().toLowerCase().equals("and")) {
				throw new QueryParserException("Invalid querry expression");
			}
		}
	}
	
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
			throw new QueryParserException("Invalid attribute name");
		}
		
	}
	
	public static void main(String[] args) {
		QueryParser qp1 = new QueryParser(" jmbag       =\"0123456789\"    ");
		System.out.println("isDirectQuery(): " + qp1.isDirectQuery()); // true
		System.out.println("jmbag was: " + qp1.getQueriedJMBAG()); // 0123456789
		System.out.println("size: " + qp1.getQuery().size()); // 1
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		System.out.println("isDirectQuery(): " + qp2.isDirectQuery()); // false
		//System.out.println(qp2.getQueriedJMBAG()); // would throw!
		System.out.println("size: " + qp2.getQuery().size()); // 2
	}
}
