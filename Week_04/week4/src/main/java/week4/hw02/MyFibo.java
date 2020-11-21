package week4.hw02;

public class MyFibo {
	
	public static void main(String[] args) {
		long start = System.nanoTime();
		System.out.println(sum(36));
		System.out.println("took:" + (System.nanoTime() - start) / 1000_000.0);
	}
	
	public static long sum(int num) {
        long sum = 0;
        long sum1 = 1;
        long sum2 = 1;
        for (long i = 3; i <= num; i++) {
        	sum = sum1 + sum2;
        	sum1 = sum2;
        	sum2 = sum;
        }
        return sum;
    }
}
