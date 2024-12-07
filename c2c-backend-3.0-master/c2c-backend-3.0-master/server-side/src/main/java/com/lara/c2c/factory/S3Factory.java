package com.lara.c2c.factory;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;

@Service
public class S3Factory {

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${app.awsServices.bucketName}")
    String bucketName;

    @Value("${app.awsServices.endpointUrl}")
    String endpointUrl;

    @Value("${cloud.aws.credentials.accessKey}")
    String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    String secretKey;

    @PostConstruct
    public void init() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        amazonS3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl, Regions.DEFAULT_REGION.getName()))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withPathStyleAccessEnabled(true) // Ensure path style access is enabled for compatibility
                .build();
    }

    public ResponseEntity<ByteArrayResource> getFile(String fileName) throws Exception {
        String finalFileName = "lara/materials-for-all-courses/zipped-files/" + fileName;
        String fileUrl = String.format("%s/%s/%s", endpointUrl, bucketName, finalFileName);
        
        System.out.println("File URL: " + fileUrl);

        S3Object obj = amazonS3Client.getObject(bucketName, finalFileName);
        System.out.println("get bucket" + finalFileName);
        S3ObjectInputStream stream = obj.getObjectContent();
        System.out.println("getStream");
        byte[] content = IOUtils.toByteArray(stream);
        System.out.println("getContent");
        obj.close();
        ByteArrayResource resource = new ByteArrayResource(content);
        System.out.println("byte array resource:" + resource);
        return ResponseEntity
                .ok()
                .contentLength(content.length)
                .header("content-type", "application/octet-stream")
                .header("content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
}
