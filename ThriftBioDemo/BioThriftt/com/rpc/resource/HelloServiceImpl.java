/**
 * This File is created by hztianduoduo at 2016年1月6日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.resource;

import org.apache.thrift.TException;

import com.rpc.framework.Hello;

/**
 * @author hztianduoduo
 *
 */
public class HelloServiceImpl implements Hello.Iface{
    
    @Override
    public String hello(String name) throws TException {
        return "Hello" + name;
    }

    @Override
    public int add(int a, int b) throws TException {
        return a+b;
    }

}
