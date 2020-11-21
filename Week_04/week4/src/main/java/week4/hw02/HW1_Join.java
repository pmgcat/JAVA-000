package week4.hw02;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
public class HW1_Join {
    
    public static void main(String[] args) throws InterruptedException {
        
        long start=System.currentTimeMillis();
        Result<Integer> result = new Result<>();
        Thread t = new Thread(() -> {result.setV(KKFibo.sum());}); //这是得到的返回值
        t.start();
        t.join();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result.getV());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        // 然后退出main线程
    }
    
    private static class Result<V> {
    	private V v;

		public V getV() {
			return v;
		}

		public void setV(V v) {
			this.v = v;
		}
    	
    }
}
