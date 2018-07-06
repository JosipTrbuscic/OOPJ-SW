package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * File visitor implementation which builds global vocabulary
 * i.e. vocabulary containing words of every document in visited folder
 * @author Josip Trbuscic
 *
 */
public class VocabularyBuilderFileVisitor extends SimpleFileVisitor<Path>{
	/**
	 * Collection of stop words
	 */
	private Set<String> stopWords;
	
	/**
	 * Vocabulary
	 */
	private Set<String> vocabulary;

	/**
	 * Constructor
	 * @param stopWords - collection of stop words
	 */
	public VocabularyBuilderFileVisitor(Set<String> stopWords) {
		Objects.requireNonNull(stopWords, "Collection of stop words must not be null");
		this.stopWords = stopWords;
		vocabulary = new HashSet<>();
	}
	
	@Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs)
        throws IOException
    {
		List<String> lines = Files.readAllLines(path);
		for(String line : lines) {
			if(line.trim().isEmpty()) continue;
			List<String> words = parseLine(line.trim());
			for(String word : words) {
				if(!vocabulary.contains(word) && !stopWords.contains(word)) {
					vocabulary.add(word);
				}
			}
		}
		
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
	 * Returns vocabulary
	 * @return vocabulary
	 */
	public Set<String> getVocabulary() {
		return vocabulary;
	}

}
