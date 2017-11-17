package org.timo.chunkedupload;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.LoggerFactory;

public class UploadClient {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		String file = "/Users/timoteo/Downloads/video/queen.mp4";
		HttpPost request = new HttpPost("http://localhost:8080/chunked-upload");		
		FileBody body = new FileBody(new File(file),ContentType.DEFAULT_BINARY);		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.STRICT);
		builder.addPart("uploadFile", body);		
		HttpEntity entity = builder.build();
		//
		request.setEntity(entity);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		LoggerFactory.getLogger(UploadClient.class).info(response.toString());
	}
}
