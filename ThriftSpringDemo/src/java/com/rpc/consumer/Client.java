/**
 * This File is created by hztianduoduo at 2016年1月7日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.consumer;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.rpc.framework.HelloService;

/**
 * @author hztianduoduo
 *
 */
public class Client {
    
    public static void main(String[] args) throws TException {
        
        TTransport transport = new TSocket("localhost", 7911);
        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,
                        "HelloService");
        
        HelloService.Client helloClient = new HelloService.Client(mp1);
        
        transport.open();
        
        System.err.println(helloClient.hello("world"));
        
    }

}
