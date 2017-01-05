package com.lib.http.line;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.lib.utils.StringUtils;

public class SimpleResponseLine implements ResponseLine {

	private String scheme;
	private String responseCode;
	private String responseMessage;

	@Override
	public void read(InputStream in) throws UnsupportedEncodingException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String responseLine = reader.readLine();
		ArrayList<String> split = StringUtils.split(responseLine, " ");
		this.scheme = split.get(0);
		this.responseCode = split.get(1);
		if (split.size() > 2) {
			this.responseMessage = split.get(2);
		}
	}

	public String getScheme() {
		return scheme;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}
}
