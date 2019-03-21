package pojo;

import java.io.Serializable;

public class RpcRequest implements Serializable{
    private String className;//调用的类
    private String methodName;//调用的方法
    private Object[]args;//调用方法的参数
    private Class<?>[] parameterTypes;//方法参数的类型

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
