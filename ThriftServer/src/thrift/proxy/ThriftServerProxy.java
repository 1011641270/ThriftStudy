package thrift.proxy;

import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import thrift.ThriftServerStartListener;

public class ThriftServerProxy {
	
	
	private static Logger logger = Logger.getLogger(ThriftServerStartListener.class);
	private int port;// 端口
	private String serviceInterface;// 实现类接口
	
	private Object serviceImplObject;//实现类

	public Object getServiceImplObject() {
		return serviceImplObject;
	}

	public void setServiceImplObject(Object serviceImplObject) {
		this.serviceImplObject = serviceImplObject;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	

	public String getServiceInterface() {
		return serviceInterface;
	}

	public void setServiceInterface(String serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void start() {
		new Thread() {
			public void run() {

				try {
					TServerSocket serverTransport = new TServerSocket(getPort());					
					Class Processor = Class.forName(getServiceInterface()+"$Processor");
					Class Iface = Class.forName(getServiceInterface()+"$Iface");
					
					
					Constructor con = Processor.getConstructor(Iface);
					TProcessor processor = (TProcessor)con.newInstance(serviceImplObject);

					Factory protFactory = new TBinaryProtocol.Factory(true,
							true);
					TThreadPoolServer.Args args = new TThreadPoolServer.Args(
							serverTransport);
					args.protocolFactory(protFactory);
					
					args.processor(processor);
					TServer server = new TThreadPoolServer(args);
					logger.info("Starting server on port "+getPort()+" ...");
					server.serve();

				} catch (TTransportException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	
		

}
