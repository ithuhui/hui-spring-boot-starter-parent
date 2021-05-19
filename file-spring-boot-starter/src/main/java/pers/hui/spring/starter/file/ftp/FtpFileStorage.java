package pers.hui.spring.starter.file.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.InputStream;

/**
 * <code>FtpStorage</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2020/9/17 17:13.
 *
 * @author Gary.Hu
 */
public interface FtpFileStorage {

    /**
     * 获取Client
     *
     * @return
     */
    FTPClient getClient();

    /**
     * 下载文件
     *
     * @param client
     * @param srcPath
     * @param fileName
     * @return
     */
    InputStream download(FTPClient client, String srcPath, String fileName);


    /**
     * 下载文件
     *
     * @param client
     * @param srcPath
     * @return
     */
    InputStream download(FTPClient client, String srcPath);

    /**
     * 查询目录下所有文件
     *
     * @param client
     * @param srcPath
     * @return
     */
    FTPFile[] listFiles(FTPClient client, String srcPath);

    /**
     * 关闭链接
     *
     * @param client
     * @return
     */
    void close(FTPClient client);
}
