package ca.paruvendu.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import ca.paruvendu.domain.GiveFree;
import ca.paruvendu.service.IGiveFreeService;

@RestController
@RequestMapping("givefree")
public class GiveFreeRController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(GiveFreeRController.class);
	
	@Autowired
	IGiveFreeService giveFreeservcie;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public GiveFree addFreeAd(@RequestBody GiveFree giveFree){
		logger.info("Add for free all");
		return giveFreeservcie.save(giveFree);
		 
	}
	
	
	@RequestMapping(value = "/add/image", method = RequestMethod.POST)
	public ResponseEntity upload(@RequestParam("id") String id, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			GiveFree giveFree = giveFreeservcie.findById(id);
			   MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			    Iterator<String> it = multipartRequest.getFileNames();
			     List<MultipartFile> multi = multipartRequest.  getFiles("uploads[]");
			      logger.info("create file");			
			     File theDir = new File("src/main/resources/static/image/free/"+id);
			     Path path = Paths.get(theDir.toString());
			     saveFilesToServer(multi,id);
			return new ResponseEntity("Upload Success!", HttpStatus.OK);
			} 
		     catch (Exception e) {
			    e.printStackTrace();
			   return new ResponseEntity("Upload failed!", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	public void saveFilesToServer(List<MultipartFile> multipartFiles, String id) throws IOException {
		String directory = "file:///var/www/html/image/free/" + id;
	 	Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr-xr-x");
		int i = 1;
		File file;
		try {
			file = new File(new URI(directory));
			if (file.mkdirs())
				 Files.setPosixFilePermissions(file.toPath(), permissions);
			
			Path path = Paths.get(file.toString());
			String fullpath=path.toUri().toString();
			logger.info("creation of folder "+ fullpath);	
			
			for (MultipartFile multipartFile : multipartFiles) {
				file = new File(new URI(directory+"/" + id+i+".png"));
				IOUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
				 Files.setPosixFilePermissions(file.toPath(), permissions);
			  i++;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="/freeList", method=RequestMethod.GET)
	public List<GiveFree> getFreeList(){
		return giveFreeservcie.findAll();
	}

}
