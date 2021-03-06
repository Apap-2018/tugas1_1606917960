package com.apap.tugasapap.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugasapap.model.JabatanModel;
import com.apap.tugasapap.model.JabatanPegawaiModel;
import com.apap.tugasapap.model.PegawaiModel;
import com.apap.tugasapap.service.JabatanPegawaiService;
import com.apap.tugasapap.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@RequestMapping(value="/jabatan/tambah", method=RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "addJabatan";
	}
	
	@RequestMapping(value="/jabatan/tambah", method=RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		return "add";
	}
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String viewJabatan(@RequestParam("id") long id, Model model){
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		model.addAttribute("jabatan", jabatan);
		return "viewJabatan";
	}
	
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	private String viewAllJabatan(Model model){
		List<JabatanModel> jabatan = jabatanService.getAllJabatan();
		
		//List<JabatanPegawaiModel> jabatanPegawai = jabatanPegawaiService.getAllJabatanPegawai();
		//System.out.println(jabatanPegawai.get(0).getJabatan());
		model.addAttribute("jabatan", jabatan);
		return "viewAllJabatan";
	}
	
	@RequestMapping(value="/jabatan/hapus/{id}", method = RequestMethod.GET)
	private String deleteJabatan(@PathVariable("id") long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		model.addAttribute("jabatan", jabatan);
		jabatanService.deleteJabatan(jabatan);
		return "delete";
	}
	
	@RequestMapping(value="/jabatan/ubah/{id}", method = RequestMethod.GET)
	private String updateJabatan(@PathVariable(value="id") long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("newJabatan", new JabatanModel());
		return "updateJabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		model.addAttribute("jabatan", jabatan);
		jabatanService.updateJabatan(jabatan);
		return "update";
	}
	
	@RequestMapping(value="jabatan/detail/{id}", method = RequestMethod.GET)
	private String viewDetailJabatan(@PathVariable(value="id") long idJabatan, Model model) {
		List<PegawaiModel> hasilPegawai = new ArrayList<PegawaiModel>();
		
		JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan);
		List<JabatanPegawaiModel> jabatanPegawai = jabatan.getJabatanPegawaiList();
		for(JabatanPegawaiModel jabat : jabatanPegawai) {
			hasilPegawai.add(jabat.getPegawai());
		}
		
		String nama = jabatan.getNama();
		
		model.addAttribute("nama", nama);
		model.addAttribute("listJabatanPegawai", hasilPegawai);
		return "detailJabatanPegawai";
	}
}
