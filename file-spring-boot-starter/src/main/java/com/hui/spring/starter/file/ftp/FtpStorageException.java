package com.hui.spring.starter.file.ftp;

/**
 * <code>FtpStorageService</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2020/10/20 21:59.
 *
 * @author Gary.Hu
 */
public class FtpStorageException extends RuntimeException{
    public FtpStorageException() {
        super();
    }

    public FtpStorageException(String message) {
        super(message);
    }

    public FtpStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public FtpStorageException(Throwable cause) {
        super(cause);
    }

    protected FtpStorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
