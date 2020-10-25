package com.threads.manual.threads;

public class Deadlock {
	public static void main(String[] args) {
		Factory factory = new Factory();

		ThreadA producer = new ThreadA(factory);
		ThreadB consumer = new ThreadB(factory);

		producer.start();
		consumer.start();
	}
}

class ThreadA extends Thread {
	private Factory factory;

	public ThreadA(Factory factory) {
		this.factory = factory;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				factory.produce();
				System.out.println("Produced " + factory.getCount());
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class ThreadB extends Thread {
	private Factory factory;

	public ThreadB(Factory factory) {
		this.factory = factory;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				factory.consume();
				System.out.println("Consumed " + factory.getCount());
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Factory {
	private int product = 0;

	public synchronized void produce() throws InterruptedException {
		this.product++;
		if (product <= 20) {
			this.product++;
			notify();
		}
		else {
			wait();
		}
	}

	public synchronized void consume() throws InterruptedException {
		if (product > 0) {
			this.product--;
			wait();
		}
		else notify();
	}

	public int getCount() {
		return this.product;
	}
}
