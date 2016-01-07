/**
 * This File is created by hztianduoduo at 2016年1月6日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @author hztianduoduo
 *
 */
public class HelloServiceClient {
    
    public static String ip = "localhost";
    public static int port = 8082;
    public static int time_out = 30000;
    
    public static void main(String[] args) throws Exception {
        
        int numTasks = 10000;
        ExecutorService pool = Executors.newFixedThreadPool(10);
        
        long time = System.currentTimeMillis();
        
        for (int i = 0; i < numTasks; i++) {
            pool.execute(runTask());
        }
        
        System.out.println("Biotime:" + (System.currentTimeMillis() - time)); //83
        
    }
    
    //253
    private static Runnable runTask() {
        
        return new Runnable() {

            @Override
            public void run() {
                
                TTransport transport = null;
                try{
                        //创建Transport
                        transport = new TSocket(ip, port, time_out);
                        //创建TProtocol
                        TProtocol protocol = new TBinaryProtocol(transport);
                        //基于TTransport和TProtocol创建Client
                        com.rpc.framework.Hello.Client client = new com.rpc.framework.Hello.Client(protocol);
                        transport.open();
                        //调用client方法

                        client.hello("word");
                        client.add(1, 2);

                }catch(Exception e){
                        e.printStackTrace();
                }  
                
                
            }
            
        };
        
    }
    
    
 //2478  21188
 public static long getTotalTimeForSingleThreadMultiExecute(com.rpc.framework.Hello.Client client) throws Exception{
        
        long time = System.currentTimeMillis();
        
        for(int i=1;i<=10000;i++){
            
            client.hello("word");
            client.add(1, 2);
            
        }
        
        return (long)(System.currentTimeMillis() - time);
        
    }

}
