package thrift.common;

import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionManager{
	
	/** 日志记录器 */
	public Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
	/** 保存local对象 */
	ThreadLocal<TSocket> socketThreadSafe = new ThreadLocal<TSocket>();

	/** 连接提供池 */
	public ConnectionProvider connectionProvider;
	
	/**
	* 取socket
	* @return TSocket
	*/
	public TSocket getSocket(){
		TSocket socket = null;
		try{
			socket = connectionProvider.getConnection();
			socketThreadSafe.set(socket);
			
			return socketThreadSafe.get();
		}catch (Exception e){
			logger.error("error ConnectionManager.invoke()", e);
		}finally{
			connectionProvider.returnCon(socket);
			socketThreadSafe.remove();
		}
		
		return socket;
	}

	public ConnectionProvider getConnectionProvider() {
		return connectionProvider;
	}

	public void setConnectionProvider(ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

}

