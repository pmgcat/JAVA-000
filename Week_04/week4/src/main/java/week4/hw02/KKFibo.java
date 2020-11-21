package week4.hw02;

public class KKFibo {

	public static int sum() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fibo(36);
	}

	private static int fibo(int a) {
		if (a < 3)
			return 1;
		return fibo(a - 1) + fibo(a - 2);
	}
}
