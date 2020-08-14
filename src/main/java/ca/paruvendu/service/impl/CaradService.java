package ca.paruvendu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.paruvendu.dao.ICaradDAO;
import ca.paruvendu.domain.Carad;
import ca.paruvendu.domain.Search;
import ca.paruvendu.repository.CarAdRepository;
import ca.paruvendu.service.ICaradService;

@Service
public class CaradService implements ICaradService {

	@Autowired
	ICaradDAO caradDAO;
	
	@Autowired
	CarAdRepository carAdRepository; 
	
	@Override
	public Carad save(Carad carad) {
		// TODO Auto-generated method stub
		return caradDAO.save(carad);
	}

	@Override
	public List<Carad> findAll() {
		// TODO Auto-generated method stub
		return caradDAO.findAll();
	}

	@Override
	public Carad findById(String id) {
		// TODO Auto-generated method stub
		return caradDAO.findById(id);
	}

	public List<Carad> findByKeyword(Search search) {
		// TODO Auto-generated method stub
		return caradDAO.findByKeyword(search);
	}

	@Override
	public List<Carad> findAllAds(String username) {
		// TODO Auto-generated method stub
		return caradDAO.findAll(username);
	}

	@Override
	public void delete(String id) {
		carAdRepository.delete(id);
		
	}
	
	

}
