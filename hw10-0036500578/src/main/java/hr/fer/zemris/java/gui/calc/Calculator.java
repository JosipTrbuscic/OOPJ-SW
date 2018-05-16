package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.binaryoperation.Addition;
import hr.fer.zemris.java.gui.calc.binaryoperation.BinaryOperation;
import hr.fer.zemris.java.gui.calc.binaryoperation.Division;
import hr.fer.zemris.java.gui.calc.binaryoperation.Multiply;
import hr.fer.zemris.java.gui.calc.binaryoperation.Power;
import hr.fer.zemris.java.gui.calc.binaryoperation.Subtract;
import hr.fer.zemris.java.gui.calc.unaryoperation.Cos;
import hr.fer.zemris.java.gui.calc.unaryoperation.Ctg;
import hr.fer.zemris.java.gui.calc.unaryoperation.Ln;
import hr.fer.zemris.java.gui.calc.unaryoperation.Log;
import hr.fer.zemris.java.gui.calc.unaryoperation.MulInverse;
import hr.fer.zemris.java.gui.calc.unaryoperation.Sin;
import hr.fer.zemris.java.gui.calc.unaryoperation.Tan;
import hr.fer.zemris.java.gui.calc.unaryoperation.UnaryOperation;
import hr.fer.zemris.java.gui.layout.CalcLayout;

/**
 * 
 * @author josip
 *
 */
public class Calculator extends JFrame{
	private CalcModel model;
	private JCheckBox inv;
	private Map<String, BinaryOperation> binaryOperations;
	private Map<String, UnaryOperation> unaryOperations;
	
