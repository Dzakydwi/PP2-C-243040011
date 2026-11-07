import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Dialog Modern Glassmorphism UI
 * Sesuai desain pop-up baru:
 * 1. Validasi Input (Warning / Error dengan icon orange menyala & tombol OK orange)
 * 2. Konfirmasi Keluar (Confirm dengan icon biru menyala & tombol No / Yes)
 * 3. Pendaftaran Sukses (Success dengan icon checkmark biru menyala & tombol OK biru)
 */
public class ModernDialog extends JDialog {

    public enum Type {
        WARNING,
        CONFIRM,
        SUCCESS
    }

    private int result = JOptionPane.CLOSED_OPTION;
    private Point initialClick;

    public ModernDialog(Window owner, String title, String message, Type type) {
        super(owner, ModalityType.APPLICATION_MODAL);
        setUndecorated(true);
        try {
            setBackground(new Color(0, 0, 0, 0)); // Transparan untuk efek rounded glass
        } catch (Exception ignored) {
        }

        initUI(title, message, type);
    }

    private void initUI(String title, String message, Type type) {
        // Container Utama Kartu Glassmorphism
        JPanel glassCard = new JPanel(new BorderLayout(0, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                int arc = 22;

                // 1. Drop Shadow Halus
                for (int i = 0; i < 5; i++) {
                    g2.setColor(new Color(0, 0, 0, 10 - i * 2));
                    g2.drawRoundRect(i, i + 1, w - 1 - i * 2, h - 1 - i * 2, arc, arc);
                }

                // 2. Background Glassmorphism (Frosted White Translucent)
                GradientPaint glassGradient = new GradientPaint(
                        0, 0, new Color(255, 255, 255, 248),
                        0, h, new Color(245, 248, 255, 230)
                );
                g2.setPaint(glassGradient);
                g2.fillRoundRect(5, 5, w - 10, h - 10, arc, arc);

                // 3. Highlight Border Putih Halus
                g2.setColor(new Color(255, 255, 255, 240));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(5, 5, w - 10, h - 10, arc, arc);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        glassCard.setOpaque(false);
        glassCard.setBorder(new EmptyBorder(16, 22, 18, 22));

        // Memungkinkan dialog di-drag dengan mouse
        glassCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });
        glassCard.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (initialClick != null) {
                    int thisX = getLocation().x;
                    int thisY = getLocation().y;
                    int xMoved = e.getX() - initialClick.x;
                    int yMoved = e.getY() - initialClick.y;
                    setLocation(thisX + xMoved, thisY + yMoved);
                }
            }
        });

        // ==========================================
        // 1. HEADER (Judul + Tombol Tutup X)
        // ==========================================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(new Color(15, 23, 42)); // #0F172A

        JButton btnClose = new JButton("✕") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isRollover()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(0, 0, 0, 15));
                    g2.fillOval(0, 0, getWidth(), getHeight());
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        btnClose.setContentAreaFilled(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnClose.setForeground(new Color(100, 116, 139));
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.setPreferredSize(new Dimension(28, 28));
        btnClose.addActionListener(e -> {
            result = (type == Type.CONFIRM) ? JOptionPane.NO_OPTION : JOptionPane.CLOSED_OPTION;
            dispose();
        });

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnClose, BorderLayout.EAST);
        glassCard.add(headerPanel, BorderLayout.NORTH);

        // ==========================================
        // 2. BODY (Icon Glowing + Teks Pesan)
        // ==========================================
        JPanel bodyPanel = new JPanel(new BorderLayout(16, 0));
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(new EmptyBorder(8, 0, 8, 0));

        // Icon Glowing Sesuai Desain Gambar
        JLabel iconLabel = createGlowingIcon(type);
        bodyPanel.add(iconLabel, BorderLayout.WEST);

        // Teks Pesan
        String formattedMessage = "<html><div style='width: 230px; font-family: Segoe UI; font-size: 11pt; color: #1E293B; line-height: 1.35;'>"
                + message.replace("\n", "<br>") + "</div></html>";
        JLabel lblMessage = new JLabel(formattedMessage);
        bodyPanel.add(lblMessage, BorderLayout.CENTER);

        glassCard.add(bodyPanel, BorderLayout.CENTER);

        // ==========================================
        // 3. FOOTER (Tombol Aksi)
        // ==========================================
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        footerPanel.setOpaque(false);

        if (type == Type.CONFIRM) {
            // Tombol "No" (Glass / Putih Berbingkai)
            JButton btnNo = new JButton("No") {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    if (getModel().isPressed()) {
                        g2.setColor(new Color(226, 232, 240, 240));
                    } else if (getModel().isRollover()) {
                        g2.setColor(new Color(241, 245, 249, 240));
                    } else {
                        g2.setColor(new Color(255, 255, 255, 200));
                    }
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    g2.setColor(new Color(226, 232, 240));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            btnNo.setContentAreaFilled(false);
            btnNo.setFocusPainted(false);
            btnNo.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btnNo.setForeground(new Color(30, 41, 59));
            btnNo.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnNo.setPreferredSize(new Dimension(80, 34));
            btnNo.addActionListener(e -> {
                result = JOptionPane.NO_OPTION;
                dispose();
            });

            // Tombol "Yes" (Biru Menyala)
            JButton btnYes = createStyledPillButton("Yes", new Color(0, 145, 255), new Color(2, 132, 199), 85, 34);
            btnYes.addActionListener(e -> {
                result = JOptionPane.YES_OPTION;
                dispose();
            });

            footerPanel.add(btnNo);
            footerPanel.add(btnYes);
        } else if (type == Type.WARNING) {
            // Tombol "OK" (Orange Menyala)
            JButton btnOk = createStyledPillButton("OK", new Color(245, 158, 11), new Color(217, 119, 6), 90, 34);
            btnOk.addActionListener(e -> {
                result = JOptionPane.OK_OPTION;
                dispose();
            });
            footerPanel.add(btnOk);
        } else {
            // Tombol "OK" (Biru Menyala untuk Sukses)
            JButton btnOk = createStyledPillButton("OK", new Color(0, 145, 255), new Color(2, 132, 199), 90, 34);
            btnOk.addActionListener(e -> {
                result = JOptionPane.OK_OPTION;
                dispose();
            });
            footerPanel.add(btnOk);
        }

        glassCard.add(footerPanel, BorderLayout.SOUTH);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(glassCard, BorderLayout.CENTER);

        // Escape key listener untuk menutup dialog
        getRootPane().registerKeyboardAction(
                e -> {
                    result = (type == Type.CONFIRM) ? JOptionPane.NO_OPTION : JOptionPane.CLOSED_OPTION;
                    dispose();
                },
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        pack();
        setSize(new Dimension(365, 205));
        setLocationRelativeTo(getOwner());
    }

    /**
     * Membuat Ikon Glowing Melingkar (Warning: Orange !, Confirm: Blue ?, Success: Blue ✓)
     */
    private JLabel createGlowingIcon(Type type) {
        return new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int cx = 24;
                int cy = 24;
                int radius = 22;

                if (type == Type.WARNING) {
                    // Glow Orange
                    g2.setColor(new Color(251, 191, 36, 60));
                    g2.fillOval(cx - radius - 4, cy - radius - 4, (radius + 4) * 2, (radius + 4) * 2);

                    // Badge Utama Orange Gradient
                    GradientPaint gp = new GradientPaint(
                            cx - radius, cy - radius, new Color(251, 191, 36),
                            cx + radius, cy + radius, new Color(234, 88, 12)
                    );
                    g2.setPaint(gp);
                    g2.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);

                    // Teks "!" Putih
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
                    FontMetrics fm = g2.getFontMetrics();
                    int x = cx - (fm.stringWidth("!") / 2);
                    int y = cy - (fm.getHeight() / 2) + fm.getAscent() - 1;
                    g2.drawString("!", x, y);

                } else if (type == Type.CONFIRM) {
                    // Glow Biru
                    g2.setColor(new Color(56, 189, 248, 60));
                    g2.fillOval(cx - radius - 4, cy - radius - 4, (radius + 4) * 2, (radius + 4) * 2);

                    // Badge Utama Biru Gradient
                    GradientPaint gp = new GradientPaint(
                            cx - radius, cy - radius, new Color(96, 165, 250),
                            cx + radius, cy + radius, new Color(2, 132, 199)
                    );
                    g2.setPaint(gp);
                    g2.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);

                    // Teks "?" Putih
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 21));
                    FontMetrics fm = g2.getFontMetrics();
                    int x = cx - (fm.stringWidth("?") / 2);
                    int y = cy - (fm.getHeight() / 2) + fm.getAscent() - 1;
                    g2.drawString("?", x, y);

                } else {
                    // Glow Biru/Cyan Sukses
                    g2.setColor(new Color(56, 189, 248, 70));
                    g2.fillOval(cx - radius - 4, cy - radius - 4, (radius + 4) * 2, (radius + 4) * 2);

                    // Badge Utama Biru Gradient
                    GradientPaint gp = new GradientPaint(
                            cx - radius, cy - radius, new Color(56, 189, 248),
                            cx + radius, cy + radius, new Color(0, 122, 255)
                    );
                    g2.setPaint(gp);
                    g2.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);

                    // Teks "✓" Putih
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
                    FontMetrics fm = g2.getFontMetrics();
                    int x = cx - (fm.stringWidth("✓") / 2);
                    int y = cy - (fm.getHeight() / 2) + fm.getAscent();
                    g2.drawString("✓", x, y);
                }

                g2.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(54, 54);
            }
        };
    }

    /**
     * Membuat Tombol Pill Rounded dengan Warna Gradient/Solid dan Hover
     */
    private JButton createStyledPillButton(String text, Color normalColor, Color hoverColor, int width, int height) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(hoverColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(normalColor);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(width, height));
        return btn;
    }

    public int getResult() {
        return result;
    }

    // ==========================================
    // STATIC HELPER METHODS
    // ==========================================

    public static void showWarning(Component parent, String title, String message) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        ModernDialog dialog = new ModernDialog(window, title, message, Type.WARNING);
        dialog.setVisible(true);
    }

    public static void showSuccess(Component parent, String title, String message) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        ModernDialog dialog = new ModernDialog(window, title, message, Type.SUCCESS);
        dialog.setVisible(true);
    }

    public static int showConfirm(Component parent, String title, String message) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        ModernDialog dialog = new ModernDialog(window, title, message, Type.CONFIRM);
        dialog.setVisible(true);
        return dialog.getResult();
    }
}
