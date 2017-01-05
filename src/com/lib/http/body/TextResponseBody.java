package com.lib.http.body;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.lib.connect.Watcher;

public class TextResponseBody implements ResponseBody {
	private final Watcher watcher;
	private final String charset;
	private String result;

	public TextResponseBody(Watcher watcher, String charset) {
		this.watcher = watcher;
		this.charset = charset;
	}

	@Override
	public void read(InputStream inStream) throws UnsupportedEncodingException, IOException {
		byte[] buffer = new byte[inStream.available()];
		inStream.read(buffer);
		result = new String(buffer, charset);
		if (watcher != null) {
			watcher.watcher(result);
		}
	}

	public String getResult() {
		return result;
	}
}
