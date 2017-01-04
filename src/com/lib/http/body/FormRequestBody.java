package com.lib.http.body;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.lib.connect.RequestEngine;
import com.lib.connect.Watcher;
import com.lib.utils.LogUtils;

public class FormRequestBody implements RequestBody {
	private static final String TAG = "FormRequestBody";
	private final String PREFIX = "--";
	public String BOUNDARY = "HttpLibFormFormBoundaryS3dFgKGOhla8i0NP";
	private final TreeMap<FormRequestKey, InputStream> mData;
	private final String charset;
	private final Watcher watcher;
	private final LinkedHashMap<byte[], InputStream> mBody = new LinkedHashMap<byte[], InputStream>();

	public FormRequestBody(Watcher watcher, String charset) {
		mData = new TreeMap<FormRequestKey, InputStream>();
		this.charset = charset;
		this.watcher = watcher;
	}

	public FormRequestBody(Watcher watcher, String charset, Comparator<FormRequestKey> cmp) {
		mData = new TreeMap<FormRequestKey, InputStream>(cmp);
		this.charset = charset;
		this.watcher = watcher;
	}

	public InputStream putText(String key, String value) {
		try {
			return mData.put(new FormRequestKey(key), new ByteArrayInputStream(value.getBytes(charset)));
		} catch (Exception e) {
			LogUtils.e(TAG, e);
		}
		return null;
	}

	public InputStream put(String key, File file) {
		try {
			String fileName = file.getName();
			return mData
					.put(new FormRequestKey(key).setFileName(file.getName()).setContentTypeSuffix(fileName.substring(fileName.lastIndexOf(".") + 1)), new FileInputStream(file));
		} catch (Exception e) {
			LogUtils.e(TAG, e);
		}
		return null;
	}

	public InputStream put(String key, String suffix, InputStream inputStream) {
		return mData.put(new FormRequestKey(key).setContentTypeSuffix(suffix), inputStream);
	}

	public InputStream put(FormRequestKey key, InputStream inputStream) {
		return mData.put(key, inputStream);
	}

	public InputStream get(FormRequestKey key) {
		return mData.get(key);

	}

	@Override
	public void prepare() throws IOException {
		for (Entry<FormRequestKey, InputStream> entry : mData.entrySet()) {
			FormRequestKey key = entry.getKey();
			InputStream value = entry.getValue();
			if (key != null && value != null) {
				mBody.put(key.toString().getBytes(charset), value);
			} else {
				LogUtils.e(TAG, "上传文件数据有null");
			}

		}
	}

	@Override
	public long length() throws IOException {
		long length = 0;
		for (Entry<byte[], InputStream> entry : mBody.entrySet()) {
			length += entry.getKey().length;
			length += entry.getValue().available();
		}
		int prefixLength = (PREFIX).getBytes(charset).length;
		int boundaryLength = (BOUNDARY).getBytes(charset).length;
		int size = mBody.size();
		return length + (prefixLength + boundaryLength) * (size + 1) + RequestEngine.CRLF.getBytes(charset).length * size * 2 + prefixLength;
	}

	@Override
	public void write(OutputStream outStream) throws UnsupportedEncodingException, IOException {
		byte[] boundaryByte = (PREFIX + BOUNDARY).getBytes(charset);
		byte[] crlfByte = RequestEngine.CRLF.getBytes(charset);
		byte[] buffer = new byte[1024 * 200];
		for (Entry<byte[], InputStream> entry : mBody.entrySet()) {
			outStream.write(boundaryByte);
			outStream.write(crlfByte);
			outStream.write(entry.getKey());
			int len = 0;
			InputStream value = entry.getValue();
			while ((len = value.read(buffer, 0, 1024)) != -1) {
				outStream.write(buffer, 0, len);
			}
			outStream.write(crlfByte);
		}
		outStream.write(boundaryByte);
		outStream.write((PREFIX).getBytes(charset));
	}

	public static class FormRequestKey {
		private final String name;
		private String fileName;
		private String contentLength;
		private String contentType;

		public FormRequestKey(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public String getFileName() {
			return fileName;
		}

		public FormRequestKey setFileName(String fileName) {
			this.fileName = fileName;
			return this;
		}

		public String getContentLength() {
			return contentLength;
		}

		public FormRequestKey setContentLength(String contentLength) {
			this.contentLength = contentLength;
			return this;
		}

		public String getContentType() {
			return contentType;
		}

		public FormRequestKey setContentType(String contentType) {
			this.contentType = contentType;
			return this;
		}

		public FormRequestKey setContentTypeSuffix(String suffix) {
			this.contentType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
			return this;
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			} else {
				if (o instanceof FormRequestBody) {
					if (name == null) {
						if (((FormRequestKey) o).name == null) {
							return true;
						} else {
							return false;
						}
					} else if (name.equals(((FormRequestKey) o).name)) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}

		@Override
		public String toString() {
			StringBuilder res = new StringBuilder(150);
			res.append("Content-Disposition:form-data;name=\"")// 拼name
					.append(name)
					.append("\"");
			if (!TextUtils.isEmpty(fileName)) {// 拼fileName
				res.append(";filename=\"")
						.append(fileName)
						.append("\"");
			}
			res.append(RequestEngine.CRLF);// 换行
			if (!TextUtils.isEmpty(contentType)) {// 如果有contentType不用拼contentLength
				return res.append("Content-Type:")
						.append(contentType)
						.append(RequestEngine.CRLF)
						.append(RequestEngine.CRLF).toString();
			}
			if (!TextUtils.isEmpty(contentLength)) {
				return res.append("Content-Length:")
						.append(contentLength)
						.append(RequestEngine.CRLF)
						.append(RequestEngine.CRLF).toString();
			}
			return res.append(RequestEngine.CRLF).toString();// 如果contentType，contentLength都没有直接使用
		}
	}
}
