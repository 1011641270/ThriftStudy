/**
 * This File is created by hztianduoduo at 2016年1月5日,any questions please have a
 * message on the http://tian-dd.top.
 */
package com.rpc.framework;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcFramework {

    /**
     * 暴露服务
     * 
     * @param service
     *            服务实现
     * @param port
     *            服务端口
     * @throws Exception
     */
    public static void export(final Object service, int port) throws Exception {
        @SuppressWarnings("resource")
        ServerSocket server = new ServerSocket(port);
        //一直提供服务
        for (;;) {
            try {
                final Socket socket = server.accept();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            try {
                                ObjectInputStream input = new ObjectInputStream(
                                        socket.getInputStream());
                                try {
                                    String methodName = input.readUTF();
                                    Class<?>[] parameterTypes = (Class<?>[]) input
                                            .readObject();
                                    Object[] arguments = (Object[]) input
                                            .readObject();
                                    ObjectOutputStream output = new ObjectOutputStream(
                                            socket.getOutputStream());
                                    try {
                                        Method method = service.getClass()
                                                .getMethod(methodName,
                                                        parameterTypes);
                                        Object result = method.invoke(service,arguments);
                                        output.writeObject(result);
                                    } catch (Throwable t) {
                                        output.writeObject(t);
                                    } finally {
                                        output.close();
                                    }
                                } finally {
                                    input.close();
                                }
                            } finally {
                                socket.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 引用服务
     * 
     * @param <T>
     *            接口泛型
     * @param interfaceClass
     *            接口类型
     * @param host
     *            服务器主机名
     * @param port
     *            服务器端口
     * @return 远程服务
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T refer(final Class<T> interfaceClass, final String host,
            final int port) throws Exception {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[] {
                    interfaceClass
        }, new InvocationHandler() {
            public Object invoke(Object proxy, Method method,
                    Object[] arguments) throws Throwable {
                Socket socket = new Socket(host, port);
                try {
                    ObjectOutputStream output = new ObjectOutputStream(
                            socket.getOutputStream());
                    try {
                        output.writeUTF(method.getName());
                        output.writeObject(method.getParameterTypes());
                        output.writeObject(arguments);
                        ObjectInputStream input = new ObjectInputStream(
                                socket.getInputStream());
                        try {
                            Object result = input.readObject();
                            if (result instanceof Throwable) {
                                throw (Throwable) result;
                            }
                            return result;
                        } finally {
                            input.close();
                        }
                    } finally {
                        output.close();
                    }
                } finally {
                    socket.close();
                }
            }
        });
    }

}
