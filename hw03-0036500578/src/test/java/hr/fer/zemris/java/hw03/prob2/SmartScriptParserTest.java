package hr.fer.zemris.java.hw03.prob2;

import static org.junit.Assert.*;

import static hr.fer.zemris.java.hw03.SmartScriptTester.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

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
		String s = loader("test2.txt");
		
		SmartScriptParser test = new SmartScriptParser(s);
		
		String recreated = SmartScriptTester.createOriginalDocumentBody(test.getDocumentNode());
		System.out.println(recreated);
	}
	
	@Test
	public void parseTest2() {
		System.out.println("--------------------");
		String s = loader("test2.txt");
		
		SmartScriptParser test = new SmartScriptParser(s);
		
		String recreated = SmartScriptTester.createOriginalDocumentBody(test.getDocumentNode());
		System.out.println(recreated);
	}
	
	@Test
	public void parseTest3() {
		System.out.println("--------------------");
		String s = loader("test3.txt");
		
		SmartScriptParser test = new SmartScriptParser(s);
		
		String recreated = SmartScriptTester.createOriginalDocumentBody(test.getDocumentNode());
		System.out.println(recreated);
	}

}
