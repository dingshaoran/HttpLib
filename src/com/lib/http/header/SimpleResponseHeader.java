package com.lib.http.header;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import com.lib.connect.RequestEngine;

public class SimpleResponseHeader implements ResponseHeader {
	public static String HEADER_ACCEPT_RANGES = "Accept-Ranges";//Accept-Ranges: bytes 表明服务器是否支持指定范围请求及哪种类型的分段请求
	public static String HEADER_AGE = "Age";//Age: 12 从原始服务器到代理缓存形成的估算时间（以秒计，非负）
	public static String HEADER_ALLOW = "Allow";//Allow: GET, HEAD   对某网络资源的有效的请求行为，不允许则返回405
	public static String HEADER_CACHE_CONTROL = "Cache-Control";//Cache-Control: no-cache   告诉所有的缓存机制是否可以缓存及哪种类型
	public static String HEADER_CONTENT_ENCODING = "Content-Encoding";//Content-Encoding: gzip 服务器返回内容压缩编码类型。
	public static String HEADER_CONTENT_LANGUAGE = "Content-Language";//Content-Language: en,zh  响应体的语言
	public static String HEADER_CONTENT_LENGTH = "Content-Length";//Content-Length: 348  响应体的长度
	public static String HEADER_CONTENT_LOCATION = "Content-Location";//Content-Location: /index.htm  请求资源可替代的备用的另一地址
	public static String HEADER_CONTENT_MD5 = "Content-MD5";//Content-MD5: Q2hlY2sgSW50ZWdyaXR5IQ==   返回资源的MD5校验值
	public static String HEADER_CONTENT_RANGE = "Content-Range";//	Content-Range: bytes 21010-47021/47022  在整个返回体中本部分的字节位置
	public static String HEADER_CONTENT_TYPE = "Content-Type";//Content-Type: text/html; charset=utf-8  返回内容的MIME类型
	public static String HEADER_CONTENT_DATE = "Date";//Date: Tue, 15 Nov 2010 08:12:31 GMT   原始服务器消息发出的时间
	public static String HEADER_ETAG = "ETag";//请求变量的实体标签的当前值
	public static String HEADER_EXPIRES = "Expires";//Expires: Thu, 01 Dec 2010 16:00:00 GMT   响应过期的日期和时间 
	public static String HEADER_LAST_MODIFIED = "Last-Modified";//Last-Modified: Tue, 15 Nov 2010 12:45:26 GMT   请求资源的最后修改时间
	public static String HEADER_LOCATION = "Location";//Location: http://www.zcmhi.com/archives/94.html  用来重定向接收方到非请求URL的位置来完成请求或标识新的资源
	public static String HEADER_PRAGMA = "Pragma";//Pragma: no-cache  包括实现特定的指令，它可应用到响应链上的任何接收方
	public static String HEADER_PROXY_AUTHENTICATE = "Proxy-Authenticate";//Proxy-Authenticate: Basic  	它指出认证方案和可应用到代理的该URL上的参数
	public static String HEADER_REFRESH = "refresh";//Refresh: 5; url=http://www.zcmhi.com/archives/94.html 	应用于重定向或一个新的资源被创造，在5秒之后重定向（由网景提出，被大部分浏览器支持）
	public static String HEADER_RETRY_AFTER = "Retry-After";//Retry-After: 120 如果实体暂时不可取，通知客户端在指定时间之后再次尝试
	public static String HEADER_SERVER = "Server";//Server: Apache/1.3.27 (Unix) (Red-Hat/Linux)   web服务器软件名称
	public static String HEADER_SET_COOKIE = "Set-Cookie";//Set-Cookie: UserID=JohnDoe; Max-Age=3600; Version=1
	public static String HEADER_TRAILER = "Trailer";//Trailer: Max-Forwards  指出头域在分块传输编码的尾部存在
	public static String HEADER_VARY = "Vary";//告诉下游代理是使用缓存响应还是从原始服务器请求
	public static String HEADER_WARNING = "Warning";//Warning: 199 Miscellaneous warning  警告实体可能存在的问题
	public static String HEADER_VIA = "Via";//	Via: 1.0 fred, 1.1 nowhere.com (Apache/1.1)     告知代理客户端响应是通过哪里发送的
	public static String HEADER_AUTHENTICATE = "WWW-Authenticate"; //表明客户端请求实体应该使用的授权方案
	public static String HEADER_TRANSFER_ENCODING = "Transfer-Encoding"; //文件传输编码
	private final ArrayList<String> mHeaders = new ArrayList<String>(10);

	@Override
	public void read(InputStream in) throws UnsupportedEncodingException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while (true) {
			String readLine = reader.readLine();
			if (RequestEngine.CRLF.equals(readLine)) {
				break;
			} else {
				mHeaders.add(readLine);
			}
		}
	}

	public void parseKVP(Map<String, String> map) {
		for (int i = 0; i < mHeaders.size(); i++) {
			String readLine = mHeaders.get(i);
			int index = readLine.indexOf(":");
			if (index > 0) {
				map.put(readLine.substring(0, index).trim(), readLine.substring(index, readLine.length()).trim());
			}
		}
	}

	public ArrayList<String> getHeaders() {
		return mHeaders;
	}
}
