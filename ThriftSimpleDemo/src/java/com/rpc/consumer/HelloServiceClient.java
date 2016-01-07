/**
 * This File is created by hztianduoduo at 2016年1月6日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.consumer;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.rpc.framework.Hello;

/**
 * @author hztianduoduo
 *
 */
public class HelloServiceClient {
    
    public static String ip = "localhost";
    public static int port = 8081;
    public static int time_out = 30000;
    
    public static void main(String[] args) {
        
        TTransport transport = null;
        try{
                //创建Transport
                transport = new TSocket(ip, port, time_out);
                //创建TProtocol
                TProtocol protocol = new TBinaryProtocol(transport);
                //基于TTransport和TProtocol创建Client
                Hello.Client client = new Hello.Client(protocol);
                transport.open();
                
                System.out.println(getTotalTimeForSingleThreadMultiExecute(client));
                
        }catch(Exception e){
                e.printStackTrace();
        }
        
    }
    
    //2492
    public static long getTotalTimeForSingleThreadMultiExecute(Hello.Client client) throws Exception{
        
        long time = System.currentTimeMillis();
        
        for(int i=1;i<=10000;i++){
            
            client.hello("word");
            client.add(1, 2);
            
        }
        
        return (long)(System.currentTimeMillis() - time);
        
    }

}
