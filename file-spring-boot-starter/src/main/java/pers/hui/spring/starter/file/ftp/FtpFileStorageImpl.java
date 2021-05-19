package pers.hui.spring.starter.file.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

/**
 * <code>FtpFileStorageImpl</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2020/10/20 21:33.
 *
 * @author Gary.Hu
 */
public class FtpFileStorageImpl implements FtpFileStorage {

    private FtpProperties properties;

    public FtpFileStorageImpl(FtpProperties properties) {
        this.properties = properties;
    }

    @Override
    public FTPClient getClient() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(properties.getHost(), properties.getDefaultPort());
            ftpClient.login(properties.getUsername(), properties.getPassword());
            int replyCode = ftpClient.getReplyCode();
            if (FTPReply.isPositivePreliminary(replyCode)) {
                close(ftpClient);
                throw new FtpStorageException("ftp is not PositivePreliminary");
            }
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(FtpCons.OPTS_UTF8, FtpCons.ON_COMMAND))) {
                ftpClient.setControlEncoding(FtpCons.SERVER_CHARSET);
            }
            ftpClient.setControlEncoding(FtpCons.CHARSET_UTF8);

            ftpClient.enterLocalPassiveMode();
            return ftpClient;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public InputStream download(FTPClient client, String srcPath, String fileName) {
        return null;
    }

    @Override
    public InputStream download(FTPClient client, String srcPath) {
        return null;
    }

    @Override
    public FTPFile[] listFiles(FTPClient client, String srcPath) {
        return new FTPFile[0];
    }

    @Override
    public void close(FTPClient client) {

    }
}
