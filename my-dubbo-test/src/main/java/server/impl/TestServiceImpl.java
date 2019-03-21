package server.impl;

import server.RpcService;
import service.HelloService;
import service.TestService;
@RpcService(TestService.class)
public class TestServiceImpl implements TestService {
    @Override
    public void hello() {
        System.out.println("调用了TestServiceImpl方法");
    }
}
