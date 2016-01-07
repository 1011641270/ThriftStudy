/**
 * This File is created by hztianduoduo at 2016年1月6日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.provider;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;

import com.rpc.framework.Hello;
import com.rpc.resource.HelloServiceImpl;

/**
 * @author hztianduoduo
 *
 */
public class HelloServiceServer {
    
    public static int port = 8081; 
    
    public static void main(String[] args) {
        
      //创建processor
        TProcessor tprocessor = new Hello.Processor<Hello.Iface>(new HelloServiceImpl());
        try{
                //创建transport 非阻塞 nonblocking
                TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);
                //创建protocol 数据传输协议
                TCompactProtocol.Factory protocol = new TCompactProtocol.Factory();
                //创建transport 数据传输方式  非阻塞需要用这种方式传输
                TFramedTransport.Factory transport = new TFramedTransport.Factory();
                TNonblockingServer.Args tArgs = new TNonblockingServer.Args(serverTransport);
                tArgs.processor(tprocessor);
                tArgs.transportFactory(transport);
                tArgs.protocolFactory(protocol);
                //创建服务器 类型是非阻塞
                TServer server = new TNonblockingServer(tArgs);
                //开启服务
                server.serve();
        }catch(Exception e){
                e.printStackTrace();
        }
        
    }

}
