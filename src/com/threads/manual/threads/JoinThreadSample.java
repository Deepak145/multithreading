package com.threads.manual.threads;

public class JoinThreadSample {
	public static void main(String[] args) throws InterruptedException {
		Runnable r1 = new MyJoinThread();
		Thread th1 = new Thread(r1);
		th1.setName("Thread A");
		th1.setPriority(5);
		
		Runnable r2 = new MyJoinThread();
		Thread th2 = new Thread(r2);
		th2.setName("Thread B");
		th2.setPriority(5);

		th1.start();
		th2.start();
		
		//th1.join();
		//th2.join();
	}
	
	
}


class MyJoinThread implements Runnable{

	private volatile int count=0;
	
	@Override
	public void run() {
		for(int i=0;i<100; i++) {
			System.out.println(count++ +" "+  Thread.currentThread().getName());
			if("Thread A".equals(Thread.currentThread().getName()))
					Thread.yield();
		}
	}
	
}