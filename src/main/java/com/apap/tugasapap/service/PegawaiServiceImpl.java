package com.apap.tugasapap.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugasapap.model.InstansiModel;
import com.apap.tugasapap.model.JabatanPegawaiModel;
import com.apap.tugasapap.model.PegawaiModel;
import com.apap.tugasapap.model.ProvinsiModel;
import com.apap.tugasapap.repository.PegawaiDB;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDB pegawaiDb;
	
	@Override
	public PegawaiModel getPegawaiDetailBynip(String nip) {
		// TODO Auto-generated method stub
		return pegawaiDb.findBynip(nip);
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		pegawaiDb.save(pegawai);
	}

	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		PegawaiModel oldPegawai = this.getPegawaiDetailBynip(pegawai.getNip());
		oldPegawai.setNama(pegawai.getNama());
		oldPegawai.setTahunMasuk(pegawai.getTahunMasuk());
		oldPegawai.setTanggalLahir(pegawai.getTanggalLahir());
		oldPegawai.setNip(pegawai.getNip());
		oldPegawai.setInstansi(pegawai.getInstansi());
		oldPegawai.setTempatLahir(pegawai.getTempatLahir());
		
		for (int i = 0; i<oldPegawai.getJabatanPegawaiList().size(); i++) {
			oldPegawai.getJabatanPegawaiList().get(i).setJabatan(pegawai.getJabatanPegawaiList().get(i).getJabatan());
		}
		
	}

	@Override
	public void deletePegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		pegawaiDb.delete(pegawai);
	}

	@Override
	public List<PegawaiModel> getAllPegawai() {
		// TODO Auto-generated method stub
		return pegawaiDb.findAll();
	}

	@Override
	public List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByinstansiOrderByTanggalLahirAsc(instansi);
	}

	@Override
	public int hitungGaji(String nip) {
		// TODO Auto-generated method stub
		PegawaiModel pegawai = pegawaiDb.findBynip(nip);
		List<JabatanPegawaiModel> listJabatan = pegawai.getJabatanPegawaiList();
		double presentase_tunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan() / 100;
		
		List<Integer> listGaji = new ArrayList<>();
		for (int i = 0; i<listJabatan.size(); i++) {
			int gaji = (int) (listJabatan.get(i).getJabatan().getGajiPokok() * (1+presentase_tunjangan));
			listGaji.add(gaji);
		}
		
		int max_gaji = 0;
		for (int i = 0; i<listGaji.size(); i++) {
			if (max_gaji < listGaji.get(i)) {
				max_gaji = listGaji.get(i);
			}
		}
		return max_gaji;
	}

	@Override
	public String buatNip(InstansiModel instansi, PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		String nip = "";
		nip += instansi.getId();
		
		Date tanggal_lahir = pegawai.getTanggalLahir();
		String[] tglLahir = (""+tanggal_lahir).split("-");
		for (int i = tglLahir.length-1; i >= 0; i--) {
			int ukuranTgl = tglLahir[i].length();
			nip += tglLahir[i].substring(ukuranTgl-2, ukuranTgl);
		}
		
		nip += pegawai.getTahunMasuk();
		
		List<PegawaiModel> listPegawai = pegawaiDb.findByTanggalLahirAndTahunMasukAndInstansi(pegawai.getTanggalLahir(), pegawai.getTahunMasuk(), pegawai.getInstansi());
		
		int banyakPegawai = listPegawai.size();
		
		if (banyakPegawai >= 10) {
			nip += banyakPegawai;
		}
		else {
			nip += "0" + (banyakPegawai+1);
		}
		return nip;
		
	}
	
	

}
