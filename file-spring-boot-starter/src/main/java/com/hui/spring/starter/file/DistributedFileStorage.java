package com.hui.spring.starter.file;

import com.amazonaws.services.s3.model.CannedAccessControlList;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * <code>FileService</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2020/9/10 10:51.
 *
 * @author Gary.Hu
 */
public interface DistributedFileStorage {

    void upload(String bucket, String key, InputStream ins);

    void upload(String key, InputStream ins);

    void upload(String key, File file);

    void upload(String bucket, String key, File file);

    FileResource download(String bucket, String key);

    FileResource download(String key);

    boolean exist(String key);

    boolean exist(String bucket, String key);

    void delete(String key);

    void delete(String bucket, String key);

    URL genUrl(String bucket, String key);

    URL genUrl(String key);

    URL genUrl(String bucket, String key, CannedAccessControlList controlList);

    URL genUrl(String key, CannedAccessControlList controlList);

    URL genPresignedUrl(String bucket, String key);

    URL genPresignedUrl(String key);

    URL genPresignedUrl(String bucket, String key, CannedAccessControlList controlList);

    URL genPresignedUrl(String key, CannedAccessControlList controlList);

}
