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
			String docBody ="This is sa\\\\mp{le t\\{$ext."
					+ "\n{$ FOR i -10 \"Ovo je \\\"citat\\\"\" 1 $}\n"
					+ "This is {$isus i $}-th time this message is generated.\n" + "{$END$}\n" + "{$FOR i 0 10 2 $}\n"
					+ "sin({$=i$}^2) = {$= i i *  \"0.000\" $}\n" + "{$END$}";
			SmartScriptParser parser = null;
//			try {
			parser = new SmartScriptParser(docBody);
//			} catch(SmartScriptParserException e) {
//			System.out.println("Unable to parse document!");
//			System.exit(-1);
//			} catch(Exception e) {
//			System.out.println("If this line ever executes, you have failed this class!");
//			System.exit(-1);
//			}
			DocumentNode document = parser.getDocumentNode();
			String originalDocumentBody = createOriginalDocumentBody(document);
			System.out.println(originalDocumentBody);
		}
		
		public static String createOriginalDocumentBody(Node node) {
			StringBuilder sb = new StringBuilder();
			int i=0;
			while(i<node.numberOfChildren()) {
				Node child = node.getChild(i);
				
				if(child instanceof ForLoopNode) {
					sb.append(buildFor((ForLoopNode) child));
				}else if(child instanceof TextNode) {
					sb.append(buildText((TextNode) child));
				} else {
					sb.append(buildEcho((EchoNode) child));
				}
				
				i++;
				
			}
				
			return sb.toString();
		}
		
		private static String buildFor(ForLoopNode node) {
			StringBuilder sb = new StringBuilder();
			sb.append("{$ FOR");
			sb.append(" "+node.getVariable().asText());
			sb.append(" "+node.getStartExpression().asText());
			sb.append(" "+node.getEndExpression().asText());
			if(node.getStepExpression() != null) {
				sb.append(" "+node.getStepExpression().asText());
			}
			sb.append(" $}");
			
			int i=0;
			while(i < node.numberOfChildren()) {
				Node child = node.getChild(i);
				
				if(child instanceof ForLoopNode) {
					sb.append(buildFor((ForLoopNode) child));
				}else if(child instanceof TextNode) {
					sb.append(buildText((TextNode) child));
				} else {
					sb.append(buildEcho((EchoNode) child));
				}
				i++;
			}
			sb.append("{$ END $}");
			
			return sb.toString();
		}
		
		private static String buildEcho(EchoNode node) {
			StringBuilder sb = new StringBuilder("{$");
			for(Element el:node.getElements()) {
				sb.append(" "+el.asText());
			}
			
			sb.append(" $}");
			return sb.toString();
		}
		
		private static String buildText(TextNode node) {
			String s = node.getText().asText().replace("\\", "\\\\");
			s = node.getText().asText().replace("{$", "\\{$");
			return s;
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
