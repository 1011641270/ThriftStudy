package thrift.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	private static Logger logger = Logger.getLogger(UserDao.class);
	public String getUserName(String uid){
		logger.info("dao run --------");
		return "getUser test=="+uid;
	}
}
