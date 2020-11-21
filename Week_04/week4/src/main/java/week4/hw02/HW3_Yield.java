package week4.hw02;

import java.util.concurrent.BrokenBarrierException;

public class HW3_Yield {
	
	private static volatile Integer result;

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		long start = System.currentTimeMillis();
		new Thread(() -> result = KKFibo.sum()).start(); 
		while (null == result) Thread.yield();
		// 这是得到的返回值
		// 确保 拿到result 并输出
  		System.out.println("异步计算结果为：" + result);
  		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
	}
}
