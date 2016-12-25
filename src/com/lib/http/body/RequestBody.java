package com.lib.http.body;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public interface RequestBody {

	public void prepare() throws IOException;

	public long length() throws IOException;

	public void write(OutputStream outStream) throws UnsupportedEncodingException, IOException;
}
