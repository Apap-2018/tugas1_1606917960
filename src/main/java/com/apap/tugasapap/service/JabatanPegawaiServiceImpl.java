package com.apap.tugasapap.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugasapap.model.JabatanPegawaiModel;
import com.apap.tugasapap.repository.PegawaiJabatanDB;

@Service
@Transactional
public class JabatanPegawaiServiceImpl implements JabatanPegawaiService {
	
	@Autowired
	private PegawaiJabatanDB jabatanPegawaiDb;
	
	@Override
	public void addJabatanPegawai(JabatanPegawaiModel jabatanPegawai) {
		// TODO Auto-generated method stub
		jabatanPegawaiDb.save(jabatanPegawai);
		
	}

	@Override
	public List<JabatanPegawaiModel> getAllJabatanPegawai() {
		// TODO Auto-generated method stub
		return jabatanPegawaiDb.findAll();
	}

}
