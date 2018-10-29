package com.apap.tugasapap.service;

import java.sql.Date;
import java.util.List;

import com.apap.tugasapap.model.InstansiModel;
import com.apap.tugasapap.model.JabatanModel;
import com.apap.tugasapap.model.PegawaiModel;
import com.apap.tugasapap.model.ProvinsiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailBynip(String nip);
	void addPegawai(PegawaiModel pegawai);
	void updatePegawai(PegawaiModel pegawai);
	void deletePegawai(PegawaiModel pegawai);
	List<PegawaiModel> getAllPegawai();
	List<PegawaiModel> findPegawaiByInstansi(InstansiModel instansi);
	List<PegawaiModel> findPegawaiByProvinsi(Long idProvinsi);
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	int hitungGaji(String nip);
	List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(
			Date tanggalLahir, String tahunMasuk, InstansiModel instansi);
	String buatNip(InstansiModel instansi, PegawaiModel pegawai);
	List<PegawaiModel> findPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan);
	List<PegawaiModel> findPegawaiByProvinsiAndJabatan(ProvinsiModel provinsi, JabatanModel jabatan);
	
}
