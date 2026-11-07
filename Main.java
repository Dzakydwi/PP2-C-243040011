import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;


public class Main extends JFrame {

    public static final String CARD_FORM = "CARD_FORM";
    public static final String CARD_RINGKASAN = "CARD_RINGKASAN";
    public static final String CARD_BANTUAN = "CARD_BANTUAN";

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public PanelFormulir panelFormulir;
    public PanelRingkasan panelRingkasan;
    public PanelBantuan panelBantuan;

    // Referensi langsung komponen untuk kemudahan akses listener
    public JTextField txtNama;
    public JTextField txtEmail;
    public JTextField txtUmur;
    public JTextField txtNim;
    public JComboBox<String> cmbProdi;
    public JRadioButton rbLaki;
    public JRadioButton rbPerempuan;
    public ButtonGroup bgGender;

    public JComboBox<String> cmbKegiatan;
    public JRadioButton rbPagi;
    public JRadioButton rbSiang;
    public ButtonGroup bgSesi;
    public JCheckBox cbSertifikat;
    public JCheckBox cbKonsumsi;
    public JCheckBox cbModul;

    public JTextArea txtCatatan;
    public JCheckBox cbSetuju;

    // Status pemantauan perubahan data yang belum disimpan
    private boolean unsavedChanges = false;
    private boolean isResetting = false;

    public Main() {
        super("Aplikasi Pendaftaran Kegiatan Mahasiswa");
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(720, 720));
        setPreferredSize(new Dimension(780, 790));

        // Tangani event klik tombol silang (X) jendela
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                konfirmasiKeluar();
            }
        });

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Buat 3 panel halaman
        panelFormulir = new PanelFormulir(this);
        panelRingkasan = new PanelRingkasan(this);
        panelBantuan = new PanelBantuan(this);

        // Petakan komponen form ke field Main agar mudah diakses listener
        txtNama = panelFormulir.panelIdentitas.txtNama;
        txtEmail = panelFormulir.panelIdentitas.txtEmail;
        txtUmur = panelFormulir.panelIdentitas.txtUmur;
        txtNim = panelFormulir.panelIdentitas.txtNim;
        cmbProdi = panelFormulir.panelIdentitas.cmbProdi;
        rbLaki = panelFormulir.panelIdentitas.rbLaki;
        rbPerempuan = panelFormulir.panelIdentitas.rbPerempuan;
        bgGender = panelFormulir.panelIdentitas.bgGender;

        cmbKegiatan = panelFormulir.panelKegiatan.cmbKegiatan;
        rbPagi = panelFormulir.panelKegiatan.rbPagi;
        rbSiang = panelFormulir.panelKegiatan.rbSiang;
        bgSesi = panelFormulir.panelKegiatan.bgSesi;
        cbSertifikat = panelFormulir.panelKegiatan.cbSertifikat;
        cbKonsumsi = panelFormulir.panelKegiatan.cbKonsumsi;
        cbModul = panelFormulir.panelKegiatan.cbModul;

        txtCatatan = panelFormulir.panelCatatan.txtCatatan;
        cbSetuju = panelFormulir.panelCatatan.cbSetuju;

        // Tambahkan ke container CardLayout
        cardPanel.add(panelFormulir, CARD_FORM);
        cardPanel.add(panelRingkasan, CARD_RINGKASAN);
        cardPanel.add(panelBantuan, CARD_BANTUAN);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(cardPanel, BorderLayout.CENTER);
        ((JComponent) getContentPane()).setDoubleBuffered(true);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Berpindah antar halaman dengan CardLayout tanpa membuka jendela baru
     */
    public void showCard(String cardName) {
        cardLayout.show(cardPanel, cardName);
    }

    /**
     * Tandai bahwa pengguna telah mengetik/mengubah data pada formulir
     */
    public void markAsModified() {
        if (!isResetting) {
            this.unsavedChanges = true;
        }
    }

    public void setUnsavedChanges(boolean val) {
        this.unsavedChanges = val;
    }

    public boolean hasUnsavedChanges() {
        return this.unsavedChanges;
    }

    public void setResetting(boolean val) {
        this.isResetting = val;
    }

    /**
     * Logika konfirmasi keluar aplikasi:
     * - Jika ada perubahan yang belum disimpan -> tampilkan peringatan spesifik.
     * - Pengguna dapat membatalkan keluar dengan memilih NO atau menutup dialog.
     */
    public boolean konfirmasiKeluar() {
        if (hasUnsavedChanges()) {
            int confirm = ModernDialog.showConfirm(
                    this,
                    "Konfirmasi Keluar",
                    "Ada perubahan data yang belum disimpan.\nApakah Anda yakin ingin keluar?"
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
                return true;
            } else {
                return false; // Pembatalan konfirmasi keluar
            }
        } else {
            int confirm = ModernDialog.showConfirm(
                    this,
                    "Konfirmasi Keluar",
                    "Apakah Anda yakin ingin keluar dari aplikasi?"
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
                return true;
            } else {
                return false; // Pembatalan konfirmasi keluar
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
