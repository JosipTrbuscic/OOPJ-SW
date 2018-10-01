package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryParserTest {

	@SuppressWarnings("unused")
	@Test(expected=NullPointerException.class)
	public void nullQuery() {
		QueryParser parser= new QueryParser(null);
	}
	
	@Test
	public void directQuery() {
		QueryParser parser= new QueryParser("jmbag=\"0000000001\"");
		QueryParser parser2= new QueryParser("jmbag    =    \"0000000001\"");
		QueryParser parser3= new QueryParser("      jmbag=\"0000000001\"");
		QueryParser parser4= new QueryParser("      jmbag>   \"0000000001\"     ");
		
		assertEquals(true, parser.isDirectQuery());
		assertEquals(true, parser2.isDirectQuery());
		assertEquals(true, parser3.isDirectQuery());
		assertEquals(false, parser4.isDirectQuery());
		assertEquals("0000000001", parser.getQueriedJMBAG());
	}
	
	@Test
	public void twoConditionalExpressions() {
		QueryParser parser= new QueryParser("jmbag=\"0000000001\" anD   firstName>=\"Marković\"");
		QueryParser parser2= new QueryParser("firstNameLIKE\"0000000001\" anD   jmbag>=\"Marković\"");
		QueryParser parser3= new QueryParser("lastName   >\"0000000001\"   anD   lastName=\"Marković\"");
		
		assertEquals(false, parser.isDirectQuery());
		assertEquals(2, parser.getQuery().size());
		
		assertEquals(false, parser2.isDirectQuery());
		assertEquals(2, parser2.getQuery().size());
		
		assertEquals(false, parser3.isDirectQuery());
		assertEquals(2, parser3.getQuery().size());
	}
	
	@SuppressWarnings("unused")
	@Test(expected=QueryParserException.class)
	public void invalidOperator() {
		QueryParser parser= new QueryParser("jmbag==\"0000000001\" anD   firstName>=\"Marković\"");
		QueryParser parser2= new QueryParser("jmbagLIKe\"0000000001\" anD   firstName>=\"Marković\"");
		QueryParser parse3= new QueryParser("jmbag=\"0000000001\" anD   firstName=<\"Marković\"");
	}
	
	@SuppressWarnings("unused")
	@Test(expected=QueryParserException.class)
	public void invalidLogicalOperator() {
		QueryParser parser= new QueryParser("jmbag==\"0000000001\"anD   firstName>=\"Marković\"");
		QueryParser parser2= new QueryParser("jmbagLIKe\"0000000001\" anDfirstName>=\"Marković\"");
		QueryParser parser3= new QueryParser("jmbag=\"0000000001\"anDfirstName=<\"Marković\"");
		QueryParser parser4= new QueryParser("jmbag=\"0000000001\"asd");
	}
	
	@SuppressWarnings("unused")
	@Test(expected=QueryParserException.class)
	public void invalidAttributeName() {
		QueryParser parser= new QueryParser("jmag==\"0000000001\"");
		QueryParser parser2= new QueryParser("first Name>=\"Marković\"");
		QueryParser parser3= new QueryParser("firstname=<\"Marković\"");
	}
	
	@Test
	public void singleExpressioNotDirect() {
		QueryParser parser= new QueryParser("jmbag>=\"0000000001\"");
		QueryParser parser2= new QueryParser("firstName=    \"0000000001\"");
		QueryParser parser3= new QueryParser("      jmbagLIKE\"0000000001\"");
		QueryParser parser4= new QueryParser("lastName<  \"0000000001\"     ");
		
		assertEquals(false, parser.isDirectQuery());
		assertEquals(false, parser2.isDirectQuery());
		assertEquals(false, parser3.isDirectQuery());
		assertEquals(false, parser4.isDirectQuery());
	}
	
	@Test
	public void multipleExpressions() {
		QueryParser parser= new QueryParser("jmbag>=\"0000000001\" and firstName=    \"Ivan\" AND   "+
											"lastName<  \"Galvinić\"  AnD firstNameLIKE\"0000000001\"" );
		
		assertEquals(false, parser.isDirectQuery());
		assertEquals(4, parser.getQuery().size());
	}
	
	@SuppressWarnings("unused")
	@Test(expected=QueryParserException.class)
	public void invalidLiteral() {
		QueryParser parser= new QueryParser("jmbag>=\"0000000001");
		QueryParser parser2= new QueryParser("firstName=    \"0000000\"001\"");
		QueryParser parser3= new QueryParser("      jmbagLIKElastName");
	}
}
