package com.hui.spring.starter.file;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * <code>BaseDistributedFileStorage</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2020/9/10 18:05.
 *
 * @author Gary.Hu
 */
public abstract class BaseDistributedFileStorage implements DistributedFileStorage {

    protected static final MimetypesFileTypeMap FILE_TYPE_MAP = new MimetypesFileTypeMap();

    public abstract String getDefaultBucket();

    @Override
    public void upload(String key, InputStream ins) {
        this.upload(getDefaultBucket(), key, ins);
    }


    @Override
    public void upload(String key, File file) {
        this.upload(getDefaultBucket(), key, file);
    }

    @Override
    public FileResource download(String key) {
        return this.download(getDefaultBucket(), key);
    }

    @Override
    public boolean exist(String key) {
        return this.exist(getDefaultBucket(), key);
    }

    @Override
    public void delete(String key) {
        this.delete(getDefaultBucket(), key);
    }

    @Override
    public URL genUrl(String key) {
        return this.genUrl(getDefaultBucket(), key);
    }

    protected String getContentType(String filename) {
        return FILE_TYPE_MAP.getContentType(filename);
    }

}
