package server.impl;

import server.RpcService;
import service.HelloService;
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String test(String message) {
        return "i am server:" + message;
    }

    @Override
    public String test(String message, Integer num) {
        return message + num;
    }
}
