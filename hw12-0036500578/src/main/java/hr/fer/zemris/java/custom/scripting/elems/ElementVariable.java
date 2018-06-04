package hr.fer.zemris.java.custom.scripting.elems;
/**
 * Class represents valid variable name. Name must start with letter and 
 * then follows zero or more letters, numbers or underscores.
 * Extends {@code Element} class.
 * @author Josip Trbuscic
 *
 */
public class ElementVariable extends Element {

		private String name; //Stores variable name
		
		/**
		 * Constructs new {@code ElementVariable} specified 
		 * by valid name.
		 * @param name of variable
		 */
		public ElementVariable(String name) {
			this.name = name;
			
		}
		
		/**
		 * Returns String representation of 
		 * {@code ElementVariable} object
		 */
		@Override
		public String asText() {
			return name;
		}
		
		/**
		 * @return variable name;
		 */
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return asText();
		}
}
