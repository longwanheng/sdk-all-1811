package util;

import java.io.*;

/**
 * 序列化工具，可以替换成第三方
 */
public class SerializeUtil {
    //序列化
    public static byte[] serialize(Object o) throws IOException {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            oos.close();
            baos.close();
        }
        return null;
    }
    // 反序列化
    public static Object unserialize(byte[] bytes)throws IOException {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bais.close();
            ois.close();
        }
        return null;
    }

}
