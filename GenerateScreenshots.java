import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

/**
 * Utilitas untuk menghasilkan tangkapan layar (screenshots) aplikasi
 * termasuk Multi-Step Formulir (Langkah 1 & Langkah 2), Ringkasan, Bantuan, dan Pop-up Glassmorphism.
 */
public class GenerateScreenshots {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                File dir = new File("screenshots");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                Main mainFrame = new Main();
                mainFrame.addNotify();
                mainFrame.setSize(750, 790);
                mainFrame.validate();

                // 1. Screenshot Halaman Formulir - Langkah 1 (Identitas)
                mainFrame.txtNama.setText("Dzaky Mahasiswa");
                mainFrame.txtEmail.setText("dzaky@example.com");
                mainFrame.txtUmur.setText("21");
                mainFrame.txtNim.setText("243040011");
                mainFrame.cmbProdi.setSelectedIndex(1);
                mainFrame.rbLaki.setSelected(true);

                mainFrame.showCard(Main.CARD_FORM);
                mainFrame.panelFormulir.setStep(1);
                mainFrame.getContentPane().validate();
                mainFrame.getContentPane().repaint();
                saveComponentImage(mainFrame.getContentPane(), "screenshots/01_formulir_langkah1_identitas.png");
                saveComponentImage(mainFrame.getContentPane(), "screenshots/01_halaman_formulir.png");

                // 2. Screenshot Halaman Formulir - Langkah 2 (Kegiatan)
                mainFrame.cmbKegiatan.setSelectedIndex(2);
                mainFrame.rbPagi.setSelected(true);
                mainFrame.cbSertifikat.setSelected(true);
                mainFrame.cbKonsumsi.setSelected(true);
                mainFrame.txtCatatan.setText("Ingin mempelajari integrasi antarmuka Java Swing dan arsitektur event-driven.");
                mainFrame.cbSetuju.setSelected(true);

                mainFrame.panelFormulir.setStep(2);
                mainFrame.getContentPane().validate();
                mainFrame.getContentPane().repaint();
                saveComponentImage(mainFrame.getContentPane(), "screenshots/01b_formulir_langkah2_kegiatan.png");

                // 3. Screenshot Halaman Ringkasan (Langkah 3)
                DataPendaftaran data = new DataPendaftaran();
                data.setNama("Dzaky Mahasiswa");
                data.setEmail("dzaky@example.com");
                data.setUmur(21);
                data.setNim("243040011");
                data.setProdi("Teknik Informatika");
                data.setJenisKelamin("Laki-laki");
                data.setKegiatan("Workshop: Desain Antarmuka Modern dengan Java Swing");
                data.setSesi("Pagi (08:30 - 12:00 WIB)");
                List<String> fas = new ArrayList<>();
                fas.add("Sertifikat Cetak");
                fas.add("Makan Siang / Konsumsi");
                data.setFasilitas(fas);
                data.setCatatan("Ingin mempelajari integrasi antarmuka Java Swing dan arsitektur event-driven.");
                data.setTanggalWaktuDaftar("06-11-2026 21:30:00");

                mainFrame.panelRingkasan.tampilkanData(data);
                mainFrame.showCard(Main.CARD_RINGKASAN);
                mainFrame.getContentPane().validate();
                mainFrame.getContentPane().repaint();
                saveComponentImage(mainFrame.getContentPane(), "screenshots/02_halaman_ringkasan.png");

                // 4. Screenshot Halaman Bantuan
                mainFrame.showCard(Main.CARD_BANTUAN);
                mainFrame.getContentPane().validate();
                mainFrame.getContentPane().repaint();
                saveComponentImage(mainFrame.getContentPane(), "screenshots/03_halaman_bantuan.png");

                // 5. Screenshot Pop-up Glassmorphism: Validasi Input
                ModernDialog dlgWarning = new ModernDialog(mainFrame, "Validasi Input", "Nama lengkap wajib diisi!", ModernDialog.Type.WARNING);
                dlgWarning.addNotify();
                dlgWarning.validate();
                saveComponentImage(dlgWarning.getContentPane(), "screenshots/04_popup_validasi_input.png");
                dlgWarning.dispose();

                // 6. Screenshot Pop-up Glassmorphism: Konfirmasi Keluar
                ModernDialog dlgConfirm = new ModernDialog(mainFrame, "Konfirmasi Keluar", "Apakah Anda yakin ingin keluar dari aplikasi?", ModernDialog.Type.CONFIRM);
                dlgConfirm.addNotify();
                dlgConfirm.validate();
                saveComponentImage(dlgConfirm.getContentPane(), "screenshots/05_popup_konfirmasi_keluar.png");
                dlgConfirm.dispose();

                // 7. Screenshot Pop-up Glassmorphism: Pendaftaran Sukses
                ModernDialog dlgSuccess = new ModernDialog(mainFrame, "Pendaftaran Sukses", "Pendaftaran berhasil disimpan!\nMenampilkan halaman ringkasan data.", ModernDialog.Type.SUCCESS);
                dlgSuccess.addNotify();
                dlgSuccess.validate();
                saveComponentImage(dlgSuccess.getContentPane(), "screenshots/06_popup_pendaftaran_sukses.png");
                dlgSuccess.dispose();

                System.out.println("Semua screenshot berhasil dibuat!");
                mainFrame.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void saveComponentImage(Component comp, String filename) throws Exception {
        BufferedImage image = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        comp.printAll(g2d);
        g2d.dispose();
        ImageIO.write(image, "png", new File(filename));
        System.out.println("Tersimpan: " + filename + " (" + image.getWidth() + "x" + image.getHeight() + ")");
    }
}
