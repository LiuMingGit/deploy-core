package com.bsoft.deploy.utils;

import com.bsoft.deploy.exception.FileOperationException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

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

    /**
     * 文件拷贝
     *
     * @param oldFile 源文件
     * @param newFile 目标文件
     * @throws Exception 异常
     */
    public static void copyFile(File oldFile, File newFile) throws Exception {
        FileInputStream input = new FileInputStream(oldFile);
        FileOutputStream output = new FileOutputStream(newFile);
        byte[] buf1 = new byte[1024];
        int len;
        while ((len = input.read(buf1)) > 0) {
            output.write(buf1, 0, len);
        }
        input.close();
        output.close();
    }

    /**
     * 获取文件的目录路径
     *
     * @param absolutePath 文件绝对路径
     * @return 目录路径
     */
    public static String getFilePath(String absolutePath) {
        if (absolutePath == null) {
            return "";
        }
        absolutePath = StringUtils.cleanPath(absolutePath);
        return absolutePath.substring(0, absolutePath.lastIndexOf("/"));
    }

    /**
     * 根据tomcat_home获取tomcat的port
     *
     * @param tomcat_home tomcat安装路径
     * @return port
     */
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
     * @param file 文件
     * @return md5
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
     * 解压缩
     *
     * @param zipPath   源文件路径
     * @param targetDir 目标路径
     * @throws IOException 异常
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
        //解决中文文件夹乱码
        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
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

    /**
     * 格式化文件路径,包含结尾分隔符
     *
     * @param path
     * @return
     */
    public static String pathFormat(String path) {
        if (path == null) {
            return "";
        }

        path = path.replaceAll("\\\\", "/").replaceAll("//", "/");
        return path.endsWith("/") ? path : path + "/";
    }

    /**
     * 复制文件或文件夹
     *
     * @param srcPath
     * @param destDir 目标文件所在的目录
     * @return
     */
    public static boolean copyGeneralFile(String srcPath, String destDir) {
        File file = new File(srcPath);
        if (!file.exists()) {
            logger.warn("复制文件失败:源文件或源文件夹不存在!");
            return false;
        }
        if (file.isFile()) { // 源文件
            return copyFile(srcPath, destDir);
        } else {
            return copyDirectory(srcPath, destDir);
        }
    }

    /**
     * 复制文件
     *
     * @param srcPath 源文件绝对路径
     * @param destDir 目标文件所在目录
     * @return boolean
     */
    private static boolean copyFile(String srcPath, String destDir) {
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) { // 源文件不存在
            logger.warn("复制文件失败:源文件或源文件夹不存在!");
            return false;
        }
        // 获取待复制文件的文件名
        String fileName = srcPath
                .substring(srcPath.lastIndexOf(File.separator));
        String destPath = destDir + fileName;
        if (destPath.equals(srcPath)) { // 源文件路径和目标文件路径重复
            logger.warn("复制文件失败:源文件或源文件夹不存在!");
            return false;
        }
        File destFile = new File(destPath);
        File destFileDir = new File(destDir);
        destFileDir.mkdirs();
        try {
            FileInputStream fis = new FileInputStream(srcFile.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] buf = new byte[4096];
            int c;
            while ((c = fis.read(buf)) != -1) {
                fos.write(buf, 0, c);
            }
            fis.close();
            fos.close();
            return true;
        } catch (IOException e) {
            logger.error("文件复制失败!", e);
            return false;
        }

    }

    /**
     * @param srcPath 源文件夹路径
     * @param destDir 目标文件夹所在目录
     * @return
     */
    private static boolean copyDirectory(String srcPath, String destDir) {
        boolean flag = false;

        File srcFile = new File(srcPath);
        if (!srcFile.exists()) { // 源文件夹不存在
            logger.warn("复制文件失败:源文件或源文件夹不存在!");
            return false;
        }
        // 获得待复制的文件夹的名字，比如待复制的文件夹为"E://dir"则获取的名字为"dir"
        String dirName = getDirName(srcPath);
        // 目标文件夹的完整路径
        String destPath = destDir + File.separator + dirName;
        // System.out.println("目标文件夹的完整路径为：" + destPath);

        if (destPath.equals(srcPath)) {
            logger.warn("复制文件失败:目标文件夹与源文件夹重复!");
            return false;
        }
        File destDirFile = new File(destPath);
        destDirFile.mkdirs(); // 生成目录

        File[] fileList = srcFile.listFiles(); // 获取源文件夹下的子文件和子文件夹
        if (fileList.length == 0) { // 如果源文件夹为空目录则直接设置flag为true，这一步非常隐蔽，debug了很久
            return true;
        } else {
            for (File temp : fileList) {
                if (temp.isFile()) { // 文件
                    flag = copyFile(temp.getAbsolutePath(), destPath);
                } else if (temp.isDirectory()) { // 文件夹
                    flag = copyDirectory(temp.getAbsolutePath(), destPath);
                }
                if (!flag) {
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 获取待复制文件夹的文件夹名
     *
     * @param dir
     * @return String
     */
    private static String getDirName(String dir) {
        if (dir.endsWith(File.separator)) { // 如果文件夹路径以"//"结尾，则先去除末尾的"//"
            dir = dir.substring(0, dir.lastIndexOf(File.separator));
        }
        return dir.substring(dir.lastIndexOf(File.separator) + 1);
    }

    /**
     * 删除文件或文件夹
     *
     * @param path 待删除的文件的绝对路径
     * @return boolean
     */
    public static boolean deleteGeneralFile(String path) {
        boolean flag = false;

        File file = new File(path);
        if (!file.exists()) { // 文件不存在
            return false;
        }

        if (file.isDirectory()) { // 如果是目录，则单独处理
            flag = deleteDirectory(file.getAbsolutePath());
        } else if (file.isFile()) {
            flag = deleteFile(file);
        }
        return flag;
    }

    /**
     * 删除文件
     *
     * @param file
     * @return boolean
     */
    private static boolean deleteFile(File file) {
        return file.delete();
    }

    /**
     * 删除目录及其下面的所有子文件和子文件夹，注意一个目录下如果还有其他文件或文件夹
     * 则直接调用delete方法是不行的，必须待其子文件和子文件夹完全删除了才能够调用delete
     *
     * @param path path为该目录的路径
     */
    private static boolean deleteDirectory(String path) {
        boolean flag = true;
        File dirFile = new File(path);
        if (!dirFile.isDirectory()) {
            return flag;
        }
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (File file : files) { // 删除该文件夹下的文件和文件夹
                // Delete file.
                if (file.isFile()) {
                    flag = deleteFile(file);
                } else if (file.isDirectory()) {// Delete folder
                    flag = deleteDirectory(file.getAbsolutePath());
                }
                if (!flag) { // 只要有一个失败就立刻不再继续
                    break;
                }
            }
        }
        flag = dirFile.delete(); // 删除空目录
        return flag;
    }

    /**
     * 由上面方法延伸出剪切方法：复制+删除
     *
     * @param srcPath 源文件
     * @param destDir 目标路径
     *
     */
    public static boolean cutGeneralFile(String srcPath, String destDir) {
        return copyGeneralFile(srcPath, destDir) && deleteGeneralFile(srcPath);
    }


    public static void main(String[] args) throws IOException {
        long begin = System.currentTimeMillis();
        File src = new File("D:/develop/apache-tomcat7_8889/webapps/deploy");
        copyDirectory(src.getAbsolutePath(), "D:\\workspace_ideal\\deploy\\master_backup");
        long end = System.currentTimeMillis();
        System.out.println("文件拷贝共耗时:" + (end - begin)+"ms");
    }
}
