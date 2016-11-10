package network;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by LOMO on 2016-11-10.
 */
public class URLDecoderTest {
    public static void main(String[] args)
            throws Exception
    {
        String keyWord= URLDecoder.decode("%E7%96%AF%E7%8B%82","utf-8");
        System.out.println(keyWord);

        String urlStr= URLEncoder.encode("疯狂Android讲义","utf-8");
        System.out.println(urlStr);
    }
}
