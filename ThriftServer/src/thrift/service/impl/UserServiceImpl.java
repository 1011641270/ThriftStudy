package thrift.service.impl;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thrift.dao.UserDao;
import thrift.service.UserService;

@Service
public class UserServiceImpl implements UserService.Iface{
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDao dao;

	@Override
	public void getUser(String uid) throws TException {
		// TODO Auto-generated method stub
		logger.info("userservice  run----- getUser");
		logger.info(dao.getUserName(uid));
	}

	@Override
	public String getUserName(String uid, int age) throws TException {
		// TODO Auto-generated method stub
		logger.info("userservice  run----- getUser");
		return "uid==" + uid + "|| age==" + age;
	}

	
}