	private List<Double> stack = new ArrayList<>();
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Calculator calc = new Calculator();
				calc.setVisible(true);
			}
		});
	}
	

	public Calculator() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Calculator");
		setLocation(500, 300);
		setSize(550, 400);
		model = new CalcModelImpl();
		createBinaryOperations();
		createUnaryOperations();
		initGUI(); 
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		
		JPanel panel = new JPanel(new CalcLayout(5));
		
		panel.add(new CalculatorLabel(model),"1,1");

		
		inv = new JCheckBox("Inv");
		inv.setBackground(Color.decode("#729FCF"));
		panel.add(inv, "5,7");
		
		List<CalculatorButton> buttons = createButtons();
		buttons.forEach(b->{
			panel.add(b, b.getPosition());
			b.setBackground(Color.decode("#729FCF"));	
		});
		cp.add(panel);
		
	}
	
	private List<CalculatorButton> createButtons(){
		List<CalculatorButton> buttons = new ArrayList<>(30);
		
		int buttonName = 0;
		buttons.add(new CalculatorButton(String.valueOf(buttonName),
				"5,3",
				l->model.insertDigit(0)));
		for(int i = 4; i > 1; i--) {
			for(int j = 3; j < 6;j++) {
				buttonName++;
				int digit = buttonName;
				buttons.add(new CalculatorButton(String.valueOf(buttonName),
						String.format("%d,%d", i,j),
						l->model.insertDigit(digit)));
			}
		}
		
		buttons.add(new CalculatorButton("=","1,6",l->{
			if(!model.isActiveOperandSet()) {
				model.setValue(model.getValue());
				return;
			}
			double result = 0;
			try {
				result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),model.getValue());
			} catch(ArithmeticException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Arithmetic Error", JOptionPane.ERROR_MESSAGE);
				model.clear();
			}
			model.clearAll();
			model.setValue(result);
		}));
		buttons.add(new CalculatorButton("/","2,6",l->{ 
			try {
				binaryOperations.get("div").doOperation(model);
			} catch(ArithmeticException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Arithmetic Error", JOptionPane.ERROR_MESSAGE);
				model.clear();
			}
		}));
		buttons.add(new CalculatorButton("*","3,6",l->{ 
			binaryOperations.get("mul").doOperation(model);
		}));
		buttons.add(new CalculatorButton("-","4,6",l->{ 
			binaryOperations.get("sub").doOperation(model);
		}));
		buttons.add(new CalculatorButton("+","5,6",l->{ 
			binaryOperations.get("add").doOperation(model);
		}));
		
		buttons.add(new CalculatorButton("x^n","5,1",l->{ 
			binaryOperations.get("xpown").doOperation(model);
		}));
		
		buttons.add(new CalculatorButton("+/-", "5,4", l->model.swapSign()));
		buttons.add(new CalculatorButton(".","5,5",l->model.insertDecimalPoint()));
		buttons.add(new CalculatorButton("clr", "1,7", l->model.clear()));
		buttons.add(new CalculatorButton("res", "2,7", l->model.clearAll()));
		buttons.add(new CalculatorButton("push", "3,7", l->{
			stack.add(model.getValue());
		}));
		buttons.add(new CalculatorButton("pop", "4,7", l->{
			if(stack.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Stack is empty", "Stack Error", JOptionPane.ERROR_MESSAGE);
			}else {
				model.setValue(stack.get(stack.size()-1));
				stack.remove(stack.size()-1);
			}
			
		}));
		
		buttons.add(new CalculatorButton("1/x", "2,1", l-> {
			try {
				unaryOperations.get("inv").doOperation(model, inv.isSelected());
			} catch(ArithmeticException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Arithmetic Error", JOptionPane.ERROR_MESSAGE);
				model.clear();
			}
			
		}));
		buttons.add(new CalculatorButton("log", "3,1", l-> {
			unaryOperations.get("log").doOperation(model, inv.isSelected());
		}));
		buttons.add(new CalculatorButton("ln", "4,1", l-> {
			unaryOperations.get("ln").doOperation(model, inv.isSelected());
		}));
		
		buttons.add(new CalculatorButton("sin", "2,2", l-> {
			try {
				unaryOperations.get("sin").doOperation(model, inv.isSelected());
			} catch(ArithmeticException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Arithmetic Error", JOptionPane.ERROR_MESSAGE);
				model.clear();
			}
		}));
		buttons.add(new CalculatorButton("cos", "3,2", l-> {
			try {
				unaryOperations.get("cos").doOperation(model, inv.isSelected());
			} catch(ArithmeticException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Arithmetic Error", JOptionPane.ERROR_MESSAGE);
				model.clear();
			}
		}));
		buttons.add(new CalculatorButton("tan", "4,2", l-> {
			unaryOperations.get("tan").doOperation(model, inv.isSelected());
		}));
		buttons.add(new CalculatorButton("ctg", "5,2", l-> {
			unaryOperations.get("ctg").doOperation(model, inv.isSelected());
		}));
		
		return buttons;
	}
	
	private void createBinaryOperations() {
		binaryOperations = new HashMap<>();
		binaryOperations.put("add", new Addition());
		binaryOperations.put("mul", new Multiply());
		binaryOperations.put("div", new Division());
		binaryOperations.put("sub", new Subtract());
		binaryOperations.put("xpown", new Power());
	}
	
	private void createUnaryOperations() {
		unaryOperations = new HashMap<>();
		unaryOperations.put("cos", new Cos());
		unaryOperations.put("sin", new Sin());
		unaryOperations.put("tan", new Tan());
		unaryOperations.put("ln", new Ln());
		unaryOperations.put("log", new Log());
		unaryOperations.put("ctg", new Ctg());
		unaryOperations.put("inv", new MulInverse());
	}
	
	private static class CalculatorButton extends JButton{
		private static final long serialVersionUID = 1L;
		private String position;
		
		public CalculatorButton(String name, String position, ActionListener listener) {
			super(name);
			this.addActionListener(listener);
			this.position = position;
		}
		
		public String getPosition() {
			return position;
		}
	}
	
	private static class CalculatorLabel extends JLabel implements CalcValueListener{

		public CalculatorLabel(CalcModel model) {
			setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			setFont(new Font("ARIAL", Font.PLAIN, 40));
			setBackground(Color.YELLOW);
			setOpaque(true);
			setText("0");
			setBorder(BorderFactory.createLineBorder(Color.decode("#729FCF")));
			model.addCalcValueListener(this);
		}
		
		@Override
		public void valueChanged(CalcModel model) {
			setText(model.toString());
			
		}
		
	}

}

