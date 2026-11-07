import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Halaman Formulir (Card 1) dengan Fitur Multi-Step Wizard:
 * - Anti-Glitch saat Resize: Menggunakan layout terpusat yang halus dan outer scrollpane tanpa konflik nested scrollbars.
 * - Icon Centang Grafis: Menggunakan createCheckmarkIcon sehingga tidak pernah muncul kotak kosong (▯).
 */
public class PanelFormulir extends JPanel {

    private final Main mainFrame;

    public PanelIdentitas panelIdentitas;
    public PanelKegiatan panelKegiatan;
    public PanelCatatan panelCatatan;

    private CardLayout stepCardLayout;
    private JPanel stepContainer;

    // Komponen Stepper Header
    private JPanel pillStep1;
    private JLabel lblStep1Text;
    private JPanel pillStep2;
    private JLabel lblStep2Text;
    private JPanel pillStep3;
    private JLabel lblStep3Text;

    private int currentStep = 1;

    public PanelFormulir(Main mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setOpaque(true); // Opaque true mencegah tearing/flickering pada background repaint

        // 1. Header Atas pada Latar Belakang Ungu
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(new EmptyBorder(14, 28, 8, 28));

        JLabel lblTopTitle = new JLabel("Pendaftaran Kegiatan Mahasiswa", JLabel.CENTER);
        lblTopTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTopTitle.setForeground(Color.WHITE);

        JButton btnTopHelp = new JButton("Bantuan") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isRollover()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(255, 255, 255, 45));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        btnTopHelp.setContentAreaFilled(false);
        btnTopHelp.setBorderPainted(false);
        btnTopHelp.setFocusPainted(false);
        btnTopHelp.setForeground(new Color(238, 242, 255));
        btnTopHelp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnTopHelp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTopHelp.addActionListener(e -> mainFrame.showCard(Main.CARD_BANTUAN));

        topBar.add(lblTopTitle, BorderLayout.CENTER);
        topBar.add(btnTopHelp, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // 2. Kartu Putih Melengkung (Floating White Card)
        JPanel whiteCard = new JPanel(new BorderLayout(0, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Drop shadow lembut
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fillRoundRect(3, 4, getWidth() - 6, getHeight() - 6, 22, 22);
                // Kartu putih
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 5, 22, 22);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                int parentW = getParent() != null ? getParent().getWidth() : 0;
                int targetW;
                if (parentW > 0) {
                    targetW = Math.min(840, Math.max(540, parentW - 32));
                } else {
                    targetW = 680;
                }
                return new Dimension(targetW, d.height);
            }
        };
        whiteCard.setOpaque(false);
        whiteCard.setBorder(new EmptyBorder(16, 22, 16, 22));

        // Stepper Bar Interaktif di Atas Kartu Putih
        JPanel stepperPanel = createInteractiveStepper();
        whiteCard.add(stepperPanel, BorderLayout.NORTH);

        // Inisialisasi Panel Form Input
        panelIdentitas = new PanelIdentitas(mainFrame);
        panelKegiatan = new PanelKegiatan(mainFrame);
        panelCatatan = new PanelCatatan(mainFrame);

        // Container CardLayout untuk berpindah antar Langkah Form
        stepCardLayout = new CardLayout();
        stepContainer = new JPanel(stepCardLayout);
        stepContainer.setOpaque(false);

        // Card Langkah 1: Identitas Peserta
        JPanel step1View = createStep1View();
        // Card Langkah 2: Pilihan Kegiatan & Catatan
        JPanel step2View = createStep2View();

        stepContainer.add(step1View, "STEP_1");
        stepContainer.add(step2View, "STEP_2");

        whiteCard.add(stepContainer, BorderLayout.CENTER);

        // Wrapper Card Terpusat Responsif (Scrollable)
        ScrollablePanel centerCardWrapper = new ScrollablePanel(new GridBagLayout());
        centerCardWrapper.setOpaque(false);
        centerCardWrapper.setBorder(new EmptyBorder(4, 16, 16, 16));

        GridBagConstraints gbcCard = new GridBagConstraints();
        gbcCard.gridx = 0;
        gbcCard.gridy = 0;
        gbcCard.weightx = 1.0;
        gbcCard.weighty = 0.0;
        gbcCard.anchor = GridBagConstraints.NORTH;
        gbcCard.fill = GridBagConstraints.NONE;
        centerCardWrapper.add(whiteCard, gbcCard);

