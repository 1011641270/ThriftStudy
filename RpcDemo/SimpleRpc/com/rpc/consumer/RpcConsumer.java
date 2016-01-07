/**
 * This File is created by hztianduoduo at 2016年1月5日,any questions please have a
 * message on the http://tian-dd.top.
 */
package com.rpc.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rpc.framework.RpcFramework;
import com.rpc.resource.HelloService;

/**
 * @author hztianduoduo
 */
public class RpcConsumer {

    public static void main(String[] args) throws Exception {

        final HelloService service = RpcFramework.refer(HelloService.class,
                "127.0.0.1", 1234);

        int numTasks = 10000;
        ExecutorService pool = Executors.newFixedThreadPool(10);

        long time = System.currentTimeMillis();

        for (int i = 0; i < numTasks; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    service.hello("world");
                    service.add(1, 2);
                }
            });
        }

        System.out.println("demotime:" + (System.currentTimeMillis() - time)); // 25
        System.out.println("singleThread:" + getTotalTimeForSingleThreadMultiExecute(service)); // 23751

    }
    
 public static long getTotalTimeForSingleThreadMultiExecute(HelloService service) throws Exception{
        
        long time = System.currentTimeMillis();
        
        for(int i=1;i<=10000;i++){
            
            service.hello("word");
            service.add(1, 2);
            
        }
        
        return (long)(System.currentTimeMillis() - time);
        
    }

}
