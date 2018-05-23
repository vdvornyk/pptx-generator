package org.rallyplugins.commons.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ResourceUtils {
    public static String getTplFromFile(String tplPath) throws IOException {
        return IOUtils.toString(new ClassPathResource(tplPath).getInputStream(), "UTF-8");
    }

    public static InputStream getInputStreamFromResource(String resourcePath) throws IOException {
        return new ClassPathResource(resourcePath).getInputStream();
    }

    public static URL getUrlFromResource(String resourcePath) throws IOException {
        return new ClassPathResource(resourcePath).getURL();
    }

    public static List<String> getStringsFromFile(String tplPath) throws IOException {
        return FileUtils.readLines(new ClassPathResource(tplPath).getFile(), "UTF-8");
    }
}
