package com.apap.tugasapap.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugasapap.model.ProvinsiModel;
import com.apap.tugasapap.repository.ProvinsiDB;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService{
	
	@Autowired
	private ProvinsiDB provinsiDb;
	
	@Override
	public List<ProvinsiModel> getAllProvinsi() {
		// TODO Auto-generated method stub
		return provinsiDb.findAll();
	}

	@Override
	public ProvinsiModel getProvinsiDetailById(long id) {
		// TODO Auto-generated method stub
		return provinsiDb.findById(id);
	}

}
