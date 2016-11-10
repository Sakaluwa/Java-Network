# Java-Network
java网络编程，多线程,下载器

1. URLConnection对象表示应用程序和URL之间的通信连接，HttpURLConnection对象代表应用程序和URL之间的HTTP连接，程序可以通过URLConnection实例向该URL发送 请求、读取URL引用的资源；
2. URLDecoder和URLEncoder用于完成普通字符串同application/x-www-form-urlencoded MIME字符串之间的转换；不同字符集的中文对应的字节数不一样（utf-8是3个，GBK是2个）；
3. InetAddress类提供了一个isReachable()方法，用于测试是否可以到达该地址。该方法将尽最大努力尝试到达主机，但防火墙和服务器配置可能阻塞请求，使得它在访问某些特定端口时处于不可达状态。
