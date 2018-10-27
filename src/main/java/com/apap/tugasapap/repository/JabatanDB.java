package com.apap.tugasapap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugasapap.model.JabatanModel;

@Repository
public interface JabatanDB extends JpaRepository<JabatanModel, Long>{
	JabatanModel findById(long id);
	JabatanModel findByNama(String nama);	
}
