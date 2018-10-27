package com.apap.tugasapap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugasapap.model.InstansiModel;

@Repository
public interface InstansiDB extends JpaRepository<InstansiModel, Long>{
	InstansiModel findById(long id);
}
