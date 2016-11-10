package network;

import java.net.InetAddress;

/**
 * Created by LOMO on 2016-11-10.
 */
public class InetAddressTest {
    //throws针对一个方法抛出抛出一个系统定义的异常,调用该方法的其他方法需处理该异常
    public static void main(String[] args) throws Exception{
        InetAddress ip=InetAddress.getByName("www.crazyit.org");
        System.out.println("crazyit是否可达："+ip.isReachable(2000));
        System.out.println(ip.getHostAddress());
        InetAddress local=InetAddress.getByAddress(new byte[]{127,0,0,1});
        System.out.println("本机是否可以到达："+local.isReachable(2000));
        System.out.println(local.getCanonicalHostName());

    }
}
