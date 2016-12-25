package com.lib.http.line;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import com.lib.connect.Watcher;

public class SimpleRequestLine implements RequestLine {
	private final String methord;
	private final String uri;
	private final String version;
	private final String charset;
	private final Watcher watcher;

	private SimpleRequestLine(Builder builder) {
		this.charset = builder.charset;
		this.methord = builder.methord;
		this.uri = builder.uri;
		this.version = builder.version;
		this.watcher = builder.watcher;
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
		if (watcher != null) {
			watcher.watcher(String.valueOf(System.currentTimeMillis()));
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
		private final Watcher watcher;

		public Builder(Watcher watcher, String charset) {
			this.charset = charset;
			this.watcher = watcher;
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
