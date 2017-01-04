package com.lib.connect;

public interface Watcher {
	/**
	 * 就是打log用的，不想叫log，和logutil重了不好分出来
	 */
	void watcher(String builder);
}
