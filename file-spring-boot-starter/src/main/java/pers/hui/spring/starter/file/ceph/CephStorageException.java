package pers.hui.spring.starter.file.ceph;

/**
 * <code>CephStorageException</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2020/10/20 21:46.
 *
 * @author Gary.Hu
 */
public class CephStorageException extends RuntimeException{
    public CephStorageException() {
        super();
    }

    public CephStorageException(String message) {
        super(message);
    }

    public CephStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public CephStorageException(Throwable cause) {
        super(cause);
    }

    protected CephStorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
