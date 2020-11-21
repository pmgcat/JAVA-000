package week4.hw02;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HW17_CompletionService {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		final ExecutorService es = Executors.newCachedThreadPool();
		final CompletionService<Integer> cs = new ExecutorCompletionService<>(es);
		long start = System.currentTimeMillis();
		cs.submit(KKFibo::sum);
		// 这是得到的返回值
		// 确保 拿到result 并输出
  		System.out.println("异步计算结果为：" + cs.take().get());
  		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
  		es.shutdown();
	}
	
}
