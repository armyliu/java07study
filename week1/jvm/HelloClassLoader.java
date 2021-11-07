package jvm;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 第一周作业:
 2.（必做）自定义一个 Classloader，加载一个 Hello.xlass 文件
 此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。
 */
public class HelloClassLoader extends ClassLoader {
    public static void main( String [] args ) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String file = "Hello.xlass";

        ClassLoader classLoader = new HelloClassLoader();
        Class<?> clazz = classLoader.loadClass( file );
        Method[] method = clazz.getDeclaredMethods();
        for(Method m : method){
            System.out.println("方法名" + m.getName());
        }

        String methodName = "hello";
        Object object = clazz.getDeclaredConstructor().newInstance();
        Method helloMethod = clazz.getMethod(methodName);
        helloMethod.invoke(object);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(name);
        try{
            int fileLength = inputStream.available();
            byte[] datas = new byte[fileLength];
            int len = inputStream.read(datas);
            if( len != fileLength ){
                throw new RuntimeException("文件读取异常");
            }
            String fileName = getFileName(name);
            // 转换
            byte[] newdatas = decode(datas);
            return defineClass(fileName, newdatas, 0, len);

        }catch (Exception e){
            System.out.println("findclass exception:"+ e.getMessage());
            return null;
        }
    }

    private byte[] decode(byte[] datas) {
        if( null == datas || datas.length==0){
            return new byte[0];
        }
        byte[] newdatas = new byte[datas.length];
        for(int i=0;i<datas.length;i++){
            int value = datas[i];
            newdatas[i] = (byte)(255 - value);
        }
        return newdatas;
    }

    private static String getFileName(String name) {
        return name.substring(0, name.indexOf(".xlass"));
    }


}