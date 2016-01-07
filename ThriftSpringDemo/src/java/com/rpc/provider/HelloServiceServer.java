/**
 * This File is created by hztianduoduo at 2016年1月6日,any questions please have a
 * message on the http://tian-dd.top.
 */
package com.rpc.provider;

import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Constructor;
import java.util.Map;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author hztianduoduo
 */
public class HelloServiceServer {

    private Map<String, Object> serviceList;

    private int port;

    private TServerSocket serverTransport;

    public void setServiceList(Map<String, Object> serviceList) {
        this.serviceList = serviceList;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void init() {
        try {
            serverTransport = new TServerSocket(port);
            TMultiplexedProcessor mprocessor = new TMultiplexedProcessor();
            for (Map.Entry<String, Object> entry: serviceList.entrySet()) {
                Object obj = entry.getValue();
                Class<?> serviceClass = obj.getClass();
                // 获取实现类接口
                Class<?>[] interfaces = serviceClass.getInterfaces();
                TProcessor processor = null;
                String serviceName = null;
                for (Class<?> clazz: interfaces) {
                    System.out.println(
                            "ThriftServer=========" + clazz.getSimpleName());
                    String className = clazz.getEnclosingClass()
                            .getSimpleName();
                    serviceName = clazz.getEnclosingClass().getName();
                    System.out.println("serviceName=========" + serviceName
                            + " " + className);
                    String pname = serviceName + "$Processor";
                    System.out.println("pname" + pname);
                    try {
                        ClassLoader classLoader = Thread.currentThread()
                                .getContextClassLoader();
                        Class<?> pclass = classLoader.loadClass(pname);
                        if (!TProcessor.class.isAssignableFrom(pclass)) {
                            continue;
                        }
                        Constructor<?> constructor = pclass
                                .getConstructor(clazz);
                        processor = (TProcessor) constructor.newInstance(obj);
                        System.out.println("processor========="
                                + processor.getClass().getSimpleName());
                        mprocessor.registerProcessor(className, processor);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (processor == null) {
                    throw new IllegalClassFormatException(
                            "service-class should implements Iface");
                }

            }
            TServer server = new TThreadPoolServer(
                    new TThreadPoolServer.Args(serverTransport)
                            .processor(mprocessor));
            System.out.println("Starting server on port 7911 ...");
            server.serve();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        serverTransport.close();
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        try {
            
            new ClassPathXmlApplicationContext(
                    "classpath:spring-context-thrift-server.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
