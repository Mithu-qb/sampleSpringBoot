package com.example.demo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Date;

import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.model.Company;
import com.example.demo.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AmazonClient {

	private AmazonS3 s3client;

	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;

	@Value("${amazonProperties.bucketName}")
	private String bucketName;

	@Value("${amazonProperties.accessKey}")
	private String accessKey;

	@Value("${amazonProperties.secretKey}")
	private String secretKey;

	@PostConstruct
	/*
	 * AmazonS3 is a class from amazon dependency. add access key and secret key to
	 * applicatn.properties
	 * 
	 * InitializeAmazon() to set amazon credentials to amazon client.
	 * 
	 * Instantiate a class that provides the AWSCredentials interface, such as
	 * BasicAWSCredentials, and supply it with the AWS access key and secret key
	 * 
	 * Create an AWSStaticCredentialsProvider with the AWSCredentials object.
	 * 
	 * Configure the client builder with the AWSStaticCredentialsProvider and build
	 * the client.
	 * 
	 */
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.AP_SOUTH_1).build();

	}

	public String uploadFile(MultipartFile multipartFile) {
		
		
		return extractValue(multipartFile.getOriginalFilename(), (Object) multipartFile);
		 
	}

	public String WriteToFile(Object object) throws IOException {

		if (object instanceof Employee)
			return extractValue(((Employee) object).getId().toString(), object);

		if (object instanceof Company)
			return extractValue(((Company) object).getId().toString(), object);

		return "Data Mismatch";

	}

	public String deleteFileFromS3Bucket(String fileUrl) {
		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		System.out.println(fileName);
		s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
		return "Successfully deleted";
	}

	private String extractValue(String fileName, Object data) {
		File file = null;
		file = covertObjectToJson(fileName, data);
		String awsFileName = fileName + "_" + new Date().getTime() + ".txt";
		String fileUrl = endpointUrl + "/" + bucketName + "/" + awsFileName;
		uploadFileTos3bucket(awsFileName, file);
		file.delete();
		return fileUrl;
	}

	private File covertObjectToJson(String fileName, Object data) {
		ObjectMapper Obj = new ObjectMapper();
		File file = new File(fileName);
		FileOutputStream fos;
		String jsonStr = null;
		try {
			fos = new FileOutputStream(file);
			if( data instanceof Employee)
				jsonStr = Obj.writeValueAsString(data);
			else if( data instanceof Company)
				jsonStr = Obj.writeValueAsString(data);
			else {
				fos.write(((MultipartFile) data).getBytes());
				fos.close();
				return file;
			}
			
			fos.write(jsonStr.getBytes());
			fos.close();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}

	/*
	 * bucketName - The name of an existing bucket, to which you have
	 * Permission.Write permission.
	 * key - The key under which to store the specified file. 
	 * The file containing the data to be uploaded to Amazon
	 * S3.
	 */

	private void uploadFileTos3bucket(String key, File file) {
		s3client.putObject(new PutObjectRequest(bucketName, key, file));
	}

}
