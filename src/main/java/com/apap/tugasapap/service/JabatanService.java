package com.apap.tugasapap.service;

import java.util.List;

import com.apap.tugasapap.model.JabatanModel;

public interface JabatanService {
	void addJabatan(JabatanModel jabatan);
	void updateJabatan(JabatanModel jabatan);
	void deleteJabatan(JabatanModel jabatan);
	JabatanModel getJabatanDetailById(long id);
	JabatanModel getJabatanDetailByNama(String nama);
	List<JabatanModel> getAllJabatan();
}
