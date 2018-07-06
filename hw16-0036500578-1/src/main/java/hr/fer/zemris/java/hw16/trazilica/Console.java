package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class represents simple search engine. When program is started 
 * it will iterate through folder containing all files and on each one 
 * program will build a vector containing meaningful words from document.
 * Those vectors are used to compare entered query with the document and 
 * determine coefficient of similarity which is in the end used to sort 
 * search results
 * 
 * @author Josip Trbuscic
 *
 */
public class Console {
	
	/**
	 * Path to stop-words
	 */
	private static final String PATH_TO_STOP_WORDS = "./src/main/resources/hrvatski_stoprijeci.txt";
	
	/**
	 * Stop-words
	 */
	private static Set<String> stopWords;
	
	/**
	 * Global vocabulary
	 */
	private static Set<String> vocabulary;
	
	/**
	 * List of documents
	 */
	private static List<DocumentVector> documents;

	/**
	 * Starting point of a program
	 * @param args - path to the source documents
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		boolean queryExecuted = false;
		
		if(args.length != 1) {
			System.out.println("Invalid number of arguments");
			System.exit(0);
		}
		
		Path path = Paths.get(args[0]);
		if(!path.toFile().exists() || !path.toFile().isDirectory()) {
			System.out.println("Argument must represent a path to a directory");
			System.exit(1);
		}
		
		stopWords = loadStopWords(Paths.get(PATH_TO_STOP_WORDS));

		FileVisitor<Path> wcfv = new VocabularyBuilderFileVisitor(stopWords);
		Files.walkFileTree(path, wcfv);
		vocabulary = ((VocabularyBuilderFileVisitor) wcfv).getVocabulary();
		
		FileVisitor<Path> vbfv = new VectorBuilderFileVisitor(stopWords);
		Files.walkFileTree(path, vbfv);
		documents  = ((VectorBuilderFileVisitor) vbfv).getDocVectors();
		documents.forEach(d->d.buildTfidfVector(documents));

		System.out.println("Velicina rijecnika je "+vocabulary.size()+" rijeci");

		Scanner sc = new Scanner(System.in);
		Map<DocumentVector, Double> results = new HashMap<>();
		while(true) {
			System.out.print("Enter command > ");
			String[] inputParts = sc.nextLine().trim().split("\\s+");
			String command = inputParts[0];
			
			switch(command.toLowerCase()) {
			case("query"):
				if(inputParts.length < 2) {
					System.out.println("Invalid query command arguments");
					continue;
				}
				List<String> queryWords = new ArrayList<>();
				queryWords.addAll(Arrays.asList(inputParts));
				queryWords.remove(0);
				removeNonExistingWords(queryWords);
				if(queryWords.isEmpty()) {
					System.out.println("None of the words exists in document");
					continue;
				}
				
				DocumentVector queryDoc = new DocumentVector(null);
				
				
				queryWords.forEach(w-> queryDoc.addWord(w));
				queryDoc.buildTfidfVector(documents);
				
				for(DocumentVector doc : documents) {
					if(doc.multiply(queryDoc)/(doc.getNorm()*doc.getNorm()) != 0) {
						results.put(doc, doc.multiply(queryDoc)/(doc.getNorm()*queryDoc.getNorm()));
					}
				}
				
				results = sortByValue(results);
				
				System.out.print("Query is: [");
				for(int i = 0; i<queryWords.size(); i++) {
					System.out.print(queryWords.get(i));
					
					if(i == queryWords.size()-1) {
						System.out.print("]");
						System.out.println();
					} else {
						System.out.print(", ");
					}
				}
				System.out.println("Najboljih 10 rezultata");
				printResults(results);
				queryExecuted = true;
				
				break;
			case("type"):
				if(!queryExecuted) {
					System.out.println("Must run query first");
					continue;
				}
				int index = -1;
				try {
					index = Integer.parseInt(inputParts[1]);
				} catch (NumberFormatException e){
					System.out.println("Invalid index");
					continue;
				}
				if(index < 0 || index > results.keySet().size()-1) {
					System.out.println("Index out of range");
					continue;
				}
				int i = 0;
				for(DocumentVector doc : results.keySet()) {
					if(i != index ) {
						i++;
						continue;
					}
					String docPath = "Dokument: "+ doc.getPath();
					System.out.println(docPath);
					System.out.println(getSeperator(docPath.length()));
					List<String> lines = Files.readAllLines(Paths.get(doc.getPath()));
					lines.forEach(l->System.out.println(l));
					break;
				}
				break;
			case("results"):
				if(!queryExecuted) {
					System.out.println("Must run query first");
					continue;
				}
				printResults(results);
				
				break;
			case("exit"):
				sc.close();
				System.exit(2);
			default:
				System.out.println("Invalid command");
				continue;
			}
		}
	}

	/**
	 * Builds separator used by "type" command
	 * @param length - length of a separator
	 * @return string of '-' representing line separator
	 */
	private static String getSeperator(int length) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i< length; i++) {
			sb.append('-');
		}
		
		return sb.toString();
	}

	/**
	 * Prints results of the search to the standard output
	 * @param results - results of the search
	 */
	private static void printResults(Map<DocumentVector, Double> results) {
		int i = 0;
		for(DocumentVector doc : results.keySet()) {
			if(results.get(doc) == 0) break;
			System.out.print("["+i+"] ");
			System.out.printf("%.4f",results.get(doc));
			System.out.print(" "+doc.getPath());
			System.out.println();
			if(i++ == 9) break;
		}
		
	}

	/**
	 * Loads stop-words from the given path
	 * @param path - path to the file containing stop-words
	 * @return collection of stop-words
	 */
	private static Set<String> loadStopWords(Path path){
		Set<String> stopWords = new TreeSet<>();
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(path);
		} catch (IOException ignorable) {
		}
		for(String line : lines) {
			if(!line.trim().isEmpty()) {
				stopWords.add(line.toLowerCase());
			}
		}
		return stopWords;
	}
	
	/**
	 * Removes words which don't exist in the vocabulary
	 * from the given list of words
	 * @param words - list of words to filter
	 */
	private static void removeNonExistingWords(List<String> words) {
		Iterator<String> it = words.iterator();
		while(it.hasNext()) {
			String word = it.next();
			if(stopWords.contains(word) || !vocabulary.contains(word)) {
				it.remove();
			}
		}
	}
	
	/**
	 * Sorts map of results by values. After sorting, iterating
	 * over key set will return key which corresponds to values 
	 * from the biggest to lowest.
	 * @param map - map of results
	 * @return sorted map of results 
	 */
	public static Map<DocumentVector, Double> sortByValue(Map<DocumentVector, Double> map) {
        List<Entry<DocumentVector, Double>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());
        Collections.reverse(list);
        
        Map<DocumentVector, Double> result = new LinkedHashMap<>();
        for (Entry<DocumentVector, Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
