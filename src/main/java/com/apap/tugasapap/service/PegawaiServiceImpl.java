	package com.apap.tugasapap.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugasapap.model.InstansiModel;
import com.apap.tugasapap.model.JabatanModel;
import com.apap.tugasapap.model.JabatanPegawaiModel;
import com.apap.tugasapap.model.PegawaiModel;
import com.apap.tugasapap.model.ProvinsiModel;
import com.apap.tugasapap.repository.JabatanDB;
import com.apap.tugasapap.repository.PegawaiDB;
import com.apap.tugasapap.repository.PegawaiJabatanDB;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDB pegawaiDb;
	
	@Autowired
	private JabatanDB jabatanDb;
	
	@Autowired
	private PegawaiJabatanDB PegawaiJabatanDb;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
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
		System.out.println(pegawai.getNama());
		PegawaiModel oldPegawai = this.getPegawaiDetailBynip(pegawai.getNip());
		
		String new_nip = this.buatNip(pegawai.getInstansi(), pegawai);
		oldPegawai.setNip(new_nip);
		
		oldPegawai.setNama(pegawai.getNama());
		oldPegawai.setTahunMasuk(pegawai.getTahunMasuk());
		oldPegawai.setTanggalLahir(pegawai.getTanggalLahir());
		
		oldPegawai.setInstansi(pegawai.getInstansi());
		oldPegawai.setTempatLahir(pegawai.getTempatLahir());
		
		int oldSize = oldPegawai.getJabatanPegawaiList().size();
		int newSize = pegawai.getJabatanPegawaiList().size();
		System.out.println(oldSize);
		System.out.println(newSize);
		
		for (int i = 0; i< oldSize; i++) {
			oldPegawai.getJabatanPegawaiList().get(i).setJabatan(pegawai.getJabatanPegawaiList().get(i).getJabatan());
		}
		
		for (int i = oldSize; i < pegawai.getJabatanPegawaiList().size(); i++) {
			pegawai.getJabatanPegawaiList().get(i).setPegawai(oldPegawai);
			PegawaiJabatanDb.save(pegawai.getJabatanPegawaiList().get(i));
		}
		
		if (newSize >= oldSize) {
			System.out.println("masuk");
			for (int i = oldSize; i<newSize; i++) {
				JabatanPegawaiModel baru = pegawai.getJabatanPegawaiList().get(i);
				baru.setPegawai(oldPegawai);
				jabatanPegawaiService.addJabatanPegawai(baru);
			}
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

	@Override
	public List<PegawaiModel> findPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan) {
		// TODO Auto-generated method stub
		List<PegawaiModel> pegawaiInstansi = instansi.getPegawaiList();
		List<PegawaiModel> search = new ArrayList<>();
		long idJabatan = jabatan.getId();
		
		for(PegawaiModel peg : pegawaiInstansi) {
			for(JabatanPegawaiModel jab: peg.getJabatanPegawaiList()) {
				if(jab.getJabatan().getId() == idJabatan) {
					search.add(peg);
					}
				}
		}
		return search;
	}

	@Override
	public List<PegawaiModel> findPegawaiByProvinsiAndJabatan(ProvinsiModel provinsi,
			JabatanModel jabatan) {
		// TODO Auto-generated method stub
		List<PegawaiModel> search = new ArrayList<>();
		List<JabatanPegawaiModel> listPegawaiJabatan = 
				jabatanDb.findById(jabatan.getId()).getJabatanPegawaiList();
		
		for (int i = 0; i < listPegawaiJabatan.size(); i++) {
			if (listPegawaiJabatan.get(i).getPegawai().getInstansi().getProvinsi().getId() == provinsi.getId()) {
				search.add(listPegawaiJabatan.get(i).getPegawai());
			}
		}
		return search;
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(
			Date tanggalLahir, String tahunMasuk, InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByTanggalLahirAndTahunMasukAndInstansi(tanggalLahir, tahunMasuk, instansi);
	}

	@Override
	public List<PegawaiModel> findPegawaiByInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansi(instansi);
	}

	@Override
	public List<PegawaiModel> findPegawaiByProvinsi(Long idProvinsi) {
		// TODO Auto-generated method stub
		List<PegawaiModel> search = new ArrayList<>();
		
		for(PegawaiModel pegawai : pegawaiDb.findAll()) {
			if (pegawai.getInstansi().getProvinsi().getId() == idProvinsi) {
				search.add(pegawai);
			}
		}
		
		return search;
	}
	
}
