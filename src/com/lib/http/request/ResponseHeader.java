package com.lib.http.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public interface ResponseHeader {

	public void read(InputStream outStream) throws UnsupportedEncodingException, IOException;
}
