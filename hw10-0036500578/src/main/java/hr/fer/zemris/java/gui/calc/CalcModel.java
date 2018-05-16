package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Interface which defines calculator model
 * @author Josip Trbuscic
 *
 */
public interface CalcModel {
	
	/**
	 * Adds given listener to this model.
	 * @param l - listener 
	 */
	void addCalcValueListener(CalcValueListener l);
	
	/**
	 * Removes specified listener from the model.
	 * @param l - listener to remove
	 */
	void removeCalcValueListener(CalcValueListener l);
	
	/**
	 * Returns string representing value stored in the model
	 * @return string representing value stored in the model
	 */
	String toString();
	
	/**
	 * Returns {@code double} value stored in the model
	 * @return value stored in the model
	 */
	double getValue();
	
	/**
	 * Stores given value in the model
	 * @param value - value to be stored
	 */
	void setValue(double value);
	
	/**
	 * Clears value currently stored in the model
	 */
	void clear();
	
	/**
	 * Clears value currently stored in the model,
	 * currently active operand, and pending binary operation
	 */
	void clearAll();
	
	/**
	 * Swaps sign of currently stored value
	 */
	void swapSign();
	
	/**
	 * Appends decimal point to the string representing 
	 * stored value if it was not already appended
	 */
	void insertDecimalPoint();
	
	/**
	 * Appends given digit to the string representing stored value
	 * @param digit - digit to append
	 */
	void insertDigit(int digit);
	
	/**
	 * Returns true if active operand is set, false otherwise
	 * @return true if active operand is set, false otherwise
	 */
	boolean isActiveOperandSet();
	
	/**
	 * Returns value currently set as active operand. If active 
	 * operand is not set exception is thrown
	 * @return value currently set as active operand
	 * @throws IllegalStateException if active operand is not set
	 */
	double getActiveOperand();
	
	/**
	 * Sets given value as current active operand
	 * @param activeOperand - operand to be set
	 */
	void setActiveOperand(double activeOperand);
	
	/**
	 * Clears current active operand
	 */
	void clearActiveOperand();
	
	/**
	 * Returns {@code DoubleBinaryOperator} which is currently set. If 
	 * binary operator is not set exception is thrown
	 * @return current {@code DoubleBinaryOperator}
	 * @throws IllegalStateException if binary operator is not set
	 */
	DoubleBinaryOperator getPendingBinaryOperation();
	
	/**
	 * Sets given {@link DoubleBinaryOperator} as current one
	 * @param op - binary operator
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}