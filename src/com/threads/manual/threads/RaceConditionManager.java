package com.threads.manual.threads;

import java.util.concurrent.atomic.AtomicInteger;

public class RaceConditionManager {
	public static void main(String[] args) {
		Runnable runable = new MyThreadwithRaceCondition();
		Thread th = new Thread(runable);
		th.setName("Thread A");

		Runnable runable1 = new MyThreadwithRaceCondition();
		Thread th1 = new Thread(runable1);
		th1.setName("Thread B");

		Runnable runable2 = new MyThreadwithRaceCondition();
		Thread th2 = new Thread(runable2);
		th2.setName("Thread C");

		th1.start();
		th.start();
		th2.start();

	}
}

//find out why all conditions are working well?
class MyThreadwithRaceCondition implements Runnable {
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private volatile int volInt = 0;
	private int hits = 0;
	private int syncHit = 0;

	@Override
	public void run() {
		for (int i = 0; i < 50000; i++) {
			countHits();
			countVolInt();
			countAtomitInteger();
			syncIncrementer();
		}
		System.out.println("synchronised method " + syncHit + " " + Thread.currentThread().getName());
		System.out.println("AtomicInteger var rerult " + atomicInteger + " " + Thread.currentThread().getName());
		System.out.println("Volatile var result " + volInt + " " + Thread.currentThread().getName());
		System.out.println("Ordinary var result " + hits + " " + Thread.currentThread().getName());
	}

	private synchronized void syncIncrementer() {
		syncHit=syncHit+1;
	}

	private void countAtomitInteger() {
		atomicInteger.addAndGet(1);
	}

	private void countVolInt() {
		volInt = volInt+1;
	}

	public void countHits() {
		this.hits += 1;
	}

}