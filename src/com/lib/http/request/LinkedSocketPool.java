package com.lib.http.request;

import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.lib.connect.SocketPool;

public class LinkedSocketPool implements SocketPool {
	private final List<Socket> mIdleList = Collections.synchronizedList(new LinkedList<Socket>());
	private final int maxSize;

	public LinkedSocketPool(int maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	public Socket get() {
		if (mIdleList.size() > 0) {
			return mIdleList.remove(0);
		} else {
			return new Socket();
		}
	}

	@Override
	public void cache(Socket socket) {
		if (maxSize > mIdleList.size()) {
			mIdleList.add(socket);
		}
	}

}
