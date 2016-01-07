/**
 * This File is created by hztianduoduo at 2016年1月7日,any questions please have a message on the http://tian-dd.top.
 */
package com.rpc.resource;

import org.apache.thrift.TException;

import com.rpc.framework.*;

/**
 * @author hztianduoduo
 *
 */
public class PythonServiceImpl implements PythonService.Iface{

    @Override
    public String get(int id) throws TException {
        return String.valueOf(id);
    }

}
