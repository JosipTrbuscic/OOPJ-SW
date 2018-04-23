package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Implementation of {@link IFilter} interface.
 * This implementation will test given student record 
 * for every conditional expression.
 * @author Josip Trbuscic
 *
 */
public class QueryFilter implements IFilter{
	/**
	 * List where conditional expressions are stored
	 */
	private List<ConditionalExpression> queryList;
	
	/**
	 * Constructs new query filter with given
	 * list of conditional expression
	 * @param queryList - list of conditional expressions
	 * @throws NullPointerException if given list is {@code null}
	 */
	public QueryFilter(List<ConditionalExpression> queryList) {
		if(queryList == null) throw new NullPointerException("List of queries cannot be null");
		
		this.queryList = queryList;
	}
	
	/**
	 * Returns {@code true} if given student record 
	 * satisfies every condition in the query
	 * @return {@code true} if every condition is met,
	 * 			{@code false} otherwise
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression query: queryList) {
			if(!query.getComparisonOperator().satisfied(query.getFieldGetter().get(record),
					query.getStringLiteral())
					) {
				return false;
			}
			
		}
		
		return true;
	}
}
