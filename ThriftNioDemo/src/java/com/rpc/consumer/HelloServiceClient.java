/**
 * This File is created by hztianduoduo at 2016年1月6日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.rpc.framework.Hello;

/**
 * @author hztianduoduo
 *
 */
public class HelloServiceClient {
    
    public static String ip = "localhost";
    public static int port = 8081;
    public static int time_out = 30000;
    
    public static void main(String[] args) throws Exception {

       int numTasks = 10000;
        
        ExecutorService pool = Executors.newFixedThreadPool(10);

        long time = System.currentTimeMillis();

        for (int i = 0; i < numTasks; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {

                    TTransport transport = null;
                    try {
                        
                        transport = new TFramedTransport(new TSocket(ip, port));
                        TCompactProtocol protocol = new TCompactProtocol(
                                transport);
                        Hello.Client client = new Hello.Client(protocol);
                        transport.open();

                        client.hello("world");
                        client.add(1, 2);

                    } catch (Exception e) {
                        
                        e.printStackTrace();
                    }

                }
            }

            );
        }

        System.out.println("Niotime:" + (System.currentTimeMillis() - time)); //32

        
    }
    
    //1090
    public static long getTotalTimeForSingleThreadMultiExecute(Hello.Client client) throws Exception{
        
        long time = System.currentTimeMillis();
        
        for(int i=1;i<=100000;i++){
            
            client.hello("word");
            client.add(1, 2);
            
        }
        
        return (long)(System.currentTimeMillis() - time);
        
    }

}
