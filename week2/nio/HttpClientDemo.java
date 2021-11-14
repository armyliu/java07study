package week2.nio;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author liujijun
 * @date 2021/11/14 - 17:44
 **/
public class HttpClientDemo {

    public static void main(String [] args) {
        /**
         * 创建 HttpClient 对象。
         * 创建请求方法的实例，并指定请求 URL。
         *      如果需要发送 GET 请求，创建 HttpGet 对象；如果需要发送 POST 请求，创建 HttpPost 对象。
         * 如果需要发送请求参数，可调用 HttpGet、HttpPost 共同的 setParams(HttpParams params) 方法来添加请求参数；对于 HttpPost 对象而言，也可调用 setEntity(HttpEntity entity) 方法来设置请求参数。
         * 调用 HttpClient 对象的 execute(HttpUriRequest request) 发送请求，该方法返回一个 HttpResponse。
         * 调用 HttpResponse 的 getAllHeaders()、getHeaders(String name) 等方法可获取服务器的响应头；调用 HttpResponse 的 getEntity() 方法可获取 HttpEntity 对象，该对象包装了服务器的响应内容。程序可通过该对象获取服务器的响应内容。
         * 释放连接。无论执行方法是否成功，都必须释放连接
         */
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try{
            HttpGet httpGet = new HttpGet("http://localhost:8801");
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));
        } catch (IOException e) {
            System.out.println("IO异常："+e.getMessage());
        }catch (Exception e){
            System.out.println("其他异常："+e.getMessage());
        }finally {
            try {
                if ( null != response) {
                    response.close();
                }
            } catch (IOException e) {
                System.out.println("流关闭异常："+e.getMessage());
            }
            try{
                if( null != httpClient ){
                    httpClient.close();
                }
            }catch (IOException e){
                System.out.println("流关闭异常："+e.getMessage());
            }

        }

    }
}
