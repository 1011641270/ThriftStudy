/**
 * This File is created by hztianduoduo at 2016年1月5日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.provider;

import com.rpc.framework.RpcFramework;
import com.rpc.resource.HelloService;
import com.rpc.resource.HelloServiceImpl;

/**
 * @author hztianduoduo
 *
 */
public class RpcProvider {
    
    public static void main(String[] args) throws Exception {  
        HelloService service = new HelloServiceImpl();  
        RpcFramework.export(service, 1234);  
    }

}
