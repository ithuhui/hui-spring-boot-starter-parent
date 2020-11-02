package com.hui.spring.starter.file.ceph;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import com.hui.spring.starter.file.BaseDistributedFileStorage;
import com.hui.spring.starter.file.FileResource;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * <code>CephStorage</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2020/9/10 10:55.
 *
 * @author Gary.Hu
 */
@Slf4j
public class CephStorage extends BaseDistributedFileStorage {

    private static final long BIG_FILE_SIZE = 500000000;
    private final AmazonS3 client;
    private final CephProperties cephProperties;
    private final TransferManager transferManager;

    public CephStorage(AmazonS3 client, CephProperties cephProperties) {
        this.client = client;
        this.cephProperties = cephProperties;
        this.transferManager = TransferManagerBuilder.standard().withS3Client(client).build();
    }

    @Override
    public String getDefaultBucket() {
        return cephProperties.getDefaultBucket();
    }

    @Override
    public void upload(String bucket, String key, InputStream ins) {
        try {
            byte[] data = IOUtils.toByteArray(ins);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(data.length);
            objectMetadata.setContentType(this.getContentType(key));
            PutObjectRequest request = new PutObjectRequest(bucket, key, new ByteArrayInputStream(data), objectMetadata);
            client.putObject(request);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CephStorageException("Fail to upload to ceph by inputStream", e);
        }
    }


    @Override
    public void upload(String bucket, String key, File file) {
        if (file.length() < BIG_FILE_SIZE) {
            client.putObject(bucket, key, file);
        } else {
            // use multipart upload for big files
            try {
                Upload upload = transferManager.upload(bucket, key, file);
                upload.waitForCompletion();
            } catch (InterruptedException e) {
                throw new CephStorageException("Fail to upload to ceph by file", e);
            }
        }
    }


    @Override
    public FileResource download(String bucket, String key) {
        S3Object object = client.getObject(bucket, key);

        return FileResource.builder()
                .content(object.getObjectContent())
                .contentType(object.getObjectMetadata().getContentType())
                .contentLength(object.getObjectMetadata().getContentLength())
                .fileName(object.getKey())
                .build();
    }

    @Override
    public boolean exist(String bucket, String key) {
        return client.doesObjectExist(bucket, key);
    }

    @Override
    public void delete(String bucket, String key) {
        client.deleteObject(bucket, key);
    }

    @Override
    public URL genUrl(String bucket, String key) {
        return client.getUrl(bucket, key);
    }

    @Override
    public URL genUrl(String bucket, String key, CannedAccessControlList controlList) {
        client.setObjectAcl(bucket, key, controlList);
        return this.genUrl(bucket, key);
    }

    @Override
    public URL genPresignedUrl(String bucket, String key) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, key);
        return client.generatePresignedUrl(request);
    }

    @Override
    public URL genPresignedUrl(String bucket, String key, CannedAccessControlList controlList) {
        client.setObjectAcl(bucket, key, controlList);
        return this.genPresignedUrl(bucket, key);
    }

}
