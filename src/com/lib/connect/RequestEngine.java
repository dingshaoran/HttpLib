package com.lib.connect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class RequestEngine {
	private final SocketPool pool;
	public static final String CRLF = "\r\n";

	public RequestEngine(SocketPool pool) {
		this.pool = pool;
	}

	/**
	 * 使用socket 发送数据并返回数据，此方法内发送请求 会阻塞线程
	 * 
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	void send(Request request, Response response) throws UnsupportedEncodingException, IOException {
		Socket socket = pool.get();
		request.write(socket.getOutputStream());
		response.read(socket.getInputStream());
		pool.cache(socket);
	}

	public static interface Request {

		public void write(OutputStream outStream) throws UnsupportedEncodingException, IOException;
	}

	public static interface Response {

		public void read(InputStream outStream) throws UnsupportedEncodingException, IOException;
	}

}
