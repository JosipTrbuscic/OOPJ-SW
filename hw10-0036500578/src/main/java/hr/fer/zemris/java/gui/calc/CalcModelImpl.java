package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel{
	private String enteredValue;
	private double activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private boolean activeOperandSet;
	
	private List<CalcValueListener> listeners;
	
	public CalcModelImpl() {
		enteredValue = "";
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if(!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}
	
	@Override
	public String toString() {
		if(enteredValue.isEmpty()) return "0";
		if(enteredValue.equals("-0")) return "-0";
		if(enteredValue.contains(".")) {
			return enteredValue;
		}
		return String.format("%d", Integer.parseInt(enteredValue));
	}
	
	@Override
	public double getValue() {
		if(enteredValue.isEmpty()) return 0.;
		return Double.parseDouble(enteredValue);
	}

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

	@Override
	public void clear() {
		enteredValue = "";
		notifyObservers();
	}

	@Override
	public void clearAll() {
		activeOperand = 0;
		activeOperandSet = false;
		pendingOperation = null;
		clear();
	}

	@Override
	public void swapSign() {
		if(enteredValue.isEmpty()) return;
		setValue(-1*getValue());
	}

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

	@Override
	public boolean isActiveOperandSet() {
		return activeOperandSet;
	}

	@Override
	public double getActiveOperand() {
		if(!activeOperandSet) throw new IllegalStateException("Active operand is not set");
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		this.activeOperandSet = true;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = 0;
		this.activeOperandSet = false;
	}
	
	
	public void clearPendingOperation() {
		pendingOperation = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		if(pendingOperation != null) throw new IllegalStateException("Binary operator already set");
		
		this.pendingOperation = op;
	}
	
	private void notifyObservers() {
		listeners.forEach(l->l.valueChanged(this));
	}
	
	private boolean isInteger(String value) {
		if(value.matches("^-?\\d+\\.0+$")) return true;
		return false;
	}

}
