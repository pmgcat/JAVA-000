package week4.hw02;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;

public class HW15_MyFutureTask {
    
    public static void main(String[] args) throws Exception {
        
        long start=System.currentTimeMillis();
        RunnableFuture<Integer> task = new MyFutureTask<>(KKFibo::sum);
        new Thread(task).start(); //这是得到的返回值
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + task.get());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        // 然后退出main线程
    }
    
    public static class MyFutureTask<V> implements RunnableFuture<V> {
    	
    	private static final String NULL = "";
    	private volatile int state;
        private static final int NEW          = 0;
        private static final int COMPLETING   = 1;
        private static final int NORMAL       = 2;
        private static final int EXCEPTIONAL  = 3;
        private static final int CANCELLED    = 4;
        private static final int INTERRUPTED  = 6;
    	
    	private Callable<V> callable;
    	private V result;
    	private ExecutionException e;
    	private volatile Thread runner;
    	private ConcurrentHashMap<Thread, String> waitTheadSet = new ConcurrentHashMap<>();
    	
    	public MyFutureTask(Callable<V> callable) {
    		if (callable == null) throw new NullPointerException();
            this.callable = callable;
            this.state = NEW; 
    	}
    	
		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			if (NEW != state ) return false;
			if (mayInterruptIfRunning) {
                try {
                    Thread t = runner;
                    if (t != null) t.interrupt();
                } finally { 
                   this.state = INTERRUPTED;
                }
            }
			return true;
		}

		@Override
		public boolean isCancelled() {
			return state >= CANCELLED;
		}

		@Override
		public boolean isDone() {
			return state != NEW;
		}

		private V report(int s) throws ExecutionException {
	        if (s == NORMAL)
	            return result;
	        if (s >= CANCELLED)
	            throw new CancellationException();
	        throw new ExecutionException((Throwable)result);
	    }
		
		@Override
		public V get() throws InterruptedException, ExecutionException {
			int s = state;
			if (s <= COMPLETING) s = awaitDone(false, 0L);
			if (null != e) throw e;
			return report(s);
		}

		@Override
		public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
	        if (unit == null) throw new NullPointerException();
	        int s = state;
	        if (s <= COMPLETING && (s = awaitDone(true, unit.toNanos(timeout))) <= COMPLETING) throw new TimeoutException();
			return report(s);
		}

		@Override
		public void run() {
			if (state != NEW) return;
			runner = Thread.currentThread();
			state = COMPLETING;
			try {
				result = callable.call();
				state = NORMAL;
			}  catch (Throwable e) {
				this.e = new ExecutionException(e);
				state = EXCEPTIONAL;
			} finally {
				finishCompletion();
			}
		}
		
		private int awaitDone(boolean timed, long nanos) throws InterruptedException {
			final long deadline = timed ? System.nanoTime() + nanos : 0L;
			Thread t = null;
	        boolean queued = false;
			for (;;) {
	            if (Thread.interrupted()) {
	                waitTheadSet.remove(t);
	                throw new InterruptedException();
	            }
	            int s = state;
	            if (s > COMPLETING) {
	                if (t != null) waitTheadSet.remove(t);
	                return s;
	            }
	            else if (s == COMPLETING)
	                Thread.yield();
	            else if (t == null)
	                t = Thread.currentThread();
	            else if (!queued) {
	            	waitTheadSet.put(t, NULL);
	            	queued = true;
	            } else if (timed) {
	                nanos = deadline - System.nanoTime();
	                if (nanos <= 0L) {
	                	this.waitTheadSet.remove(t);
	                    return state;
	                }
	                LockSupport.parkNanos(this, nanos);
	            }
	            else
	                LockSupport.park(this);
	        }
		}
		
		private void finishCompletion() {
			waitTheadSet.forEach((k, v) -> LockSupport.unpark(k));
	    }

    	
    } 
    
   
}
