package com.apap.tugasapap.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugasapap.model.JabatanModel;
import com.apap.tugasapap.repository.JabatanDB;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDB jabatanDb;
	
	@Override
	public void addJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.save(jabatan);
		
	}
	
	@Override
	public void updateJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		JabatanModel old_jabatan = this.getJabatanDetailById(jabatan.getId());
		old_jabatan.setNama(jabatan.getNama());
		old_jabatan.setDeskripsi(jabatan.getDeskripsi());
		old_jabatan.setGajiPokok(jabatan.getGajiPokok());
	}

	@Override
	public void deleteJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.delete(jabatan);
		
	}

	@Override
	public JabatanModel getJabatanDetailById(long Id) {
		// TODO Auto-generated method stub
		return jabatanDb.findById(Id);
	}

	@Override
	public JabatanModel getJabatanDetailByNama(String nama) {
		// TODO Auto-generated method stub
		return jabatanDb.findByNama(nama);
	}

	@Override
	public List<JabatanModel> getAllJabatan() {
		// TODO Auto-generated method stub
		return jabatanDb.findAll();
	}
	
}
