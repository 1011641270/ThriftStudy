/**
 * This File is created by hztianduoduo at 2016年1月6日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.provider;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

import com.rpc.framework.Hello;
import com.rpc.resource.HelloServiceImpl;

/**
 * @author hztianduoduo
 *
 */
public class HelloServiceServer {
    
    public static int port = 8082; 
    
    public static void main(String[] args) {
        
      //创建processor
        TProcessor tprocessor = new Hello.Processor<Hello.Iface>(new HelloServiceImpl());
        try{
                //创建transport 阻塞通信
                TServerSocket serverTransport = new TServerSocket(port);
                //创建protocol  数据传输协议
                TBinaryProtocol.Factory protocol = new TBinaryProtocol.Factory();
                
                TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(serverTransport);
                tArgs.processor(tprocessor);
                tArgs.protocolFactory(protocol);
                //创建服务器类型  多线程
                TServer server = new TThreadPoolServer(tArgs);
                //开启服务
                server.serve();
        }catch(Exception e){
                e.printStackTrace();
        }
    }

}
