package network;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.RandomAccess;

/**
 * Created by LOMO on 2016-11-10.
 */
public class DownUtil {
    private String path;//定义下载资源的路径
    private String targetFile;//指定所下载资源的保存位置
    private int threadNum;//定义需要多少个线程来下载资源
    private DownThread[] threads;//定义下载的线程对象
    private int fileSize;//下载文件总大小

    public DownUtil(String path,String targetFile,int threadNum)
    {
        this.path=path;
        this.targetFile=targetFile;
        this.threadNum=threadNum;
        threads=new DownThread[threadNum];

    }


    public void download() throws Exception{

        URL url=new URL(path);//初始化URL,
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();//代表与URL所引用的远程对象的连接
        conn.setConnectTimeout(5*1000);//超时

        //设置HTTP请求的相关属性，包括请求方式、接收文件、接收语言、字符集、连接方式
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept","image/gif,image/jpeg,image/pjpeg,image/pjpeg," +
                "application/x-shockwave-flash,application/xaml+xml," +
                "application/vnd.ms-xpsdocument,application/x-ms-xbap," +
                "application/x-ms-application,application/vnd.ms-excel," +
                "application/vnd.ms-powerpoint,application/msword,*/*");
        conn.setRequestProperty("Accept-Language","zh-CN");
        conn.setRequestProperty("Charset","UTF-8");
        conn.setRequestProperty("Connection","keep-Alive");//keep_Alive保证tcp连接可以使用多次

        fileSize=conn.getContentLength();//得到文件大小
        conn.disconnect();//断开连接
        int currentPartSize=fileSize/threadNum+1;//每个线程下载的文件块大小

        //设置本地文件
        RandomAccessFile file=new RandomAccessFile(targetFile,"rw");
        file.setLength(fileSize);//本地文件大小
        file.close();

        for (int i=0;i<threadNum;i++){
            int startPos=i*currentPartSize;//计算每个线程下载的开始位置
            RandomAccessFile currentPart=new RandomAccessFile(targetFile,"rw");//每个线程使用一个RandomAccessFile进行下载
            currentPart.seek(startPos);//定位该线程的下载位置
            //创建下载线程
            threads[i]=new DownThread(startPos,currentPartSize,currentPart);
            //启动下载线程
            threads[i].start();


        }
    }
    //返回完成百分比
    public double getCompleteRate()
    {
        int sumSize=0;
        for (int i=0;i<threadNum;i++){
            sumSize+=threads[i].length;

        }
        return sumSize*1.0/fileSize;
    }
    private class DownThread extends Thread
    {
        private int startPos;//当前线程下载位置
        private int currentPartSize;//定义当前线程负责下载的文件大小
        private RandomAccessFile currentPart;//当前线程需要下载的文件块
        public int length;//定义该线程下载的字节数
        public DownThread(int startPos,int currentPartSize,RandomAccessFile currentPart){
            this.startPos=startPos;
            this.currentPartSize=currentPartSize;
            this.currentPart=currentPart;

        }

        @Override
        public void run() {
            try
            {
                URL url=new URL(path);//初始化URL,
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();//与URL所引用的远程对象的连接
                conn.setConnectTimeout(5*1000);//超时

                //设置HTTP请求的相关属性，包括请求方式、接收文件、接收语言、字符集、连接方式
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept","image/gif,image/jpeg,image/pjpeg,image/pjpeg," +
                        "application/x-shockwave-flash,application/xaml+xml," +
                        "application/vnd.ms-xpsdocument,application/x-ms-xbap," +
                        "application/x-ms-application,application/vnd.ms-excel," +
                        "application/vnd.ms-powerpoint,application/msword,*/*");
                conn.setRequestProperty("Accept-Language","zh-CN");
                conn.setRequestProperty("Charset","UTF-8");

                //从连接获得输入流，
                InputStream inStream=conn.getInputStream();
                inStream.skip(this.startPos);//跳过startPos个字节，表明该线程只下载自己负责的那部分文件
                byte[] buffer=new byte[1024];//缓冲区大小为1024个字节
                int hasRead=0;//读取缓冲区大小
                //若缓冲区没有读到数据，则范围hasRead=-1
                while(length<currentPartSize&&(hasRead=inStream.read(buffer))!=-1){
                    currentPart.write(buffer,0,hasRead);
                    //累计该线程下载的总大小
                    length+=hasRead;
                }
                currentPart.close();//关闭当前文件块
                inStream.close();//关闭文件流

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
