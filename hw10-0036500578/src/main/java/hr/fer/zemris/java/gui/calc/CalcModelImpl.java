package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of calculator model
 * @author Josip Trbuscic
 *
 */
public class CalcModelImpl implements CalcModel{
	
	/**
	 * String representation of value currently
	 * being entered in calculator
	 */
	private String enteredValue;
	
	/**
	 * Currently active operand
	 */
	private double activeOperand;
	
	/**
	 * Pending binary operation
	 */
	private DoubleBinaryOperator pendingOperation;
	
	/**
	 * Indicator if active operand is set
	 */
	private boolean activeOperandSet;
	
	/**
	 * List of listeners
	 */
	private List<CalcValueListener> listeners;
	
	/**
	 * Constructor
	 */
	public CalcModelImpl() {
		enteredValue = "";
		listeners = new ArrayList<>();
	}
	
	/**
	 * Adds given listener to this model.
	 * @param l - listener 
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if(!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	/**
	 * Removes specified listener from the model.
	 * @param l - listener to remove
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Returns string representing value stored in the model
	 * @return string representing value stored in the model
	 */
	@Override
	public String toString() {
		if(enteredValue.isEmpty()) return "0";
		if(enteredValue.equals("-0")) return "-0";
		if(enteredValue.contains(".")) {
			removeLeadingZeroesDouble();
			return enteredValue;
		}
		return String.format("%d", Integer.parseInt(enteredValue));
	}
	
	/**
	 * Returns {@code double} value stored in the model
	 * @return value stored in the model
	 */
	@Override
	public double getValue() {
		if(enteredValue.isEmpty()) return 0.;
		return Double.parseDouble(enteredValue);
	}

	/**
	 * Stores given value in the model
	 * @param value - value to be stored
	 */
	@Override
	public void setValue(double value) {
		if(Double.isFinite(value)) {
			String valueString = String.valueOf(value);
			if(isInteger(valueString)) {
				enteredValue = valueString.substring(0,valueString.indexOf("."));
			}else {
				enteredValue = valueString;
			}
			notifyObservers();
		}
	}

	/**
	 * Clears value currently stored in the model
	 */
	@Override
	public void clear() {
		enteredValue = "";
		notifyObservers();
	}

	/**
	 * Clears value currently stored in the model,
	 * currently active operand, and pending binary operation
	 */
	@Override
	public void clearAll() {
		clearActiveOperand();
		clearPendingOperation();
		clear();
	}

	/**
	 * Swaps sign of currently stored value
	 */
	@Override
	public void swapSign() {
		if(enteredValue.isEmpty()) return;
		setValue(-1*getValue());
	}

	/**
	 * Appends decimal point to the string representing 
	 * stored value if it was not already appended
	 */
	@Override
	public void insertDecimalPoint() {
		if(enteredValue.isEmpty()) {
			enteredValue = "0.";
			notifyObservers();
		}
		
		if(!enteredValue.contains(".")) {
			enteredValue += ".";
			notifyObservers();
		}
	}

	/**
	 * Appends given digit to the string representing stored value
	 * @param digit - digit to append
	 */
	@Override
	public void insertDigit(int digit) {
		
		if(enteredValue.contains(".")) {
			if(getValue() != 0 && Double.MAX_VALUE / Math.abs(getValue()) < 10) return;
		}else {
			if(getValue() != 0 && Integer.MAX_VALUE / Math.abs(getValue()) < 10) return;
		}
		
		if(enteredValue.equals("0") && digit == 0) return;
		
		enteredValue += digit;
		notifyObservers();
	}

	/**
	 * Returns true if active operand is set, false otherwise
	 * @return true if active operand is set, false otherwise
	 */
	@Override
	public boolean isActiveOperandSet() {
		return activeOperandSet;
	}

	/**
	 * Returns value currently set as active operand. If active 
	 * operand is not set exception is thrown
	 * @return value currently set as active operand
	 * @throws IllegalStateException if active operand is not set
	 */
	@Override
	public double getActiveOperand() {
		if(!activeOperandSet) throw new IllegalStateException("Active operand is not set");
		return activeOperand;
	}

	/**
	 * Sets given value as current active operand
	 * @param activeOperand - operand to be set
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		this.activeOperandSet = true;
	}

	/**
	 * Clears current active operand
	 */
	@Override
	public void clearActiveOperand() {
		activeOperand = 0;
		this.activeOperandSet = false;
	}
	
	/**
	 * Clears current pending operation
	 */
	public void clearPendingOperation() {
		pendingOperation = null;
	}

	/**
	 * Returns {@code DoubleBinaryOperator} which is currently set. If 
	 * binary operator is not set exception is thrown
	 * @return current {@code DoubleBinaryOperator}
	 * @throws IllegalStateException if binary operator is not set
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		if(pendingOperation == null) throw new IllegalStateException("Binary operator is not sett");
		return pendingOperation;
	}

	/**
	 * Sets given {@link DoubleBinaryOperator} as current one
	 * @param op - binary operator
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}
	
	/**
	 * Notifies all observers that value has changed
	 */
	private void notifyObservers() {
		listeners.forEach(l->l.valueChanged(this));
	}
	
	/**
	 * Returns true if given string can be parsed as integer
	 * @param value - string to check
	 * @return true if given string can be parsed as integer, false otherwise
	 */
	private boolean isInteger(String value) {
		if(value.matches("^-?\\d+\\.0+$")) return true;
		return false;
	}
	
	/**
	 * Removes leading zeroes from double value
	 */
	private void removeLeadingZeroesDouble() {
		if(enteredValue.startsWith("0.")) return;
		int i = 0;
		while(enteredValue.charAt(i) == '0') {
			i++;
		}
		enteredValue = enteredValue.substring(i);
	}
}
