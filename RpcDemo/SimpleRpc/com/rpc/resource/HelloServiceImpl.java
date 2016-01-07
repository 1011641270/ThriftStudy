/**
 * This File is created by hztianduoduo at 2016年1月5日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.resource;

/**
 * @author hztianduoduo
 *
 */
public class HelloServiceImpl implements HelloService{

    @Override
    public String hello(String name) {
        return "Hello " + name;  
    }

    @Override
    public int add(int a, int b) {
        return a+b;
    }

}
