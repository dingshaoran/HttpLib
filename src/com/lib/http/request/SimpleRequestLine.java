package com.lib.http.request;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class SimpleRequestLine implements RequestLine {
	private final String methord;
	private final String uri;
	private final String version;
	private final String charset;

	private SimpleRequestLine(Builder builder) {
		this.charset = builder.charset;
		this.methord = builder.methord;
		this.uri = builder.uri;
		this.version = builder.version;
	}

	public String getMethord() {
		return methord;
	}

	public String getUri() {
		return uri;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public void write(OutputStream outStream) throws UnsupportedEncodingException, IOException {
		if (methord == null) {
			throw new NullPointerException("请设置那种请求类型");
		}
		StringBuilder stringBuilder = new StringBuilder(100);
		stringBuilder.append(methord)
				.append(" ")
				.append(uri == null ? "" : uri)
				.append(" ")
				.append("HTTP/")
				.append(version == null ? "1.1" : version);
		outStream.write(stringBuilder.toString().getBytes(charset));
	}

	public static class Builder {
		private final String charset;
		private String methord;
		private String uri;
		private String version;

		public Builder(String charset) {
			this.charset = charset;
		}

		public void setMethord(String methord) {
			this.methord = methord;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public SimpleRequestLine build() {
			return new SimpleRequestLine(this);
		}
	}
}
