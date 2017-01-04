package com.lib.http.request;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lib.connect.SocketPool;

public class SocketPoolImmediatea implements SocketPool {
	public final static String CRLF = "\r\n";
	private final int mCacheCount;
	private final List<Socket> mIdleList = Collections.synchronizedList(new ArrayList<Socket>());
	private final int mConnectTimeout;
	private final int mReadTimeout;
	private static final int DEFAULT_CACHECOUNT = 3;
	private static final int DEFAULT_CONNECT_TIMEOUT = 15000;
	private static final int DEFAULT_READ_TIMEOUT = 30000;

	public SocketPoolImmediatea(int cacheCount, int connectTimeout, int readTimeout) {
		this.mCacheCount = cacheCount;
		this.mConnectTimeout = connectTimeout;
		this.mReadTimeout = readTimeout;
	}

	public SocketPoolImmediatea() {
		mCacheCount = DEFAULT_CACHECOUNT;
		mConnectTimeout = DEFAULT_CONNECT_TIMEOUT;
		mReadTimeout = DEFAULT_READ_TIMEOUT;
	}

	@Override
	public Socket get() {
		return null;
	}

	@Override
	public void cache(Socket socket) {
	}

}
