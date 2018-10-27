package com.apap.tugasapap.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugasapap.model.InstansiModel;
import com.apap.tugasapap.repository.InstansiDB;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {

	@Autowired
	private InstansiDB instansiDb;
	
	@Override
	public List<InstansiModel> getAllInstansi() {
		// TODO Auto-generated method stub
		return instansiDb.findAll();
	}

	@Override
	public InstansiModel getInstansiDetailById(long id) {
		// TODO Auto-generated method stub
		return instansiDb.findById(id);
	}

}
