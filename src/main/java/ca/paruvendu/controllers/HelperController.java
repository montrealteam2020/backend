package ca.paruvendu.controllers;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ca.paruvendu.domain.AllAds;
import ca.paruvendu.domain.Carad;
import ca.paruvendu.domain.User;
import ca.paruvendu.service.ICaradService;
import ca.paruvendu.service.UserService;

@RestController
public class HelperController {
	
	@Autowired
	private ICaradService carAdService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/allAds", method=RequestMethod.GET)
	public List<AllAds> sendAllAds( Principal principal){
		User user = userService.findByUserName(principal.getName());
		
		List<AllAds> listAllAds = new ArrayList<AllAds>();
		List<Carad> carAdList=carAdService.findAllAds(user.getUsername());
		for(Carad car : carAdList )
		{
		  AllAds allAds = new AllAds();
		  allAds.setId(car.getId());
		  SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); 
          String strDate = formatter.format(car.getAddate());  
		  allAds.setAdDate(strDate);
		  allAds.setDescription(car.getAdtitle());
		  allAds.setAddType("CAR");
		  listAllAds.add(allAds);
		}
		
		return listAllAds;	
		
	}
	
	
	@RequestMapping(value="/removeAds", method=RequestMethod.POST)
	ResponseEntity removeAds(@RequestBody AllAds adToRemove){
		
		if("CAR".equals(adToRemove.getAddType()))
			carAdService.delete(adToRemove.getId());
		
		return new ResponseEntity("Message sent", HttpStatus.OK);
	}

}
