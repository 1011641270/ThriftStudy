package thrift.common;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftPoolableObjectFactory implements PoolableObjectFactory{

	/** 日志记录器 */
	public static final Logger logger = LoggerFactory.getLogger(ThriftPoolableObjectFactory.class);
	
	/** 服务的IP */
	private String serviceIP;
	/** 服务的端口 */
	private int servicePort;
	/** 超时设置 */
	private int timeOut;
	
	public ThriftPoolableObjectFactory(String serviceIP, int servicePort,
			int timeOut) {
		super();
		this.serviceIP = serviceIP;
		this.servicePort = servicePort;
		this.timeOut = timeOut;
	}

	/**
	 * 激活对象
	 */
	@Override
	public void activateObject(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 销毁对象
	 */
	@Override
	public void destroyObject(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		if (arg0 instanceof TSocket){
			TSocket socket = (TSocket) arg0;
			if (socket.isOpen()){
				socket.close();
			}
		}
	}

	/**
	 * 创建个性化对象
	 */
	@Override
	public Object makeObject() throws Exception {
		// TODO Auto-generated method stub		
		try{
			TTransport transport = new TSocket(this.serviceIP,
			this.servicePort, this.timeOut);
			transport.open();
			return transport;
		}catch (Exception e){
			logger.error("error ThriftPoolableObjectFactory()", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void passivateObject(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 检验对象是否可以由pool安全返回
	 */
	@Override
	public boolean validateObject(Object arg0) {
		// TODO Auto-generated method stub
		try{
			if (arg0 instanceof TSocket){
				TSocket thriftSocket = (TSocket) arg0;
				if (thriftSocket.isOpen()){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}catch (Exception e){
			return false;
		}
	}

	public String getServiceIP() {
		return serviceIP;
	}

	public void setServiceIP(String serviceIP) {
		this.serviceIP = serviceIP;
	}

	public int getServicePort() {
		return servicePort;
	}

	public void setServicePort(int servicePort) {
		this.servicePort = servicePort;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

}

