package server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.RpcRequest;
import server.impl.HelloServiceImpl;
import server.impl.TestServiceImpl;
import util.SerializeUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerThread extends Thread {
    //private Map<String, Class> service = new HashMap<>();

    private Socket client;

    public ServerThread() {
    }
    private ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring.xml");
    public ServerThread(Socket client) {
        this.client = client;

//        service.put("service.HelloService", HelloServiceImpl.class);
//        service.put("service.TestService", TestServiceImpl.class);
    }

    @Override
    public void run() {
        OutputStream output = null;
        InputStream input = null;
        try {
            output = client.getOutputStream();
            input = client.getInputStream();
            // 进行循环的发送和接收消息
            while (true) {
                byte[] arr = new byte[1024];// 接收客户端发送来的消息
                int len = input.read(arr);
                System.out.println(len);
                RpcRequest rpcRequest = (RpcRequest) SerializeUtil.unserialize(arr);
                //Class clazz = Class.forName(service.get(rpcRequest.getClassName()).getName());
                Class clazz = Class.forName(rpcRequest.getClassName());
                Method method = clazz.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
               // Object target = clazz.newInstance();
                Object target=applicationContext.getBean(clazz);
                Object result = method.invoke(target, rpcRequest.getArgs());
                output.write(SerializeUtil.serialize(result));
                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
