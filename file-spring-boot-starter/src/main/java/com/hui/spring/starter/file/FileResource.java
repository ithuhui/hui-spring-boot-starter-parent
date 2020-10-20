package com.hui.spring.starter.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * <code>FileResource</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2020/9/10 14:09.
 *
 * @author Gary.Hu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResource {
    private String contentType;
    private String fileName;
    private InputStream content;
    private Long contentLength;
}
