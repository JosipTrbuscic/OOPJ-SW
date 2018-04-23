package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This class represents a storage for one object.
 * Stored object must represent a number. 
 * It offers basic arithmetic operations.
 * @author Josip Trbuscic
 *
 */
public class ValueWrapper {
	
	/**
	 * Stored value
	 */
	private Object value;
	
	/**
	 * Type of stored value
	 */
	private ValidType valueType;

	/**
	 * Enumeration which defines
	 * valid types of object
	 */
	private enum ValidType {
		STRING_INT, STRING_DEC, DOUBLE, INTEGER, NULL;
	}

	/**
	 * Enumeration which defines
	 * valid operations
	 */
	private enum ARTITHMETIC_OPERATION {
		SUB, ADD, MUL, DIV, COMPARE;
	}

	/**
	 * Constructs new instance of this class 
	 * from given object.
	 * @param value - object to be stored
	 * @throws IllegalArgumentException if given object 
	 * 			does not represent a number
	 */
	public ValueWrapper(Object value) {
		this.value = value;
		valueType = checkType(value);
	}

	/**
	 * Adds value of given object to the stored one  
	 * @param incValue - value to add
	 * @throws IllegalArgumentException if given object 
	 * 			does not represent a number 
	 */
	public void add(Object incValue) {
		setValue(operate(incValue, ARTITHMETIC_OPERATION.ADD));
	}

	/**
	 * Subtracts value of given object from the stored one  
	 * @param incValue - value to subtract
	 * @throws IllegalArgumentException if given object 
	 * 			does not represent a number 
	 */
	public void substract(Object incValue) {
		setValue(operate(incValue, ARTITHMETIC_OPERATION.SUB));
	}

	/**
	 * Multiplies stored value by the given one  
	 * @param incValue - value to multiply with
	 * @throws IllegalArgumentException if given object 
	 * 			does not represent a number 
	 */
	public void multiply(Object incValue) {
		setValue(operate(incValue, ARTITHMETIC_OPERATION.MUL));
	}

	/**
	 * Divides stored value with the given one
	 * @param incValue - value to divide with
	 * @throws IllegalArgumentException if given object 
	 * 			does not represent a number 
	 * @throws ArithmeticException if division by
	 * 			zero is tried
	 */
	public void divide(Object incValue) {
		setValue(operate(incValue, ARTITHMETIC_OPERATION.DIV));
	}

	/**
	 * Compares stored value with the given one
	 * @param withValue - value to compare
	 * 					stored value with
	 * @return  Returns a negative integer, zero,
	 * 			 or a positive integer as this object
	 * 			 is less than, equal to, or greater than
	 * 			 the specified object. 
	 * @throws IllegalArgumentException if given object 
	 * 			does not represent a number 
	 */
	public int numCompare(Object withValue) {
		return ((Integer)operate(withValue, ARTITHMETIC_OPERATION.COMPARE)).intValue();

	}

	/**
	 * Parses given object as number and performs given
	 * operation on stored value and given value.
	 * @param incValue - second operand
	 * @param operation - arithmetic operation to perform
	 * @return result of arithmetic operation
	 * @throws IllegalArgumentException if given object 
	 * 			does not represent a number 
	 * @throws ArithmeticException if division by
	 * 			zero is tried
	 */
	private Object operate(Object incValue, ARTITHMETIC_OPERATION operation) {
		ValidType incValueType = checkType(incValue);
		Double firstOperand = 0.;
		Double secondOperand = 0.;
		Object result;
		
		try {
			if(value != null) {
				firstOperand = Double.parseDouble(value.toString());

			}
			
			if(incValue != null) {
				secondOperand = Double.parseDouble(incValue.toString());
			}

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid Double Operand");
		}
		
		if (isResultTypeInteger(incValueType, valueType)) {
			result = calculate(firstOperand, secondOperand, operation).intValue();
			
		} else {
			result = calculate(firstOperand, secondOperand, operation);
			
			if(operation == ARTITHMETIC_OPERATION.COMPARE) {
				result = ((Double)result).intValue();
			}
		}

		return result;
	}

	/**
	 * Calculates the result of expression specified by given operands
	 * and specified operation
	 * @param first - first operand
	 * @param second - second operand
	 * @param operation - arithmetic operation
	 * @return result of arithmetic operation
	 * @throws ArithmeticException if division by zero
	 * 			is tried
	 */
	private Double calculate(Double first, Double second, ARTITHMETIC_OPERATION operation) {
		switch (operation) {
		case ADD:
			return first + second;
		case SUB:
			return first - second;
		case DIV:
			if (second == 0)
				throw new ArithmeticException("Cannot divide by 0");
			return first / second;
		case MUL:
			return first * second;
		default:
			return Double.valueOf(first.compareTo(second));
		}

	}

	/**
	 * Determines if result of arithmetic
	 * operation will be integer.
	 * @param firstType - first operand type
	 * @param secondType - second operand type
	 * @return true if result of of arithmetic
	 * 			operation will be integer, false
	 * 			otherwise
	 */
	private static boolean isResultTypeInteger(ValidType firstType, ValidType secondType) {
		if (secondType == ValidType.DOUBLE || secondType == ValidType.STRING_DEC) {
			return false;
		}

		if (firstType == ValidType.DOUBLE || firstType == ValidType.STRING_DEC) {
			return false;
		}

		return true;
	}

	/**
	 * Determines type of given object.
	 * @param value - object which type will
	 * 					be determined
	 * @return type of object
	 * @throws IllegalArgumentException of type of
	 * 			object is not valid
	 */
	private static ValidType checkType(Object value) {
		if (value == null)
			return ValidType.NULL;
		if (value instanceof Double)
			return ValidType.DOUBLE;
		if (value instanceof Integer)
			return ValidType.INTEGER;
		if (value instanceof String) {
			if (((String) value).contains(".") || ((String) value).contains("E")) {
				return ValidType.STRING_DEC;
			}

			return ValidType.STRING_INT;
		}

		throw new IllegalArgumentException("Invalid object type");
	}
	
	/**
	 * Sets current value of object
	 * to the given one
	 * @param value - value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Returns current value of object
	 * @return current value of object
	 */
	public Object getValue() {
		return value;
	}

}
