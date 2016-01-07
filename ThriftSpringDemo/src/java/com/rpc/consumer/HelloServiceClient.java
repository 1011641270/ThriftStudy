/**
 * This File is created by hztianduoduo at 2016年1月6日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.consumer;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rpc.framework.HelloService;

/**
 * @author hztianduoduo
 *
 */

public class HelloServiceClient {
    
    private int port;
    private Map<String, String> serviceMap;
    private Map<String, Object> clientMap;
    
    public void setPort(int port) {
            this.port = port;
    }

    public void setServiceMap(Map<String, String> serviceMap) {
            this.serviceMap = serviceMap;
    }

    public Object getClient(String name){
            return clientMap.get(name);
    }
    
    public void init(){
            clientMap = new HashMap<String, Object>();
            try {
                    TTransport transport = new TSocket("localhost", port);
                    TProtocol protocol = new TBinaryProtocol(transport);
                    for(Map.Entry<String, String> entry : serviceMap.entrySet()){
                            String obj = entry.getValue();
                            System.out.println(entry.getKey() + " " + entry.getValue());
                            TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,
                                            entry.getKey());
                            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                            Class<?> objectClass = classLoader.loadClass(obj + "$Client");

                            Constructor<?> stor = objectClass.getDeclaredConstructor(TProtocol.class);
                            Object client = stor.newInstance(mp);
                            clientMap.put(entry.getKey(), client);
                    }

                    transport.open();

            } catch (Exception x) {
                    x.printStackTrace();
            }
    }
    public void close(){
            
    }
    
    @SuppressWarnings("resource")
    public static void main(String[] args){
        try {

            ApplicationContext context = new ClassPathXmlApplicationContext(
                    "spring-context-thrift-client.xml");
            
            HelloServiceClient springClient = (HelloServiceClient) context
                    .getBean("thriftServer");
            
            HelloService.Client helloClient = (HelloService.Client) springClient
                    .getClient("HelloService");
                        
            System.out.println(helloClient.hello("world"));
            System.out.println(helloClient.add(1, 2));

        } catch (Exception e) {
                    e.printStackTrace();
            }
    }

}
