package com.lib.connect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class Engine {
	public static final Object CRLF = "\r\n";

	/**
	 * 使用 socket 发送数据并返回数据，此方法内发送请求 会阻塞线程
	 * 
	 * @return
	 */
	void send(Request request, Response response) {

	};

	public static interface Request {

		public void write(OutputStream outStream) throws UnsupportedEncodingException, IOException;
	}

	public static interface Response {

		public void read(InputStream outStream) throws UnsupportedEncodingException, IOException;
	}

}
