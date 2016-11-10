package network;

/**
 * Created by LOMO on 2016-11-10.
 */
public class MultiThreadDown {
    public static void main(String[] args) throws Exception{
        final DownUtil downUtil=new DownUtil("http://imgsrc.baidu.com/forum/w%3D580/sign=1bb46cee78f0f736d8fe4c093a54b382/5e63219759ee3d6de52bdd5a43166d224e4ade14.jpg",
                "ios.jpg",4);
        downUtil.download();
        new Thread(()->{
            while (downUtil.getCompleteRate()<1){
                System.out.println("已完成："+downUtil.getCompleteRate());
                try{
                    Thread.sleep(100);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }).start();

    }
}
