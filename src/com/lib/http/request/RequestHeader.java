package com.lib.http.request;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public interface RequestHeader {

	public void write(OutputStream outStream) throws UnsupportedEncodingException, IOException;
}
