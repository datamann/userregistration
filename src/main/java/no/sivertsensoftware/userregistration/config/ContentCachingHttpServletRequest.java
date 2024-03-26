package no.sivertsensoftware.userregistration.config;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.SneakyThrows;
import org.springframework.util.StreamUtils;

import java.io.*;

public class ContentCachingHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] cachedBody;

    public ContentCachingHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);

        InputStream requestInputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);

    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        return new ServletInputStream() {

            private InputStream cachedBodyInputStream = new ByteArrayInputStream(cachedBody);

            @Override
            @SneakyThrows
            public boolean isFinished() {
                return cachedBodyInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int read() throws IOException {
                return cachedBodyInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

}