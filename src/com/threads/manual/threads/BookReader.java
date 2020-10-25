package com.threads.manual.threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BookReader {
	static File file = new File("./book.txt");
	public static void main(String[] args) throws IOException, InterruptedException {
		try(FileReader reader = new FileReader(file);
				BufferedReader bis = new BufferedReader(reader);) {
		BookReader book = new BookReader();
		
		//reading file with main thread
		long start = System.currentTimeMillis();
		book.singleThread(bis);
		long end = System.currentTimeMillis();
		System.out.println("read with single thread in time :"+ (end-start) +" "+ Thread.currentThread().getName());
		
		//reading same file with 4 worker threads
		long start1 = System.currentTimeMillis();
		Executor service = Executors.newFixedThreadPool(4);
		for(int i=0;i<4; i++) service.execute(() -> {
			try {
				book.singleThread(bis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		long end1 = System.currentTimeMillis();
		System.out.println("read with 4 thread in time : "+ (end1-start1) +" "+ Thread.currentThread().getName());
		
		Thread.sleep(1000);
		}
	}
	
	public void singleThread(BufferedReader bis) throws IOException {
		String para = "";
		int count =0;
		while((para = bis.readLine()) != null) {
			count ++;
		}
	}
}
