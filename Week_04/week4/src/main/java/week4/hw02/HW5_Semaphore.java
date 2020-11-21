package week4.hw02;

import java.util.concurrent.Semaphore;

public class HW5_Semaphore {

	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		Semaphore semaphore = new Semaphore(0);
		KKFiboTask task = new KKFiboTask(semaphore);
		new Thread(task).start(); // 这是得到的返回值
		semaphore.acquire();
		// 确保 拿到result 并输出
		System.out.println("异步计算结果为：" + task.getResult());
		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
		// 然后退出main线程
	}

	public static class KKFiboTask implements Runnable {

		private Semaphore semaphore;
		private Integer result;

		public KKFiboTask(Semaphore semaphore) {
			this.semaphore = semaphore;
		}

		@Override
		public void run() {
			try {
				result = KKFibo.sum();
			} finally {
				semaphore.release();
			}
		}

		public Integer getResult() {
			return result;
		}

	}
}
