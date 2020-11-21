package week4.hw02;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class HW8_CyclicBarrier2 {
	
	private static Integer result;

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		long start = System.currentTimeMillis();
		CyclicBarrier barrier = new CyclicBarrier(2, null);
		KKFiboTask task = new KKFiboTask(barrier);
		new Thread(task).start();
		barrier.await();
		// 确保 拿到result 并输出
  		System.out.println("异步计算结果为：" + result);
  		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
	}

	public static class KKFiboTask implements Runnable {

		private final CyclicBarrier barrier;

		public KKFiboTask(CyclicBarrier barrier) {
			this.barrier = barrier;
		}

		@Override
		public void run() {
			result = KKFibo.sum();
			try {
				barrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

		public Integer getResult() {
			return result;
		}

	}
}
