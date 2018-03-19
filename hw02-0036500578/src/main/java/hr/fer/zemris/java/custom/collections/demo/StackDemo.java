package hr.fer.zemris.java.custom.collections.demo;

import java.util.Scanner;
import static java.lang.Integer.parseInt;

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
		//Kako postupiti u catch bloku
		for(String part:parts) {
			try
			{
				Integer operand = Integer.parseInt(part);
				stack.push(operand);
				
			}catch(NumberFormatException ex){
				try {
					Integer operand2 = (Integer) stack.pop();
					Integer operand1 = (Integer) stack.pop();
					
					calculate(operand1, operand2, part);
				} catch(EmptyStackException emptyStack) {
					System.out.println("Invalid postfix expression. Terminating..");
					System.exit(1);
				} catch (IllegalArgumentException invalidArgument) {
					System.out.println(invalidArgument.getMessage()+"Terminating...");
					System.exit(1);
				}
			}
		}
		
		if(stack.size()==1) {
			System.out.println("Result = "+stack.pop());
		} else {
			
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
			if(((Integer) second) == 0){
				throw new IllegalArgumentException("Division by zero");
			}
			return Integer.valueOf( first /  second);
		} 
		else if(operator.equals("%")) {
			if(((Integer) second) == 0){
				throw new IllegalArgumentException("Division by zero");
			}
			return Integer.valueOf(first % second);
		} 
		else throw new IllegalArgumentException("Invalid operator");
		
	}
}
