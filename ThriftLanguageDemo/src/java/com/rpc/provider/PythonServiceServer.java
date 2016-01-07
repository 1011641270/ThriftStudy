/**
 * This File is created by hztianduoduo at 2016年1月6日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.provider;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

import com.rpc.framework.PythonService;
import com.rpc.resource.PythonServiceImpl;

/**
 * @author hztianduoduo
 *
 */
public class PythonServiceServer {
    
    public static int port = 8081; 
    
    public static void main(String[] args) throws Exception{

        TProcessor tProcessor = new PythonService.Processor<PythonService.Iface>(new PythonServiceImpl());
       
        TServerSocket serverSocket = new TServerSocket(port);
        TServer.Args tArgs = new TServer.Args(serverSocket);
        
        tArgs.processor(tProcessor);
        tArgs.protocolFactory(new TBinaryProtocol.Factory());

        TServer server = new TSimpleServer(tArgs);
        server.serve();

    }

}
