package com.apap.tugasapap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugasapap.model.JabatanPegawaiModel;

@Repository
public interface PegawaiJabatanDB extends JpaRepository<JabatanPegawaiModel, Long>{

}
