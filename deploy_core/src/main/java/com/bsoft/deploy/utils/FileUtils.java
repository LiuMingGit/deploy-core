package com.bsoft.deploy.utils;

import com.bsoft.deploy.exception.FileOperationException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
     *
     * @param file
     * @return
     */
    public static String getFileMd5(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return DigestUtils.md5DigestAsHex(fis);
        } catch (Exception e) {
            // slient
            return null;
        } finally {
            closeStream(fis);
        }
    }

    /**
     * @param zipPath
     * @param targetDir
     * @throws IOException
     */
    public static void unZip(String zipPath, String targetDir) throws IOException {
        unZip(new File(zipPath), targetDir);
    }

    /**
     * 解压文件到指定目录
     * 解压后的文件名，和之前一致
     *
     * @param zipFile   待解压的zip文件
     * @param targetDir 指定目录
     */
    @SuppressWarnings("rawtypes")
    public static void unZip(File zipFile, String targetDir) throws IOException {
        targetDir = StringUtils.cleanPath(targetDir);
        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));//解决中文文件夹乱码
        File pathFile = new File(targetDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = entries.nextElement();
            String zipEntryName = entry.getName();

            String outPath = targetDir + File.separator + zipEntryName;
            InputStream in = null;
            FileOutputStream out = null;
            try {
                // System.out.println(outPath);
                // 判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                if (!file.exists()) {
                    file.mkdirs();
                }
                // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if (new File(outPath).isDirectory()) {
                    continue;
                }
                in = zip.getInputStream(entry);
                out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }

        }
        zip.close();
        // System.out.println("******************解压完毕********************");
    }

    /**
     * 静默关闭
     *
     * @param closeable 所有实现了Closeable接口的对象
     */
    public static void closeStream(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            // silent
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true"
     */
    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            //递归删除目录中的子目录下
            for (File file : dir.listFiles()) {
                deleteDir(file);
            }
        }
        // 目录此时为空，可以删除
        dir.delete();
    }

    public static void main(String[] args) throws IOException {
        File zip = new File("D:/dmp/dmp.zip");
        System.out.println(zip.getAbsolutePath().endsWith(".zip"));
    }
}
