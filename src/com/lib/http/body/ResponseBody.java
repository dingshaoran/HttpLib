package com.lib.http.body;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.lib.connect.Watcher;

public interface ResponseBody extends Watcher {

	public void read(InputStream outStream) throws UnsupportedEncodingException, IOException;
}
