package hr.fer.zemris.java.hw05.db;

import java.util.List;

public class QueryFilter implements IFilter{
	private List<ConditionalExpression> queryList;
	
	public QueryFilter(List<ConditionalExpression> queryList) {
		this.queryList = queryList;
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression query: queryList) {
			if(!query.getComparisonOperator().satisfied(query.getFieldGetter().get(record), query.getStringLiteral())) {
				return false;
			}
			
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		
	}
	
}
