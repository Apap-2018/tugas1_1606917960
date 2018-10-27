package com.apap.tugasapap.service;

import java.util.List;

import com.apap.tugasapap.model.InstansiModel;
import com.apap.tugasapap.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailBynip(String nip);
	void addPegawai(PegawaiModel pegawai);
	void updatePegawai(PegawaiModel pegawai);
	void deletePegawai(PegawaiModel pegawai);
	List<PegawaiModel> getAllPegawai();
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	int hitungGaji(String nip);
	String buatNip(InstansiModel instansi, PegawaiModel pegawai);
}
