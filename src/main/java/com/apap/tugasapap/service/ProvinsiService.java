package com.apap.tugasapap.service;

import java.util.List;

import com.apap.tugasapap.model.ProvinsiModel;

public interface ProvinsiService {
	List<ProvinsiModel> getAllProvinsi();
	ProvinsiModel getProvinsiDetailById(long id);
}
