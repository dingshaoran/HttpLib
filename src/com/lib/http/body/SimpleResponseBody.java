package com.lib.http.body;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class SimpleResponseBody implements ResponseBody {
	private final OutputStream outputStream;

	public SimpleResponseBody(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public SimpleResponseBody(String filePath) throws Exception {
		File file = new File(filePath);
		file.getParentFile().mkdirs();
		file.delete();
		this.outputStream = new FileOutputStream(file);
	}

	@Override
	public void read(InputStream inStream) throws UnsupportedEncodingException, IOException {
		byte[] buffer = new byte[1024 * 200];
		int len = 0;
		while ((len = inStream.read(buffer, 0, 1024)) != -1) {
			outputStream.write(buffer, 0, len);
		}
	}
}
