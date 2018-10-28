package com.apap.tugasapap.service;

import java.util.List;

import com.apap.tugasapap.model.JabatanPegawaiModel;

public interface JabatanPegawaiService {
	void addJabatanPegawai(JabatanPegawaiModel jabatanPegawai);
	List<JabatanPegawaiModel> getAllJabatanPegawai();
}
