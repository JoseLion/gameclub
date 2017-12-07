package ec.com.levelap.gameclub.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="open/util", produces=MediaType.APPLICATION_JSON_VALUE)
public class UtilController {
	
	@RequestMapping(value="getImage", method=RequestMethod.GET)
	public void getImage(@RequestParam String name, HttpServletResponse response) throws ServletException, IOException {
		File image = File.createTempFile(name + "-temp-file-", "." + FilenameUtils.getExtension(name));
		FileCopyUtils.copy(FileCopyUtils.copyToByteArray(getClass().getResourceAsStream("/img/" + name)), image);
		String contentType = Files.probeContentType(image.toPath());
		
		response.setContentType(contentType != null ? contentType : "application/octet-stream");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + name +"\""));
		response.setContentLengthLong(image.length());
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(image));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
}
