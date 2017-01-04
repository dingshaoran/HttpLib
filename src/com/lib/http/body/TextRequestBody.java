package com.lib.http.body;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.lib.connect.Watcher;
import com.lib.utils.LogUtils;

public class TextRequestBody implements RequestBody {
	private final TreeMap<String, String> mData;
	private final String charset;
	private byte[] mBody;
	private final Watcher watcher;

	public TextRequestBody(Watcher watcher, String charset) {
		mData = new TreeMap<String, String>();
		this.charset = charset;
		this.watcher = watcher;
	}

	public TextRequestBody(Watcher watcher, String charset, Comparator<String> cmp) {
		mData = new TreeMap<String, String>(cmp);
		this.charset = charset;
		this.watcher = watcher;
	}

	public String put(String key, String value) {
		return mData.put(key, value);
	}

	public String get(String key) {
		return mData.get(key);
	}

	@Override
	public void prepare() throws UnsupportedEncodingException {
		StringBuilder bilder = new StringBuilder(30 * mData.size());
		for (Entry<String, String> entry : mData.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (key != null && value != null) {
				bilder.append(key).append("=").append(value).append("&");
			} else {
				LogUtils.e("TextRequestBody", key + "   header item error   " + value);
			}
		}
		int length = bilder.length();
		String string = bilder.substring(0, length == 0 ? 0 : length - 1);
		if (watcher != null) {
			watcher.watcher(string);
		}
		this.mBody = string.getBytes(charset);
	}

	@Override
	public long length() {
		return mBody.length;
	}

	@Override
	public void write(OutputStream outStream) throws UnsupportedEncodingException, IOException {
		outStream.write(mBody);
	}
}
