/**
 * This File is created by hztianduoduo at 2016年1月7日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.provider;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

import com.rpc.framework.HelloService;
import com.rpc.resource.HelloServiceImpl;

/**
 * @author hztianduoduo
 *
 */
public class Server {
    
    public static int port = 8081; 
    
    public static void main(String[] args) throws Exception{
        
        TServerSocket serverTransport = new TServerSocket(7911);
        
        @SuppressWarnings({
            "unchecked", "rawtypes"
        })
        HelloService.Processor helloProcessor = new HelloService.Processor(
                        new HelloServiceImpl());
        
        TMultiplexedProcessor processor = new TMultiplexedProcessor();
        
        processor.registerProcessor("HelloService", helloProcessor);
        
        TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(
                        serverTransport).processor(processor));
        
        System.out.println("Starting server on port 7911 ...");
        server.serve();
        
        
    }

}
