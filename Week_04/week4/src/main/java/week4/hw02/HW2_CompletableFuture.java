package week4.hw02;

import java.util.concurrent.CompletableFuture;

public class HW2_CompletableFuture {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String reusult = CompletableFuture.supplyAsync(KKFibo::sum)
				.thenCombine(CompletableFuture.supplyAsync(KKFibo::sum), (v1, v2) -> v1 + "_" + v2)
				.join();
		System.out.println("异步计算结果为：" + reusult);
		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

	}
}
