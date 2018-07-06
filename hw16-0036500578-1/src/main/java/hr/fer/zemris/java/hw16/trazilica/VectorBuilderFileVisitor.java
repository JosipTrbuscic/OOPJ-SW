package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * File Visitor implementation which builds vectors over
 * visited documents and stores the vectors in internal 
 * collection
 * @author Josip Trbuscic
 *
 */
public class VectorBuilderFileVisitor extends SimpleFileVisitor<Path>{
	private List<DocumentVector> docVectors = new ArrayList<>();
	private Set<String> stopWords;
	
	/**
	 * Constructor
	 * @param stopWords - collection of stop words
	 */
	public VectorBuilderFileVisitor(Set<String> stopWords) {
		Objects.requireNonNull(stopWords, "Collection of stop words must not be null");
		this.stopWords = stopWords;
	}
	
	@Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs)
        throws IOException
    {
		List<String> lines = Files.readAllLines(path);
		DocumentVector docVector = new DocumentVector(path.toString());
		for(String line : lines) {
			List<String> words = parseLine(line.trim());
			for(String word : words) {
				if(!stopWords.contains(word)) {
					docVector.addWord(word);
				}
			}
		}
		docVectors.add(docVector);
		return FileVisitResult.CONTINUE;
    }

	/**
	 * Parses the line of the document and returns the words line contains
	 * @param line - document line to parse
	 * @return list of words
	 */
	private List<String> parseLine(String line){
		List<String> words = new ArrayList<>();
		StringBuilder wordSB = new StringBuilder();
		char[] chars = line.toCharArray();
		
		for(int i=0; i<chars.length; i++) {
			if(!Character.isAlphabetic(chars[i])) {
				if(wordSB.length() == 0) continue;
				words.add(wordSB.toString().toLowerCase());
				wordSB.setLength(0);
				continue;
			} 
			wordSB.append(chars[i]);
		}
		if(wordSB.length() > 0) {
			words.add(wordSB.toString().toLowerCase());
		}
		return words;
	}
	
	/**
	 * Returns collection of document vectors
	 * @return collection of document vectors
	 */
	public List<DocumentVector> getDocVectors() {
		return docVectors;
	}
}
