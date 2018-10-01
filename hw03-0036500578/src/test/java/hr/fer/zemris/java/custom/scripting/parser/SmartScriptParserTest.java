package hr.fer.zemris.java.custom.scripting.parser;



import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.hw03.SmartScriptTester;

public class SmartScriptParserTest {

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}

	@Test
	public void parseTest() {
		String s = loader("test.txt");
		
		SmartScriptParser test = new SmartScriptParser(s);
		String recreated = SmartScriptTester.createOriginalDocumentBody(test.getDocumentNode());
		
		SmartScriptParser parser2 = new SmartScriptParser(recreated);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = SmartScriptTester.createOriginalDocumentBody(document2);
		
		assertEquals(recreated, originalDocumentBody2);
	}
	
	@Test
	public void parseTest2() {
		String s = loader("test2.txt");
		
		SmartScriptParser test = new SmartScriptParser(s);
		String recreated = SmartScriptTester.createOriginalDocumentBody(test.getDocumentNode());
		
		SmartScriptParser parser2 = new SmartScriptParser(recreated);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = SmartScriptTester.createOriginalDocumentBody(document2);
		
		assertEquals(recreated, originalDocumentBody2);
	}
	
	@Test
	public void parseTest3() {
		String s = loader("test3.txt");
		
		SmartScriptParser test = new SmartScriptParser(s);
		String recreated = SmartScriptTester.createOriginalDocumentBody(test.getDocumentNode());
		
		SmartScriptParser parser2 = new SmartScriptParser(recreated);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = SmartScriptTester.createOriginalDocumentBody(document2);
		
		assertEquals(recreated, originalDocumentBody2);
	}
	
	@Test
	public void parseTest4() {
		String s = loader("test4.txt");
		
		SmartScriptParser test = new SmartScriptParser(s);
		String recreated = SmartScriptTester.createOriginalDocumentBody(test.getDocumentNode());
		
		SmartScriptParser parser2 = new SmartScriptParser(recreated);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = SmartScriptTester.createOriginalDocumentBody(document2);
		
		assertEquals(recreated, originalDocumentBody2);
	}

}
