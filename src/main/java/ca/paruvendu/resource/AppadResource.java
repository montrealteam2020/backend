package ca.paruvendu.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.paruvendu.domain.Appad;
import ca.paruvendu.domain.Carad;
import ca.paruvendu.domain.Search;
import ca.paruvendu.service.impl.AppadService;


@RestController
@RequestMapping("/appad")
public class AppadResource {

	private static final Logger logger = LoggerFactory.getLogger(AppadResource.class);

	@Autowired
	private AppadService appadService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Appad addAppad(@RequestBody Appad appad) {

		Date addate = new Date();
		appad.setAddate(addate);
		
		return appadService.save(appad);

	}
	
	@RequestMapping(value="listAll", method=RequestMethod.GET)
	public List<Appad> getAllAppad(){
			return appadService.findAll();
		}
	
	
	  @RequestMapping("/{id}")
	  public Appad getCaradById(@PathVariable("id")  String id){
		  ObjectMapper mapper = new ObjectMapper();
		  int numFiles=0;
		  File file;
		  String[] files;
		  //Convert object to JSON string
		  logger.info("Find by id--> "+ id);
		  Appad appad= appadService.findById(id);
		  String directory = "src/main/resources/static/image/appad/"+id;
		  
		  try {
			   file = new File(directory);
			    files = file.list();
			    numFiles = files.length;
			   logger.info("numFiles---> "+ numFiles);
			  } catch (NullPointerException e) {
			    System.out.println("File null !");
			  }
			   
					  
		  appad.setFileNumber(numFiles);
			String jsonInString;
			try {
				jsonInString = mapper.writeValueAsString(appad);
				System.out.println(jsonInString);
				 logger.info("jsonInString---> "+jsonInString);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		  logger.info("id---> "+id);
		  return appad;
	  }
	  
	
	

	@RequestMapping(value = "/add/image", method = RequestMethod.POST)
	public ResponseEntity upload(@RequestParam("id") String id, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			Appad appad = appadService.findById(id);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> it = multipartRequest.getFileNames();
			
			List<MultipartFile> multi = multipartRequest.  getFiles("uploads[]");
	
			int i=1;
						
			File theDir = new File("src/main/resources/static/image/appad/"+id);
        	saveFilesToServer(multi,id);
			return new ResponseEntity("Upload Success!", HttpStatus.OK);
		
		
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Upload failed!", HttpStatus.BAD_REQUEST);
		}
	}
	
	  public void saveFilesToServer(List<MultipartFile> multipartFiles, String id) throws IOException {
		  	String directory = "file:///var/www/html/image/appad/"+id;
			Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr-xr-x");
			int i=1;
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
				
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		  }
	  
	  
	  
	  @RequestMapping(value="/searchAppad/key", method=RequestMethod.POST)
	  List<Appad>  getCaradByKeyword(@RequestBody Search search){
		  logger.info("search element 1---> "+search.getElement1());
		  return appadService.findByKeyword(search);
	  }
	  

}
