package week4.hw02;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.LinkedBlockingDeque;

public class HW10_BlockingQueue {
	
	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		long start = System.currentTimeMillis();
		BlockingQueue<Integer> queue = new LinkedBlockingDeque<>();
		new Thread(() -> queue.add(KKFibo.sum())).start();
  		System.out.println("异步计算结果为：" + queue.take());
  		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
	}
	
}
