# coding=utf-8
'''
Created on 2013-9-22

@author: hanqunfeng
'''

import sys
sys.path.append('../') #导入上下文环境

from servicePy import  PythonService
from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.protocol import TCompactProtocol

def pythonServerExe():
    try:
        transport = TSocket.TSocket('localhost', 8081) 
        transport = TTransport.TBufferedTransport(transport)
        protocol = TBinaryProtocol.TBinaryProtocol(transport)
        #protocol = TCompactProtocol.TCompactProtocol(transport)
        client = PythonService.Client(protocol)
        transport.open()
        print "The return value is : " 
        print client.get(100)
        print "............"
        transport.close()
    except Thrift.TException, tx:
        print '%s' % (tx.message)
        
        
if __name__ == '__main__':
    pythonServerExe()