package com.lara.c2c.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
    @Value("${cloud.aws.credentials.accessKey}")
    String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    String accessSecret;
    @Value("${cloud.aws.region.static}")
    String region;
    
    @Bean
    public AmazonS3 generateS3Client() {
    	AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
    	AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
    	AmazonS3 client = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withRegion(region).build();
        return client;
    }
}