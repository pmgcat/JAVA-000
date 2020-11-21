package week4.hw02;

import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class HW13_FutureTask1 {
    
    public static void main(String[] args) throws Exception {
        long start=System.currentTimeMillis();
        RunnableFuture<Integer> task = new FutureTask<>(KKFibo::sum);
        new Thread(task).start(); //这是得到的返回值
        System.out.println("异步计算结果为：" + task.get());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
    
   
}
