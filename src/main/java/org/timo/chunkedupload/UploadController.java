package org.timo.chunkedupload;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author timoteo
 * 
 * https://github.com/blueimp/jQuery-File-Upload/wiki/Chunked-file-uploads
 * 
 *
 */
@RestController
public class UploadController {

	@RequestMapping("/hello")
	public String sayHi() {
		return "Hi at " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
	}

	@ResponseBody
	@RequestMapping(value = "/chunked-upload", method = RequestMethod.POST)
	public Map<String, String> chunkedUpload(final @RequestHeader HttpHeaders headers,
			final MultipartHttpServletRequest request) throws IOException {
		info("Headers are: " + headers.toString());
		info("File is: " + headers.getFirst("Content-Disposition"));

		// MultipartHttpServletRequest multipartRequest =
		// (MultipartHttpServletRequest) request;
		request.getFileNames().forEachRemaining(t -> {
			MultipartFile file = request.getFile(t);
			info("multipart: " + t);
			info("multipart-file: " + file.getSize());
			try {
				info("multipart-data: " + IOUtils.toByteArray(file.getInputStream()).length);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return Collections.singletonMap("Test", "Value");
	}

	private void info(String string) {
		LoggerFactory.getLogger(getClass()).info(string);
	}

}
