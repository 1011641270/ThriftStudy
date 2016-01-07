/**
 * This File is created by hztianduoduo at 2016年1月7日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.consumer;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.rpc.framework.HelloService;

/**
 * @author hztianduoduo
 *
 */
public class NiftyClient {
    
    public static int port = 1234;
    public static String ip = "localhost";

    public static void main(String[] args) throws Exception {
        
        TTransport transport = new TSocket(ip, port);  
        transport.open();  
        TProtocol protocol = new TBinaryProtocol(transport);  
        HelloService.Client client = new HelloService.Client(protocol);  
        
        System.out.println(getTotalTimeForSingleThreadMultiExecute(client));
        
        transport.close(); 
        
    }
    
    //4809
    public static long getTotalTimeForSingleThreadMultiExecute(HelloService.Client client) throws Exception{
        
        long time = System.currentTimeMillis();
        
        for(int i=1;i<=100000;i++){
            
            client.hello("word");
            client.add(1, 2);
            
        }
        
        return (long)(System.currentTimeMillis() - time);
        
    }
    
}
