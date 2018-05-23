package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.buttons.BinaryOperationButton;
import hr.fer.zemris.java.gui.calc.buttons.CalculatorButton;
import hr.fer.zemris.java.gui.calc.buttons.GenericCalculatorButton;
import hr.fer.zemris.java.gui.calc.buttons.UnaryOperationButton;
import hr.fer.zemris.java.gui.layout.CalcLayout;

/**
 * Simple calculator program which provides arithmetic operations 
 * and basic trigonometric operations and their inverses. 
 * @author Josip Trbuscic
 *
 */
public class Calculator extends JFrame{
	/**
	 * Universal ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Calculator model
	 */
	private CalcModel calcModel;
	
	/**
	 * Check box which indicates if unary operation
	 * should be inverted
	 */
	private JCheckBox inv;
	
	/**
	 * Stack
	 */
	private List<Double> stack = new ArrayList<>();
	
	/**
	 * Starting method
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Calculator calc = new Calculator();
				calc.setVisible(true);
			}
		});
	}
	
	/**
	 * Constructor. Sets basic frame parameters.
	 */
	public Calculator() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Calculator");
		setLocation(500, 300);
		setSize(700, 400);
		calcModel = new CalcModelImpl();
		initGUI(); 
	}
	
	/**
	 * Initializes GUI by adding components to the frame
	 */
	private void initGUI() {
		Container cp = getContentPane();
		
		JPanel panel = new JPanel(new CalcLayout(5));
		
		panel.add(new CalculatorLabel(calcModel),"1,1");

		
		inv = new JCheckBox("Inv");
		inv.setBackground(Color.decode("#729FCF"));
		panel.add(inv, "5,7");
		
		List<CalculatorButton> buttons = createButtons();
		buttons.forEach(b->{
			panel.add(b, b.getPosition());
			b.setBackground(new Color(7512015));	
		});
		cp.add(panel);
		
	}
	
	/**
	 * Creates a list of all buttons of this calculator
	 * and returns it
	 * @return list of all calculator buttons
	 */
	private List<CalculatorButton> createButtons(){
		List<CalculatorButton> buttons = new ArrayList<>(30);
		int buttonName = 0;
		buttons.add(new GenericCalculatorButton(String.valueOf(buttonName),
										"5,3",
										l->calcModel.insertDigit(0)));
		
		for(int i = 4; i > 1; i--) {
			for(int j = 3; j < 6;j++) {
				buttonName++;
				int digit = buttonName;
				buttons.add(new GenericCalculatorButton(String.valueOf(buttonName),
						String.format("%d,%d", i,j),
						l->calcModel.insertDigit(digit)));
			}
		}
		
		buttons.add(new GenericCalculatorButton("=","1,6",l->{
			if(!calcModel.isActiveOperandSet()) {
				calcModel.setValue(calcModel.getValue());
				return;
			}
			double result = 0;
			try {
				result = calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(),calcModel.getValue());
			} catch(ArithmeticException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Arithmetic Error", JOptionPane.ERROR_MESSAGE);
				calcModel.clear();
			}
			calcModel.clearAll();
			calcModel.setValue(result);
		}));
		buttons.add(new BinaryOperationButton("/","2,6",calcModel,(a,b)->{
			if(b == 0) {
				throw new ArithmeticException("Division by zero");
			} else {
				return a/b;
			}
		}));
		
		buttons.add(new BinaryOperationButton("*","3,6",calcModel,(a,b)->a*b));
		buttons.add(new BinaryOperationButton("-","4,6",calcModel,(a,b)->a-b));
		buttons.add(new BinaryOperationButton("+","5,6",calcModel,(a,b)->a+b));
		
		buttons.add(new BinaryOperationButton("x^n","5,1",calcModel,(a,b)->Math.pow(a, b)));
		
		buttons.add(new GenericCalculatorButton("+/-", "5,4", l->calcModel.swapSign()));
		buttons.add(new GenericCalculatorButton(".","5,5",l->calcModel.insertDecimalPoint()));
		buttons.add(new GenericCalculatorButton("clr", "1,7", l->calcModel.clear()));
		buttons.add(new GenericCalculatorButton("res", "2,7", l->calcModel.clearAll()));
		buttons.add(new GenericCalculatorButton("push", "3,7", l->{
			stack.add(calcModel.getValue());
		}));
		buttons.add(new GenericCalculatorButton("pop", "4,7", l->{
			if(stack.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Stack is empty", "Stack Error", JOptionPane.ERROR_MESSAGE);
			}else {
				calcModel.setValue(stack.get(stack.size()-1));
				stack.remove(stack.size()-1);
			}
		}));
		
		buttons.add(new UnaryOperationButton("1/x", "2,1",calcModel,x->1/x,x->x, inv));
		buttons.add(new UnaryOperationButton("log", "3,1",calcModel,Math::log10,x->Math.pow(10, x), inv));
		buttons.add(new UnaryOperationButton("ln", "4,1",calcModel,x->Math.log(x),x->Math.pow(Math.E, x), inv));
		buttons.add(new UnaryOperationButton("sin", "2,2",calcModel,Math::sin,Math::asin, inv));
		buttons.add(new UnaryOperationButton("cos", "3,2",calcModel,Math::cos,Math::acos, inv));
		buttons.add(new UnaryOperationButton("tan", "4,2",calcModel,Math::tan,Math::atan, inv));
		buttons.add(new UnaryOperationButton("ctg", "5,2",calcModel,x->1/Math.tan(x),x->1/Math.atan(x), inv));
		
		return buttons;
	}

	/**
	 * Class which represents screen of the calculator. It
	 * implements {@link CalcValueListener} interface that
	 * allows it to update currently displayed text when 
	 * values are being entered 
	 * @author Josip Trbuscic
	 *
	 */
	private static class CalculatorLabel extends JLabel implements CalcValueListener{

		private static final long serialVersionUID = 1L;

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

