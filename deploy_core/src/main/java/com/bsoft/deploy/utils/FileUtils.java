package com.bsoft.deploy.utils;

import com.bsoft.deploy.exception.FileOperationException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件工具类
 * Created on 2018/8/10.
 *
 * @author yangl
 */
public class FileUtils {

    /**
     * 判断指定path的文件是否存在
     *
     * @param path 文件路径
     * @return
     */
    public static boolean exists(String path) {
        return new File(path).exists();
    }

    /**
     * 获取文件二进制数据
     *
     * @param filePath
     * @return
     */
    public static byte[] getBytes(String filePath) {
        try {
            return getBytes(new FileInputStream(filePath));
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] getBytes(File file) {
        try {
            return getBytes(new FileInputStream(file));
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] getBytes(FileInputStream in) throws IOException {
        byte[] data = new byte[in.available()];
        in.read(data);
        in.close();
        return data;
    }

    /**
     * 以应用默认路径开始计算
     *
     * @param basePath     项目设定的应用所在目录
     * @param absolutePath 文件的全路径
     * @return
     */
    public static String getRelativePath(String basePath, String absolutePath) {
        String clean_basePath = StringUtils.cleanPath(basePath);
        String clean_absolutePath = StringUtils.cleanPath(absolutePath);
        return clean_absolutePath.replace(clean_basePath, "");
    }

    public static void copyFile(File oldFile, File newFile) throws Exception {
        FileInputStream input = new FileInputStream(oldFile);
        FileOutputStream output = new FileOutputStream(newFile);
        int in = input.read();
        while (in != -1) {
            output.write(in);
            in = input.read();
        }
        input.close();
        output.close();
    }

    public static String getFilePath(String absolutePath) {
        if (absolutePath == null) {
            return "";
        }
        absolutePath = StringUtils.cleanPath(absolutePath);
        return absolutePath.substring(0, absolutePath.lastIndexOf("/"));
    }

    public static String getTomcatServerPort(String tomcat_home) {
        String serverXml = StringUtils.cleanPath(tomcat_home + "/conf/server.xml");

        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(serverXml));
            Element root = document.getRootElement();
            return root.element("Service").element("Connector").attributeValue("port");
        } catch (DocumentException e) {
            throw new FileOperationException("获取tomcat端口失败!" + e.getMessage(), e);
        }
    }


    /**
     * file md5 文件签名
     * @param file
     * @return
     */
    public static String getFileMd5(File file) {
        FileInputStream fis = null;
        try {
            return DigestUtils.md5DigestAsHex(new FileInputStream(file));
        } catch (Exception e) {
            // slient
            return null;
        } finally {
            try { fis.close(); } catch (Exception e) {}
        }
    }

    public static void main(String[] args) {
        String path = "d:\\a/b_c/d e/f";
        System.out.println(StringUtils.cleanPath(path));
        String cleanPath = StringUtils.cleanPath(path);
    }
}
