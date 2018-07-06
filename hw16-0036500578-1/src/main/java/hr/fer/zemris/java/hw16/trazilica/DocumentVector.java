package hr.fer.zemris.java.hw16.trazilica;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents vector built over the single text document
 * @author Josip Trbuscic
 *
 */
public class DocumentVector {
	
	/**
	 * Path to the file
	 */
	private String path;
	
	/**
	 * Vocabulary of the document, maps word to the number of the occurrences
	 * in the document
	 */
	private Map<String, Integer> docVocabulary = new HashMap<>();
	
	/**
	 * TF-IDF vector of this document
	 */
	private Map<String, Double> tfidfVector = new HashMap<>();
	
	/**
	 * Constructor
	 * @param path - path to the document
	 */
	public DocumentVector(String path) {
		this.path = path;
	}
	
	/**
	 * Adds word to the vocabulary or increments the number of
	 * occurrences
	 * @param word - word to add
	 */
	public void addWord(String word) {
		if(docVocabulary.containsKey(word)) {
			docVocabulary.put(word, docVocabulary.get(word)+1);
		}else {
			docVocabulary.put(word, 1);
		}
	}
	
	/**
	 * Builds TF-IDF vector of this document
	 * @param documents - other documents 
	 */
	public void buildTfidfVector(List<DocumentVector> documents) {
		 for(String key : docVocabulary.keySet()) {
			 int tf = docVocabulary.get(key);
			 int occurences = 0;
			 for(DocumentVector doc : documents) {
				 if(doc.docVocabulary.containsKey(key)) {
					 occurences++;
				 }
			 }
			 double idf = Math.log(documents.size()/(double)occurences);
			 
			 tfidfVector.put(key, tf*idf);
		 }
	}
	
	/**
	 * Multiplies this document vector with the other one
	 * @param other - other document vector
	 * @return dot product of two vectors
	 */
	public double multiply(DocumentVector other) {
		double result = 0;
		for(String word : other.tfidfVector.keySet()) {
			if(this.tfidfVector.containsKey(word)) {
				result += other.tfidfVector.get(word)*this.tfidfVector.get(word);
			}
		}
		
		return result;
	}
	
	/**
	 * Returns norm of this vector
	 * @return norm of this vector
	 */
	public double getNorm() {
		double sumSquares = 0;
		
		for(double value : tfidfVector.values()) {
			sumSquares += value*value;
		}
		
		return Math.sqrt(sumSquares);
	}
	
	/**
	 * Returns true if this document contains given word
	 * @param word - word to look for
	 * @return true if this document contains given word, false otherwise
	 */
	public boolean containsWord(String word) {
		return docVocabulary.containsKey(word);
	}
	
	/**
	 * Returns path of this document
	 * @return path of this document
	 */
	public String getPath() {
		return path;
	}
}
