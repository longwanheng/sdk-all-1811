package server;

import pojo.RpcRequest;
import util.SerializeUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.net.UnknownHostException;

public class RpcProxy {
    private Socket socket = null;// 1.建立一个与服务端之间的连接
    private OutputStream output = null;// 用于向别人发消息
    private InputStream input = null;// 用于读取别人发过来的消息

    public RpcProxy() {
        try {
            socket = new Socket("127.0.0.1", 8888);
            output = socket.getOutputStream();
            input = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getProxyInstance(Class interfaceClass) throws Exception {
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("我是代理");
                RpcRequest rpcRequest = new RpcRequest();
                rpcRequest.setClassName(interfaceClass.getName());
                rpcRequest.setMethodName(method.getName());
                rpcRequest.setArgs(args);
                rpcRequest.setParameterTypes(method.getParameterTypes());
                try {
                    output.write(SerializeUtil.serialize(rpcRequest));// 进行发送
                    output.flush();
                    byte[] arr = new byte[1024];// 接收消息
                    int len = input.read(arr);
                    Object result = SerializeUtil.unserialize(arr);
                    System.out.println(result);
                    return result;

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

}
