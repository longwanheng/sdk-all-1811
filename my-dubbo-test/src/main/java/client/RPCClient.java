package client;

import server.RpcProxy;
import service.HelloService;

public class RPCClient {
    public static void main(String[] args) throws Exception{
        RpcProxy proxy = new RpcProxy();
        HelloService service =(HelloService) proxy.getProxyInstance(HelloService.class);
        System.out.println(service.test("hahahahhaha"));;
        proxy.destroy();
    }

}
