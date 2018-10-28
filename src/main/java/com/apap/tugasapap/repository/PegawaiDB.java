package com.apap.tugasapap.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugasapap.model.InstansiModel;
import com.apap.tugasapap.model.JabatanModel;
import com.apap.tugasapap.model.PegawaiModel;
import com.apap.tugasapap.model.ProvinsiModel;

@Repository
public interface PegawaiDB extends JpaRepository<PegawaiModel, Long> {
	PegawaiModel findBynip(String nip);
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
	List<PegawaiModel> findByinstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	List<PegawaiModel> findByTanggalLahirAndTahunMasukAndInstansi(Date tanggalLahir, String tahunMasuk, InstansiModel instansi);
	
}
