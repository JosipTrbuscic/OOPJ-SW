package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
/**
 * Demo program for calculation of postfix expression. Program
 * stack implementation of collection. Program expects postfix
 * expression as command line argument
 * @author Josip Trbuscic
 *
 */
public class StackDemo {
	/*
	 * Main method of postfix expression calculator.
	 * @param args - postfix expression 
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Invalid number of arguments");
			System.exit(1);
		}
		
		String[] parts = args[0].split("\\s+");
		ObjectStack stack = new ObjectStack();
		
		for(String part:parts) {
			if(isInteger(part)) {
				Integer operand = Integer.parseInt(part);
				stack.push(operand);
			
			} else {
				try {
					Integer operand2 = (Integer) stack.pop();
					Integer operand1 = (Integer) stack.pop();
						
					stack.push(calculate(operand1, operand2, part)); 
				} catch(EmptyStackException ex){
					System.out.println("Invalid postifx expression");
					System.exit(1);
					
				} catch(IllegalArgumentException ex) {
					System.out.println(ex.getMessage()+" Terminating");
					System.exit(0);
				}
			}
			
		}

		if(stack.size()==1) {
			System.out.println("Expression evaluates to "+stack.pop());
		} else {
			System.out.println("Invalid postfix expression");
		}
		
	}
	/**
	 * Returns true if specified string can be parsed as integer
	 * @param str - string to be parsed
	 * @return {@code true} if string can be parsed as integer, {@code false} otherwise
	 */
	private static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch(NumberFormatException ex) {
			return false;
		}
		
		
	}
	/**
	 * Calculates operation specified by two operands and valid operator.
	 * @param first Integer operand
	 * @param second Integer operand
	 * @param operator that specifies operation between operands.
	 * @return result of valid operation
	 * @throws IllegalArgumentException if divisor is zero or operator is invalid.
	 */
	public static Integer calculate(Integer first, Integer second, String operator) {
		if(operator.equals("+")) {
			return Integer.valueOf(first + second);
		} 
		else if(operator.equals("-")) {
			return Integer.valueOf( first - second);
		} 
		else if(operator.equals("*")) {
			return Integer.valueOf(first * second);
		} 
		else if(operator.equals("/")) {
			if(second == 0){
				throw new IllegalArgumentException("Division by zero.");
			}
			return Integer.valueOf( first /  second);
		} 
		else if(operator.equals("%")) {
			if(second == 0){
				throw new IllegalArgumentException("Division by zero.");
			}
			return Integer.valueOf(first % second);
		} 
		else throw new IllegalArgumentException("Invalid operator.");
		
	}
}
