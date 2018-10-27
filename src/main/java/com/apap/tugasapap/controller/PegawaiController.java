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

import com.apap.tugasapap.model.InstansiModel;
import com.apap.tugasapap.model.JabatanModel;
import com.apap.tugasapap.model.JabatanPegawaiModel;
import com.apap.tugasapap.model.PegawaiModel;
import com.apap.tugasapap.model.ProvinsiModel;
import com.apap.tugasapap.service.InstansiService;
import com.apap.tugasapap.service.JabatanPegawaiService;
import com.apap.tugasapap.service.JabatanService;
import com.apap.tugasapap.service.PegawaiService;
import com.apap.tugasapap.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> jabatan = jabatanService.getAllJabatan();
		List<InstansiModel> instansi = instansiService.getAllInstansi();
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("instansi", instansi);
		return "home";
	}
	
	@RequestMapping(value="/pegawai", method=RequestMethod.GET)
	private String viewPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailBynip(nip);
		List<JabatanPegawaiModel> listJabatan = pegawai.getJabatanPegawaiList();
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("gaji", pegawaiService.hitungGaji(pegawai.getNip()));
		return "viewPegawai";
	}
	
	@RequestMapping(value="/pegawai/termuda-tertua", method=RequestMethod.GET)
	private String viewPegawaiTermudaTertua(@RequestParam("id") long id, Model model) {
		InstansiModel instansi = instansiService.getInstansiDetailById(id);
		List<PegawaiModel> listPegawai =  pegawaiService.findByInstansiOrderByTanggalLahirAsc(instansi);
		
		if (!listPegawai.isEmpty()) {
			int banyakPegawai = listPegawai.size();
			
			PegawaiModel pegawai_muda = listPegawai.get(banyakPegawai-1);
			PegawaiModel pegawai_tua = listPegawai.get(0);
			
			model.addAttribute("gajiMuda", pegawaiService.hitungGaji(pegawai_muda.getNip()));
			model.addAttribute("gajiTua", pegawaiService.hitungGaji(pegawai_tua.getNip()));
			
			model.addAttribute("pegawaiMuda", pegawai_muda);
			model.addAttribute("pegawaiTua", pegawai_tua);
		}
		
		return "termudaTertua";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.GET)
	private String add(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		List<JabatanModel> jabatan = jabatanService.getAllJabatan();
		List<ProvinsiModel> provinsi = provinsiService.getAllProvinsi();
		List<JabatanPegawaiModel> listJabatanPegawai = new ArrayList<JabatanPegawaiModel>();
		pegawai.setJabatanPegawaiList(listJabatanPegawai);
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getJabatanPegawaiList().add(jabatanPegawai);
		List<InstansiModel> instansi = provinsi.get(0).getInstansiList();
		
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("provinsi", provinsi);
		model.addAttribute("instansi", instansi);
		model.addAttribute("pegawai", pegawai);
		return "addPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"addJabatan"})
	public String addJabatan(@ModelAttribute PegawaiModel pegawai_baru, Model model) {
		PegawaiModel pegawai = pegawai_baru;
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getJabatanPegawaiList().add(jabatanPegawai);
		
		List<ProvinsiModel> provinsi = provinsiService.getAllProvinsi();
		List<InstansiModel> instansi = new ArrayList<InstansiModel>();
		instansi = provinsi.get(0).getInstansiList();
		
		List<JabatanModel> jabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("provinsi", provinsi);
		model.addAttribute("instansi", instansi);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("pegawai", pegawai);
		
		return "addPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	public String addJabatanBaru(@ModelAttribute PegawaiModel pegawai, Model model) {
		
		String nip = pegawaiService.buatNip(pegawai.getInstansi(), pegawai);
		pegawai.setNip(nip);
		
		List<JabatanPegawaiModel> jabatan = pegawai.getJabatanPegawaiList();
		pegawai.setJabatanPegawaiList(new ArrayList<JabatanPegawaiModel>());
		
		pegawaiService.addPegawai(pegawai);;
		
		for (int i = 0; i < jabatan.size(); i++) {
			jabatan.get(i).setPegawai(pegawai);
			jabatanPegawaiService.addJabatanPegawai(jabatan.get(i));
		}
		model.addAttribute("nip", nip);
		return "submitPegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.GET)
	private String ubahPegawai(@RequestParam("nip") String nip, Model model) {
		return "updatePegawai";
	}
		

	
	@RequestMapping(value="/pegawai/cari", method=RequestMethod.GET)
	private String cariPegawai(Model model) {
		List<JabatanModel> jabatan = jabatanService.getAllJabatan();
		List<ProvinsiModel> provinsi = provinsiService.getAllProvinsi();
		List<InstansiModel> instansi = provinsi.get(0).getInstansiList();
		
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("provinsi", provinsi);
		model.addAttribute("instansi", instansi);
		return "cariPegawai";
	}
}
