package week4.hw02;

import java.util.concurrent.CountDownLatch;

public class HW6_CountDownLatch {

	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		CountDownLatch c = new CountDownLatch(1);
		KKFiboTask task = new KKFiboTask(c);
		new Thread(task).start(); // 这是得到的返回值
		c.await();
		// 确保 拿到result 并输出
		System.out.println("异步计算结果为：" + task.getResult());
		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
		// 然后退出main线程
	}

	public static class KKFiboTask implements Runnable {

		private final CountDownLatch c;
		private Integer result;

		public KKFiboTask(CountDownLatch c) {
			this.c = c;
		}

		@Override
		public void run() {
			try {
				result = KKFibo.sum();
			} finally {
				c.countDown();
			}
		}

		public Integer getResult() {
			return result;
		}

	}
}
