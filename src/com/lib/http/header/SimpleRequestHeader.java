package com.lib.http.header;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.lib.connect.RequestEngine;
import com.lib.connect.Watcher;
import com.lib.utils.LogUtils;

public class SimpleRequestHeader implements RequestHeader {
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

	private final HashMap<String, String> map;
	private final String charset;
	@SuppressWarnings("unused")
	private final Watcher watcher;

	public SimpleRequestHeader(Builder builder) {
		this.charset = builder.charset;
		this.map = builder.map;
		this.watcher = builder.watcher;
	}

	public String getHost() {
		return map.get(HEADER_HOST);
	}

	public String getAccept() {
		return map.get(HEADER_ACCEPT);
	}

	public String getRange() {
		return map.get(HEADER_RANGE);
	}

	public String getAuthorization() {
		return map.get(HEADER_AUTHORIZATION);
	}

	public String getAcceptEncode() {
		return map.get(HEADER_ENCODING);
	}

	public String getAcceptCharset() {
		return map.get(HEADER_CHARSET);
	}

	public String getAcceptLanguage() {
		return map.get(HEADER_LANGUAGE);
	}

	public String getAcceptCache() {
		return map.get(HEADER_CACHE);
	}

	public String getAcceptDate() {
		return map.get(HEADER_DATE);
	}

	public String getAccepExpect() {
		return map.get(HEADER_EXPECT);
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

	public String getCookie() {
		return map.get(HEADER_COOKIE);
	}

	public String getIfMatch() {
		return map.get(HEADER_IfMatch);
	}

	public String getIfNoneMatch() {
		return map.get(HEADER_IfNoneMatch);
	}

	public String getIfModifiedSince() {
		return map.get(HEADER_IfModifiedSince);
	}

	public String getIfRange() {
		return map.get(HEADER_IfRange);
	}

	public String getIfUnmodifiedSince() {
		return map.get(HEADER_IfUnmodifiedSince);
	}

	public String getPragma() {
		return map.get(HEADER_Pragma);
	}

	public String getReferer() {
		return map.get(HEADER_Referer);
	}

	@Override
	public void write(OutputStream outStream) throws UnsupportedEncodingException, IOException {
		StringBuilder stringBuilder = new StringBuilder(50);
		for (Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (key != null && value != null) {
				stringBuilder.append(key).append(":").append(value).append(RequestEngine.CRLF);
				outStream.write(stringBuilder.toString().getBytes(charset));
				stringBuilder.replace(0, stringBuilder.length(), "");
			} else {
				LogUtils.e("SimpleRequestHeader", key + "   header item error   " + value);
			}
		}
	}

	public static class Builder {

		private final String charset;
		private final HashMap<String, String> map = new HashMap<String, String>();
		private final Watcher watcher;

		public Builder(Watcher watcher, String charset) {
			this.charset = charset;
			this.watcher = watcher;
		}

		public void setHost(String host) {
			map.put(HEADER_HOST, host);
		}

		public void setAccept(String accept) {
			map.put(HEADER_ACCEPT, accept);
		}

		public void setRange(String range) {
			map.put(HEADER_RANGE, range);
		}

		public void setAuthorization(String authorization) {
			map.put(HEADER_AUTHORIZATION, authorization);
		}

		public void setAcceptCharset(String charset) {
			map.put(HEADER_CHARSET, charset);
		}

		public void setAcceptLanguage(String lang) {
			map.put(HEADER_LANGUAGE, lang);
		}

		public void setAcceptCache(String cache) {
			map.put(HEADER_CACHE, cache);
		}

		public void setDate(String date) {
			map.put(HEADER_DATE, date);
		}

		public void setExpect(String expect) {
			map.put(HEADER_EXPECT, expect);
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

		public void setCookie(String cookie) {
			map.put(HEADER_COOKIE, cookie);
		}

		public void setIfMatch(String match) {
			map.put(HEADER_IfMatch, match);
		}

		public void setIfNoneMatch(String match) {
			map.put(HEADER_IfNoneMatch, match);
		}

		public void setIfModifiedSince(String modify) {
			map.put(HEADER_IfModifiedSince, modify);
		}

		public void setIfRange(String range) {
			map.put(HEADER_IfRange, range);
		}

		public void setIfUnmodifiedSince(String modify) {
			map.put(HEADER_IfUnmodifiedSince, modify);
		}

		public void setPragma(String pragma) {
			map.put(HEADER_Pragma, pragma);
		}

		public void setReferer(String ref) {
			map.put(HEADER_Referer, ref);
		}

		public void setOtherhead(String key, String value) {
			map.put(key, value);
		}

		public SimpleRequestHeader build() {
			return new SimpleRequestHeader(this);
		}
	}
}
