package hr.fer.zemris.java.hw03;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptTester {

	
		public static void main(String[] args) {
			String docBody ="This is sam{p$$le text."
					+ "{$ FOR i @sin \"Ovo je \\\"citat\\\"\" 1 $}\n"
					+ "This is {$= i $}-th time this message is generated.\n" + "{$END$}\n" + "{$FOR i 0 10 2 $}\n"
					+ "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" + "{$END$}";
			SmartScriptParser parser = null;
			try {
			parser = new SmartScriptParser(docBody);
			} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
			} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
			}
			DocumentNode document = parser.getDocumentNode();
			String originalDocumentBody = createOriginalDocumentBody(document);
			System.out.println(originalDocumentBody);
		}
		
		public static String createOriginalDocumentBody(Node node) {
			StringBuilder sb = new StringBuilder();
			int size = node.numberOfChildren();
			int i=0;
			if(size == 0) {
				if(node instanceof TextNode) {
					TextNode text = (TextNode) node;
					return text.getText().asText();
				}else if(node instanceof ForLoopNode) {
					ForLoopNode text = (ForLoopNode) node;
					StringBuilder forLoop = new StringBuilder("{$ FOR");
					forLoop.append(" "+text.getStartExpression().toString());
					forLoop.append(" "+text.getEndExpression().toString());
					if(text.getEndExpression() != null) {
						forLoop.append(" "+text.getStepExpression().toString());
					}
					forLoop.append(" $}");
					return forLoop.toString();
					
				} else {
					EchoNode echo = (EchoNode) node;
					StringBuilder echoString = new StringBuilder("{$=");
					for(Element el:echo.getElements()) {
						echoString.append(" "+el.asText());
					}
					echoString.append(" $}");
					return echoString.toString();
				}
			}
			while(i<size) {
				sb.append(createOriginalDocumentBody(node.getChild(i)));
				++i;
			}
			return sb.toString();
			
		}
		
		private String loader(String filename) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try(InputStream is =
			this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while(true) {
			int read = is.read(buffer);
			if(read<1) break;
			bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
			} catch(IOException ex) {
			return null;
			}
			}
}
