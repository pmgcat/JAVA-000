package week4.hw02;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class HW14_FutureTask2 {
	
	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		long start = System.currentTimeMillis();
		RunnableFuture<Integer> task = new FutureTask<Integer>(KKFibo::sum) {
			protected void done() { 
				try {
					System.out.println("异步计算结果为：" + get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
		  		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
			}
		};
		new Thread(task).start();
	}
}
