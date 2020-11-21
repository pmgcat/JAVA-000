package week4.hw02;

import java.util.concurrent.locks.LockSupport;

public class HW4_LockSupport {

	public static void main(String[] args) throws InterruptedException {

		long start = System.currentTimeMillis();
		KKFiboTask task = new KKFiboTask(Thread.currentThread());
		new Thread(task).start(); // 这是得到的返回值
		LockSupport.park();
		// 确保 拿到result 并输出
		System.out.println("异步计算结果为：" + task.getResult());
		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
		// 然后退出main线程
	}

	public static class KKFiboTask implements Runnable {

		private Thread t;
		private Integer result;

		public KKFiboTask(Thread t) {
			this.t = t;
		}

		@Override
		public void run() {
			try {
				result = KKFibo.sum();
			} finally {
				LockSupport.unpark(t);
			}
		}

		public Integer getResult() {
			return result;
		}

	}
}
