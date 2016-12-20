package com.lib.connect;

import java.net.Socket;

public interface SocketPool {

	Socket get();

	void cache(Socket socket);
}
