package week4.hw02;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class HW9_CyclicBarrier3 {
	
	private static Integer result;

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		long start = System.currentTimeMillis();
		CyclicBarrier barrier = new CyclicBarrier(1,  () -> result = KKFibo.sum());
		barrier.await();
  		System.out.println("异步计算结果为：" + result);
  		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
	}
}
