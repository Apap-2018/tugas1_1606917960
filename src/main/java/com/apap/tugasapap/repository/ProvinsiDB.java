package com.apap.tugasapap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tugasapap.model.ProvinsiModel;

public interface ProvinsiDB extends JpaRepository<ProvinsiModel, Long>{
	ProvinsiModel findById(long id);
}
