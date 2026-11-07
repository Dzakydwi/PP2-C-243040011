import java.util.ArrayList;
import java.util.List;

/**
 * Model Data untuk menampung informasi pendaftaran kegiatan.
 */
public class DataPendaftaran {
    private String nama;
    private String email;
    private int umur;
    private String nim;
    private String prodi;
    private String jenisKelamin;
    private String kegiatan;
    private String sesi;
    private List<String> fasilitas;
    private String catatan;
    private String tanggalWaktuDaftar;

    public DataPendaftaran() {
        this.fasilitas = new ArrayList<>();
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public String getSesi() {
        return sesi;
    }

    public void setSesi(String sesi) {
        this.sesi = sesi;
    }

    public List<String> getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(List<String> fasilitas) {
        this.fasilitas = fasilitas;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getTanggalWaktuDaftar() {
        return tanggalWaktuDaftar;
    }

    public void setTanggalWaktuDaftar(String tanggalWaktuDaftar) {
        this.tanggalWaktuDaftar = tanggalWaktuDaftar;
    }
}
