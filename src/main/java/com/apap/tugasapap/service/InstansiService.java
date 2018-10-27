package com.apap.tugasapap.service;

import java.util.List;

import com.apap.tugasapap.model.InstansiModel;

public interface InstansiService {
	List<InstansiModel> getAllInstansi();
	InstansiModel getInstansiDetailById(long id);
}
