package week4.hw02;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class HW16_CompletionResultPool {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		final ExecutorService es = Executors.newCachedThreadPool();
		final CompletionResultPool<Integer> resultPool = new CompletionResultPool<>(es);
		long start = System.currentTimeMillis();
		resultPool.submit(KKFibo::sum);
		resultPool.submit(KKFibo::sum);
		// 这是得到的返回值
		// 确保 拿到result 并输出
  		System.out.println("异步计算结果为：" + resultPool.get());
  		System.out.println("异步计算结果为：" + resultPool.get());
  		System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
  		es.shutdown();
	}
	
	public static class CompletionResultPool<V> {

		private final BlockingQueue<Result<V>> resultQueue = new LinkedBlockingQueue<>();
		private final ExecutorService es;
		
		public CompletionResultPool(ExecutorService es) {
			this.es = es;
		}

		public void submit(Callable<V> callable) {
			es.submit(new CompletionResultRunnable(callable));
		}
		
		public V get() throws InterruptedException, ExecutionException {
			return resultQueue.take().get();
		}
		
		public class CompletionResultRunnable implements Runnable {

			private Callable<V> callable;
			private CompletionResultRunnable(Callable<V> callable) {
				this.callable = callable;
			}
			
			@Override
			public void run() {
				Result<V> result = new Result<>();
				try {
					result.setV(callable.call());
				} catch (Throwable e) {
					result.setE(e);
				} finally {
					resultQueue.add(result);
				}
			}
		}
		
	}
	
	public static class Result<V> {
		
		private volatile V v;
		private volatile Throwable e;
		
		public void setE(Throwable e) {
			this.e = e;
		}
		public void setV(V v) {
			this.v = v;
		}
		public V get() throws ExecutionException {
			if (null != e) throw new ExecutionException(e);
			return v;
		}
		
	}
	
}
