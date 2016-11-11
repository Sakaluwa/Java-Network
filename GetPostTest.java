package network;

import java.awt.print.PrinterGraphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by LOMO on 2016-11-11.
 */
public class GetPostTest {
    /**
     * 向指定URL发送GET请求
     * @param url 发送请求的URL
     * @param param 请求参数，格式满足name1=value1&name2=value2的形式
     * @return URL 代表远程资源的响应
     */
    public static String sendGet(String url,String param)
    {
        String result="";
        String urlName=url+"?"+param;
        try
        {
            URL realUrl=new URL(urlName);
            URLConnection conn=realUrl.openConnection();
            conn.setRequestProperty("accept","*/*");//告诉服务器，客户机支持的数据格式，*/*表示都接收
            conn.setRequestProperty("connection","keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MISE 6.0; Windows NT 5.1; sv1)");//告诉服务器客户机的软件环境
            conn.connect();//建立实际的连接
            //获取所有的响应头字段
            Map<String,List<String>> map=conn.getHeaderFields();
            for (String key:map.keySet())
            {
                System.out.println(key+"--->"+map.get(key));
            }

            try(BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8")))
            {
                String line;
                while((line=in.readLine())!=null)
                {
                    result+="\n"+line;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("发送GET请求出现异常！"+e);
            e.printStackTrace();
        }
        return result;
    }

    /**
         * 向指定URL发送POST请求
     * @param url 发送请求的URL
     * @param param 请求参数，格式满足name1=value1&name2=value2的形式
     * @return URL 代表远程资源的响应
     */
    public static String sendPost(String url,String param)
    {
        String result="";
        try
        {
            URL realUrl=new URL(url);
            URLConnection conn=realUrl.openConnection();
            conn.setRequestProperty("accept","*/*");//告诉服务器，客户机支持的数据格式，*/*表示都接收
            conn.setRequestProperty("connection","keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MISE 6.0; Windows NT 5.1; sv1)");//告诉服务器客户机的软件环境
            //发送POST请求必须设置如下两行,doIn和doOut请求头字段的值
            conn.setDoOutput(true);
            conn.setDoInput(true);

            try(
                    //获取URLConnection对象对应的输出流
                    PrintWriter out=new PrintWriter(conn.getOutputStream()))
            {
                //发送请求参数
                out.print(param);
                //flush输出流的缓冲
                out.flush();//将缓存中的输出流强制输出
            }

            try(BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8")))
            {
                String line;
                while((line=in.readLine())!=null)
                {
                    result+="\n"+line;
                }
            }

        }
        catch (Exception e)
        {
            System.out.println("发送POST请求出现异常！"+e);
            e.printStackTrace();
        }
        return  result;

    }
    //提供主方法，测试发送GET和POST请求
    public static void main(String[] args) {

        /*
        程序中发送请求的两个URL是部署在本机的Web应用，需知道如何创建Web应用，编写jsp页面
         */
        String s=GetPostTest.sendGet("http://localhost:8888/abc/a.jsp",null);
        System.out.println(s);
        //提交Web应用中的登录表单页，让程序不断地变换用户名、密码来提交登录请求，直到返回登录成功，这便是所谓的暴力破解
        String s1=GetPostTest.sendPost("http://localhost:8888/abc/login.jsp","name=crazyit.org&pass=leegang");
        System.out.println(s1);


    }

}
