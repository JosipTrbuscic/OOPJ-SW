package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {
	
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
	
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch(NumberFormatException ex) {
			return false;
		}
		
		
	}
	
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
