package com.apap.tugasapap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugasapap.service.ProvinsiService;
import com.apap.tugasapap.model.InstansiModel;
import com.apap.tugasapap.model.ProvinsiModel;


@Controller
public class InstansiController {
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping(value = "/instansi/tambah", method = RequestMethod.GET)
	public @ResponseBody List<InstansiModel> instansiSearch(@RequestParam(value = "idProvinsi", required = true) long idProvinsi){
		ProvinsiModel provinsi = provinsiService.getProvinsiDetailById(idProvinsi);
		return provinsi.getInstansiList();
	}
}