        // Outer ScrollPane halus untuk mengatasi window resize kecil tanpa membuat tombol terpotong
        JScrollPane outerScroll = new JScrollPane(centerCardWrapper);
        outerScroll.setBorder(null);
        outerScroll.setOpaque(false);
        outerScroll.getViewport().setOpaque(false);
        outerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        outerScroll.getVerticalScrollBar().setUnitIncrement(16);

        add(outerScroll, BorderLayout.CENTER);

        // Mulai pada Langkah 1
        setStep(1);
    }

    /**
     * Tampilan Langkah 1: Identitas Peserta
     */
    private JPanel createStep1View() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setOpaque(false);

        // Header teks langkah 1
        JPanel titleBox = new JPanel(new GridLayout(0, 1, 0, 2));
        titleBox.setOpaque(false);
        titleBox.setBorder(new EmptyBorder(4, 0, 8, 0));

        JLabel lblStep = new JLabel("Langkah 1 dari 3", JLabel.CENTER);
        lblStep.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblStep.setForeground(UIStyle.PURPLE_PRIMARY);

        JLabel lblTitle = new JLabel("Identitas Peserta", JLabel.CENTER);
        lblTitle.setFont(UIStyle.FONT_BIG_TITLE);
        lblTitle.setForeground(UIStyle.TEXT_DARK);

        JLabel lblSub = new JLabel("Lengkapi data identitas diri Anda untuk memulai pendaftaran.", JLabel.CENTER);
        lblSub.setFont(UIStyle.FONT_REGULAR);
        lblSub.setForeground(UIStyle.TEXT_MUTED);

        titleBox.add(lblStep);
        titleBox.add(lblTitle);
        titleBox.add(lblSub);
        p.add(titleBox, BorderLayout.NORTH);

        // Body Form Identitas langsung tanpa scrollpane bertingkat
        p.add(panelIdentitas, BorderLayout.CENTER);

        // Tombol Bawah Langkah 1
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(12, 10, 4, 10));

        JButton btnReset = UIStyle.createSecondaryButton("< Reset Form", 115, 36);
        btnReset.addActionListener(new TombolResetListener(mainFrame));
        footer.add(btnReset, BorderLayout.WEST);

        JPanel rightBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightBtns.setOpaque(false);

        JButton btnHelp = UIStyle.createSecondaryButton("Bantuan", 90, 36);
        btnHelp.addActionListener(e -> mainFrame.showCard(Main.CARD_BANTUAN));

        JButton btnExit = UIStyle.createSecondaryButton("Keluar", 85, 36);
        btnExit.addActionListener(new TombolKeluarListener(mainFrame));

        JButton btnNext = UIStyle.createPurpleButton("Lanjut: Pilihan Kegiatan  >", 200, 36);
        btnNext.addActionListener(e -> {
            if (validateStep1()) {
                setStep(2);
            }
        });

        rightBtns.add(btnHelp);
        rightBtns.add(btnExit);
        rightBtns.add(btnNext);
        footer.add(rightBtns, BorderLayout.EAST);

        p.add(footer, BorderLayout.SOUTH);
        return p;
    }

    /**
     * Tampilan Langkah 2: Pilihan Kegiatan & Catatan
     */
    private JPanel createStep2View() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setOpaque(false);

        // Header teks langkah 2
        JPanel titleBox = new JPanel(new GridLayout(0, 1, 0, 2));
        titleBox.setOpaque(false);
        titleBox.setBorder(new EmptyBorder(4, 0, 8, 0));

        JLabel lblStep = new JLabel("Langkah 2 dari 3", JLabel.CENTER);
        lblStep.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblStep.setForeground(UIStyle.PURPLE_PRIMARY);

        JLabel lblTitle = new JLabel("Pilihan Kegiatan & Fasilitas", JLabel.CENTER);
        lblTitle.setFont(UIStyle.FONT_BIG_TITLE);
        lblTitle.setForeground(UIStyle.TEXT_DARK);

        JLabel lblSub = new JLabel("Pilih jenis kegiatan yang ingin diikuti, sesi waktu, dan fasilitas tambahan.", JLabel.CENTER);
        lblSub.setFont(UIStyle.FONT_REGULAR);
        lblSub.setForeground(UIStyle.TEXT_MUTED);

        titleBox.add(lblStep);
        titleBox.add(lblTitle);
        titleBox.add(lblSub);
        p.add(titleBox, BorderLayout.NORTH);

        // Body Form Kegiatan & Catatan
        JPanel bodyForm2 = new JPanel();
        bodyForm2.setLayout(new BoxLayout(bodyForm2, BoxLayout.Y_AXIS));
        bodyForm2.setOpaque(false);
        bodyForm2.add(panelKegiatan);
        bodyForm2.add(Box.createRigidArea(new Dimension(0, 8)));
        bodyForm2.add(panelCatatan);

        p.add(bodyForm2, BorderLayout.CENTER);

        // Tombol Bawah Langkah 2
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(12, 10, 4, 10));

        JButton btnBack = UIStyle.createSecondaryButton("< Kembali ke Identitas", 165, 36);
        btnBack.addActionListener(e -> setStep(1));
        footer.add(btnBack, BorderLayout.WEST);

        JPanel rightBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightBtns.setOpaque(false);

        JButton btnReset = UIStyle.createSecondaryButton("Reset Form", 100, 36);
        btnReset.addActionListener(new TombolResetListener(mainFrame));

        JButton btnExit = UIStyle.createSecondaryButton("Keluar", 85, 36);
        btnExit.addActionListener(new TombolKeluarListener(mainFrame));

        JButton btnSimpan = UIStyle.createPurpleButton("Simpan / Tampilkan  >", 175, 36);
        btnSimpan.addActionListener(new TombolSimpanListener(mainFrame));

        rightBtns.add(btnReset);
        rightBtns.add(btnExit);
        rightBtns.add(btnSimpan);
        footer.add(rightBtns, BorderLayout.EAST);

        p.add(footer, BorderLayout.SOUTH);
        return p;
    }

    /**
     * Validasi Khusus Langkah 1 (Nama, Email, Umur)
     */
    public boolean validateStep1() {
        String nama = mainFrame.txtNama.getText().trim();
        if (nama.isEmpty()) {
            ModernDialog.showWarning(mainFrame, "Validasi Input", "Nama lengkap wajib diisi!");
            mainFrame.txtNama.requestFocus();
            return false;
        }

        String email = mainFrame.txtEmail.getText().trim();
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (email.isEmpty()) {
            ModernDialog.showWarning(mainFrame, "Validasi Input", "Email wajib diisi!");
            mainFrame.txtEmail.requestFocus();
            return false;
        } else if (!email.matches(emailRegex)) {
            ModernDialog.showWarning(mainFrame, "Validasi Input", "Format email tidak valid!\nContoh format yang benar: nama@domain.com");
            mainFrame.txtEmail.requestFocus();
            return false;
        }

        String umurStr = mainFrame.txtUmur.getText().trim();
        if (umurStr.isEmpty()) {
            ModernDialog.showWarning(mainFrame, "Validasi Input", "Umur wajib diisi!");
            mainFrame.txtUmur.requestFocus();
            return false;
        }

        int umur;
        try {
            umur = Integer.parseInt(umurStr);
        } catch (NumberFormatException ex) {
            ModernDialog.showWarning(mainFrame, "Validasi Input", "Umur harus berupa angka bilangan bulat!");
            mainFrame.txtUmur.requestFocus();
            return false;
        }

        if (umur < 15 || umur > 100) {
            ModernDialog.showWarning(mainFrame, "Validasi Input", "Umur harus berupa bilangan bulat antara 15 sampai 100 tahun!");
            mainFrame.txtUmur.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Mengubah Langkah Aktif (1 atau 2) dan Memperbarui Indikator Stepper
     */
    public void setStep(int step) {
        this.currentStep = step;
        if (step == 1) {
            stepCardLayout.show(stepContainer, "STEP_1");
            updateStepperUI(1);
        } else if (step == 2) {
            stepCardLayout.show(stepContainer, "STEP_2");
            updateStepperUI(2);
        }
    }

    public int getCurrentStep() {
        return currentStep;
    }

    /**
     * Membuat Bar Stepper yang Interaktif dan Dapat Diklik dengan Icon Grafis
     */
    private JPanel createInteractiveStepper() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 4));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(0, 0, 8, 0));

        // Tab 1: Identitas Peserta
        pillStep1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4)) {
            @Override
            protected void paintComponent(Graphics g) {
                if (currentStep == 1) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(UIStyle.PURPLE_LIGHT);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        pillStep1.setOpaque(false);
        pillStep1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblStep1Text = new JLabel(" 1. Identitas Peserta");
        lblStep1Text.setIcon(UIStyle.createActiveStepDotIcon(16));
        pillStep1.add(lblStep1Text);
        pillStep1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setStep(1);
            }
        });

        // Tab 2: Pilihan Kegiatan
        pillStep2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4)) {
            @Override
            protected void paintComponent(Graphics g) {
                if (currentStep == 2) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(UIStyle.PURPLE_LIGHT);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        pillStep2.setOpaque(false);
        pillStep2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblStep2Text = new JLabel(" 2. Pilihan Kegiatan");
        lblStep2Text.setIcon(UIStyle.createPendingStepDotIcon(16));
        pillStep2.add(lblStep2Text);
        pillStep2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (validateStep1()) {
                    setStep(2);
                }
            }
        });

        // Tab 3: Ringkasan & Konfirmasi
        pillStep3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        pillStep3.setOpaque(false);
        lblStep3Text = new JLabel(" 3. Ringkasan & Konfirmasi");
        lblStep3Text.setIcon(UIStyle.createPendingStepDotIcon(16));
        pillStep3.add(lblStep3Text);

        p.add(pillStep1);
        p.add(pillStep2);
        p.add(pillStep3);
        return p;
    }

    /**
     * Memperbarui visual indikator stepper saat langkah berpindah
     * Menggunakan icon grafis murni sehingga bebas dari bug missing font (▯).
     */
    private void updateStepperUI(int activeStep) {
        if (activeStep == 1) {
            lblStep1Text.setIcon(UIStyle.createActiveStepDotIcon(16));
            lblStep1Text.setText(" 1. Identitas Peserta");
            lblStep1Text.setFont(UIStyle.FONT_BUTTON);
            lblStep1Text.setForeground(UIStyle.PURPLE_PRIMARY);

            lblStep2Text.setIcon(UIStyle.createPendingStepDotIcon(16));
            lblStep2Text.setText(" 2. Pilihan Kegiatan");
            lblStep2Text.setFont(UIStyle.FONT_REGULAR);
            lblStep2Text.setForeground(UIStyle.TEXT_MUTED);

            lblStep3Text.setIcon(UIStyle.createPendingStepDotIcon(16));
            lblStep3Text.setText(" 3. Ringkasan & Konfirmasi");
            lblStep3Text.setFont(UIStyle.FONT_REGULAR);
            lblStep3Text.setForeground(UIStyle.TEXT_MUTED);
        } else if (activeStep == 2) {
            // Memberikan icon centang hijau grafis yang rapi (Bukan karakter Unicode font)
            lblStep1Text.setIcon(UIStyle.createCheckmarkIcon(16));
            lblStep1Text.setText(" 1. Identitas Peserta");
            lblStep1Text.setFont(UIStyle.FONT_BUTTON);
            lblStep1Text.setForeground(UIStyle.SUCCESS_GREEN);

            lblStep2Text.setIcon(UIStyle.createActiveStepDotIcon(16));
            lblStep2Text.setText(" 2. Pilihan Kegiatan");
            lblStep2Text.setFont(UIStyle.FONT_BUTTON);
            lblStep2Text.setForeground(UIStyle.PURPLE_PRIMARY);

            lblStep3Text.setIcon(UIStyle.createPendingStepDotIcon(16));
            lblStep3Text.setText(" 3. Ringkasan & Konfirmasi");
            lblStep3Text.setFont(UIStyle.FONT_REGULAR);
            lblStep3Text.setForeground(UIStyle.TEXT_MUTED);
        }
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(
                0, 0, UIStyle.PURPLE_GRADIENT_START,
                0, getHeight(), UIStyle.PURPLE_GRADIENT_END
        );
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }

    private static class ScrollablePanel extends JPanel implements Scrollable {
        public ScrollablePanel(LayoutManager layout) {
            super(layout);
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 16;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 64;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }
}
