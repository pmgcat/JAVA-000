package week4.hw02;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HW12_Lock {

	private static volatile Integer result;
	private static final Lock lock = new ReentrantLock();
	private static final Condition condition = lock.newCondition();

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		long start = System.currentTimeMillis();
		launchTask();
		printResult();
		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

	}
	
	private static void printResult() throws InterruptedException {
		try {
			lock.lock();
			if (null == result) condition.await();
			System.out.println("异步计算结果为：" + result);
		} finally {
			lock.unlock();
		}
	}

	private static void launchTask() {
		new Thread(() -> {
			try {
				lock.lock();
				result = KKFibo.sum();
				condition.signalAll();
			} finally {
				lock.unlock();
			}
		}).start();
		;
	}

}
