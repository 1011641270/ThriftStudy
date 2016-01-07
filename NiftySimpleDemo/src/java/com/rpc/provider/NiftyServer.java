/**
 * This File is created by hztianduoduo at 2016年1月7日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.provider;

import org.apache.thrift.TProcessor;

import com.facebook.nifty.core.NettyServerTransport;
import com.facebook.nifty.core.ThriftServerDef;
import com.facebook.nifty.core.ThriftServerDefBuilder;
import com.rpc.framework.HelloService;
import com.rpc.resource.HelloServiceImpl;

/**
 * @author hztianduoduo
 *
 */
public class NiftyServer {
    
    public static int port = 1234;
    
    public static void main(String[] args) {
        
        startServer();
        System.out.println("服务器启动成功...");  
        
        
    }
    
    public static void startServer(){
        
        HelloService.Iface serviceInterface = new HelloServiceImpl();

        TProcessor processor = new HelloService.Processor<>(serviceInterface);

        ThriftServerDef serverDef = new ThriftServerDefBuilder().listen(port).withProcessor(processor).build();

        final NettyServerTransport server = new NettyServerTransport(serverDef);

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    server.stop();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
    }

}
