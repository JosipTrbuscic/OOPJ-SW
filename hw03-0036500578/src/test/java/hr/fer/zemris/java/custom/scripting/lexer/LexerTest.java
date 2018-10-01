package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;

public class LexerTest {
	Lexer lex1;
	Lexer lex2;
	Lexer lex3;
	Lexer lex4;
	
	@Before
	public void setup() {
		String s1 = "{$ var $}";
		String s2 = " 567 \\{${ ";
		String s3 = "{$FOR asd -123\"str\ning\" $}";
		String s4 = "{$end$}";
		lex1 = new Lexer(s1);
		lex2 = new Lexer(s2);
		lex3 = new Lexer(s3);
		lex4 = new Lexer(s4);
		
	}
	
	@Test
	public void getNextTokenEmptyTag() {
		Token token = lex1.nextToken();
		assertEquals( "{$",token.getValue());
		assertEquals(TokenType.SOT,token.getType());
		
		lex1.setState(LexerState.TAG_STATE);
		
		token = lex1.nextToken();
		assertEquals("var",token.getValue());
		assertEquals( TokenType.VARIABLE, token.getType());
		
		token = lex1.nextToken();
		assertEquals("$}",token.getValue());
		assertEquals(TokenType.EOT,token.getType());	
	}
	
	@Test
	public void getNextTokenText() {
		Token token = lex2.nextToken();

		assertEquals(" 567 \\{${ ",token.getValue());
		assertEquals(token.getType(), TokenType.TEXT);
	}
	
	@Test
	public void getNextTokenEnd() {
		Token token = lex3.nextToken();
		assertEquals(token.getValue(),"{$");
		assertEquals(TokenType.SOT,token.getType());
		
		lex3.setState(LexerState.TAG_STATE);
		
		token = lex3.nextToken();
		assertEquals("FOR",token.getValue());
		assertEquals(TokenType.VARIABLE,token.getType());
		
		token = lex3.nextToken();
		assertEquals("asd",token.getValue());
		assertEquals(TokenType.VARIABLE, token.getType());
		
		token = lex3.nextToken();
		assertEquals(Integer.valueOf(-123),token.getValue());
		assertEquals(TokenType.INTEGER,token.getType());
		
		token = lex3.nextToken();
		assertEquals("\"str\ning\"",token.getValue());
		assertEquals(TokenType.TEXT,token.getType());
		
		token = lex3.nextToken();
		assertEquals("$}",token.getValue());
		assertEquals(TokenType.EOT,token.getType());	
		
		
	}
	
	@Test
	public void getNextTokenFor() {
		Token token = lex4.nextToken();
		assertEquals(token.getValue(),"{$");
		assertEquals(TokenType.SOT,token.getType());
		
		lex4.setState(LexerState.TAG_STATE);
		
		token = lex4.nextToken();
		assertEquals("end",token.getValue());
		assertEquals(TokenType.VARIABLE,token.getType());
		
		token = lex4.nextToken();
		assertEquals("$}",token.getValue());
		assertEquals(TokenType.EOT,token.getType());
	}
	
}
