package com.threads.manual.threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author 91831
 * <h1>
 * This class will be able to count the words in the existing ebook,
 * By using Single thread as well as 4 threads 
 * check the difference 
 * </h1>
 */
public class WordCounter {

	private static Map<String, Long> wordMap = null;

	public static void main(String[] args) throws IOException {
		File file = new File("./book.txt");
		//performing operations with single thread
		long start = System.currentTimeMillis();
		long end = processor(file);
		System.out.println("Time Taken with 1 threads " + (end - start));

		//Performing same operations with 4 threads
		Executor service = Executors.newFixedThreadPool(4);
			service.execute(() -> {
				long start1 = System.currentTimeMillis();
				long end1;
				try {
					end1 = processor(file);
					System.out.println("Time Taken with 4 threads" + (end1 - start1));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
	}

	private static long processor(File file) throws IOException {
		wordMap = dict(readFileWordbyWord(file));
		wordMap.forEach((k, v) -> {
			// System.out.println(k +" <--> "+v);
		});
		long end = System.currentTimeMillis();
		return end;
	}

	public static Map<String, Long> dict(List<String> words) {
		wordMap = words.stream().collect(Collectors.groupingBy((String k) -> k, Collectors.counting()));
		return wordMap;
	}

	public static List<String> readFileWordbyWord(File file) throws IOException {
		List<String> wordList = new ArrayList<>();
		FileReader reader = new FileReader(file);
		BufferedReader bf = new BufferedReader(reader);
		String para = "";
		while ((para = bf.readLine()) != null) {
			String[] words = para.split(" ");
			for (int i = 0; i < words.length; i++) {
				String w = words[i].trim();
				wordList.add(w);
			}
		}
		return wordList;
	}
}
