package hr.fer.zemris.java.hw05.db;

public class ComparisonOperators {
	public static final IComparisonOperator LESS = (s1, s2) -> {
		return s1.compareTo(s2) < 0;
	};

	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> {
		return s1.compareTo(s2) <= 0;
	};

	public static final IComparisonOperator GREATER = (s1, s2) -> {
		return s1.compareTo(s2) > 0;
	};

	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> {
		return s1.compareTo(s2) >= 0;
	};

	public static final IComparisonOperator EQUALS = (s1, s2) -> {
		return s1.compareTo(s2) == 0;
	};

	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> {
		return s1.compareTo(s2) != 0;
	};

	public static final IComparisonOperator LIKE = (s1, s2) -> {
		if (s2.contains("*")) {
			if (s2.indexOf("*") != s2.lastIndexOf("*")) {
				throw new IllegalArgumentException("More than 1 wildcard in a literal");
			}

			if (s2.startsWith("*")) {
				if (s1.endsWith(s2.replace("*", ""))) {
					return true;
				}
			} else if (s2.endsWith("*")) {
				if (s1.startsWith(s2.replace("*", ""))) {
					return true;
				}
			} else {
				String[] parts = s2.split("\\*");
				if (s1.startsWith(parts[0]) && s1.substring(parts[0].length()).endsWith(parts[1])) {
					return true;
				}
			}
			return false;
		} else {
			return s1.equals(s2);
		}
	};

	public static void main(String[] args) {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		System.out.println(oper.satisfied("Zagreb", "Aba*")); // false
		System.out.println(oper.satisfied("AAA", "AA*AA")); // false
		System.out.println(oper.satisfied("AAAA", "AA*AA")); // true

		IComparisonOperator oper2 = ComparisonOperators.EQUALS;
		System.out.println(oper2.satisfied("A", "a"));
	}
}
