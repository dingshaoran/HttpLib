package com.lib.http.request;

public class RequestLine {
	private final String methord;
	private final String uri;
	private final String version;

	private RequestLine(Builder builder) {
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

	public String getLine() {
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
		return stringBuilder.toString();
	}

	public static class Builder {
		private String methord;
		private String uri;
		private String version;

		public void setMethord(String methord) {
			this.methord = methord;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public RequestLine build() {
			return new RequestLine(this);
		}
	}
}
