package week4.hw02;

public class HW11_SynchronizedWait {

	private static volatile Integer result;

	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		launchTask();
		printResult();
		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
	}
	
	private static void printResult() throws InterruptedException {
		synchronized (HW11_SynchronizedWait.class) {
			if (null == result) HW11_SynchronizedWait.class.wait();
			System.out.println("异步计算结果为：" + result);
		}
	}

	private static void launchTask() {
		new Thread(() -> {
			synchronized (HW11_SynchronizedWait.class) {
				try {
					result = KKFibo.sum();
				} finally {
					HW11_SynchronizedWait.class.notify();
				}
			}
		}).start();
	}

}
