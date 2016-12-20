package com.lib.http.request;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.lib.connect.Engine;
import com.lib.utils.LogUtils;

public class SimpleRequestHeader implements RequestHeader {
	private final HashMap<String, String> map;
	private final String charset;
	public static String HEADER_HOST = "Host";
	public static String HEADER_RANGE = "Range";
	public static String HEADER_ENCODING = "Accept-Encoding";
	public static String HEADER_CONTENT_TYPE = "Content-Type";
	public static String HEADER_CONTENT_LENGTH = "Content-Length";
	public static String HEADER_CONNECTION = "Connection";
	public static String HEADER_USERAGENT = "User-Agent";

	public SimpleRequestHeader(Builder builder) {
		this.charset = builder.charset;
		this.map = builder.map;
	}

	public String getHost() {
		return map.get(HEADER_HOST);
	}

	public String getAcceptEncode() {
		return map.get(HEADER_ENCODING);
	}

	public String getContentType() {
		return map.get(HEADER_CONTENT_TYPE);
	}

	public String getConnection() {
		return map.get(HEADER_CONNECTION);
	}

	public String getContentLength() {
		return map.get(HEADER_CONTENT_LENGTH);
	}

	public String getUserAgent() {
		return map.get(HEADER_USERAGENT);
	}

	@Override
	public void write(OutputStream outStream) throws UnsupportedEncodingException, IOException {
		StringBuilder stringBuilder = new StringBuilder(50);
		for (Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (key != null && value != null) {
				stringBuilder.append(key).append(": ").append(value).append(Engine.CRLF);
				outStream.write(stringBuilder.toString().getBytes(charset));
				stringBuilder.replace(0, stringBuilder.length(), "");
			} else {
				LogUtils.e(key + "   header item error   " + value);
			}
		}
	}

	public static class Builder {

		private final String charset;
		private final HashMap<String, String> map = new HashMap<String, String>();

		public Builder(String charset) {
			this.charset = charset;
		}

		public void setHost(String host) {
			map.put(HEADER_HOST, host);
		}

		public void setRange(String range) {
			map.put(HEADER_RANGE, range);
		}

		public void setAcceptEncode(String encode) {
			map.put(HEADER_ENCODING, encode);
		}

		public void setContentType(String contentType) {
			map.put(HEADER_CONTENT_TYPE, contentType);
		}

		public void setConnection(String connection) {
			map.put(HEADER_CONNECTION, connection);
		}

		public void setContentLength(String contentLength) {
			map.put(HEADER_CONTENT_LENGTH, contentLength);
		}

		public void setUserAgent(String userAgent) {
			map.put(HEADER_USERAGENT, userAgent);
		}

		public void setOtherhead(String key, String value) {
			map.put(key, value);
		}

		public SimpleRequestHeader build() {
			return new SimpleRequestHeader(this);
		}
	}
}
