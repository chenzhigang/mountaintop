package com.czg.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class ContentCachingRequestWrapper extends HttpServletRequestWrapper {
	private final byte[] body;

	public ContentCachingRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		// 读一次 然后缓存起来
		body = IOUtils.toByteArray(request.getInputStream());
	}

	public byte[] getBody() {
		return body;
	}

	@Override
	public ServletInputStream getInputStream() {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				// Do nothing
			}

			@Override
			public int read() {
				return byteArrayInputStream.read();
			}
		};
	}

	@Override
	public BufferedReader getReader() {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}
}
