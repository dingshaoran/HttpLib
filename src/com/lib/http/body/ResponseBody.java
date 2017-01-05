package com.lib.http.body;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public interface ResponseBody {

	public void read(InputStream inStream) throws UnsupportedEncodingException, IOException;
}
