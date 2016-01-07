namespace java com.rpc.framework

service Hello{

       string hello(1:string name),
       i32 add(1:i32 a, 2:i32 b)
  
}
