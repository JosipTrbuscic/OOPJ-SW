package hr.fer.zemris.java.custom.scripting.parser;
/**
 * Valid lexer states
 * @author Josip Trbuscic
 *
 */
public enum LexerState {
	/**
	 * Text reading state
	 */
	TEXT_STATE,
	
	/**
	 * Tag reading state
	 */
	TAG_STATE,
}
