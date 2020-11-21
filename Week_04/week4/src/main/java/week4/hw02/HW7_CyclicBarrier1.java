package week4.hw02;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class HW7_CyclicBarrier1 {
	
	private static Integer result;

	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		CyclicBarrier barrier = new CyclicBarrier(1, () -> {
			System.out.println("异步计算结果为：" + result);
			System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
		});
		KKFiboTask task = new KKFiboTask(barrier);
		new Thread(task).start();
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

	}
}
