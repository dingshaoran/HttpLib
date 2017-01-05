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
	public static String HEADER_HOST = "Host";
	public static String HEADER_ACCEPT = "Accept";
	public static String HEADER_AUTHORIZATION = "Authorization";
	public static String HEADER_RANGE = "Range";
	public static String HEADER_ENCODING = "Accept-Encoding";
	public static String HEADER_CHARSET = "Accept-Charset";
	public static String HEADER_LANGUAGE = "Accept-Language";
	public static String HEADER_CACHE = "Cache-Control";
	public static String HEADER_DATE = "Date";
	public static String HEADER_EXPECT = "Expect";
	public static String HEADER_CONTENT_TYPE = "Content-Type";
	public static String HEADER_CONTENT_LENGTH = "Content-Length";
	public static String HEADER_CONNECTION = "Connection";
	public static String HEADER_USERAGENT = "User-Agent";
	public static String HEADER_COOKIE = "Cookie";
	public static String HEADER_IfMatch = "If-Match";
	public static String HEADER_IfNoneMatch = "If-None-Match";
	public static String HEADER_IfModifiedSince = "If-Modified-Since";
	public static String HEADER_IfRange = "If-Range";
	public static String HEADER_IfUnmodifiedSince = "If-Unmodified-Since";
	public static String HEADER_Pragma = "Pragma";
	public static String HEADER_Referer = "Referer";
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
