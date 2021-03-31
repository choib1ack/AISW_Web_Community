package com.aisw.community.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AttachmentStorageProperties {
    private String uploadDir;
}
