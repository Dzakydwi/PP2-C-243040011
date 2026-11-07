import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Utilitas dan konstanta tema antarmuka modern (Purple & Card/Ticket Theme).
 * Dilengkapi custom icon untuk Radio Button, Checkbox, ComboBox Renderer, Stepper Icons, dan Focus-Aware Border.
 */
public class UIStyle {

    // Palet Warna Ungu Modern (Sesuai Desain Gambar 1 & Gambar 2)
    public static final Color PURPLE_PRIMARY = new Color(99, 102, 241);     // #6366F1
    public static final Color PURPLE_HOVER = new Color(79, 70, 229);       // #4F46E5
    public static final Color PURPLE_DARK = new Color(67, 56, 202);        // #4338CA
    public static final Color PURPLE_LIGHT = new Color(238, 242, 255);     // #EEF2FF
    public static final Color PURPLE_GRADIENT_START = new Color(99, 102, 241);
    public static final Color PURPLE_GRADIENT_END = new Color(139, 92, 246); // #8B5CF6

    // Warna Teks dan Netral
    public static final Color TEXT_DARK = new Color(15, 23, 42);          // #0F172A
    public static final Color TEXT_MUTED = new Color(100, 116, 139);      // #64748B
    public static final Color BORDER_LIGHT = new Color(226, 232, 240);    // #E2E8F0
    public static final Color BORDER_FOCUS = new Color(99, 102, 241);     // #6366F1
    public static final Color BG_LIGHT = new Color(248, 250, 252);        // #F8FAFC
    public static final Color SUCCESS_GREEN = new Color(16, 185, 129);    // #10B981
    public static final Color SUCCESS_BG = new Color(209, 250, 229);       // #D1FAE5

    // Font Tipografi Segoe UI
    public static final Font FONT_BIG_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 12);

    /**
     * Membuat Tombol Bergaya Modern dengan Sudut Melengkung dan Efek Rollover
     */
    public static JButton createPurpleButton(String text, int width, int height) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(PURPLE_DARK);
                } else if (getModel().isRollover()) {
                    g2.setColor(PURPLE_HOVER);
                } else {
                    g2.setColor(PURPLE_PRIMARY);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(FONT_BUTTON);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(width, height));
        return btn;
    }

    /**
     * Membuat Tombol Sekunder / Ghost Button (Abu-abu / Transparan)
     */
    public static JButton createSecondaryButton(String text, int width, int height) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(new Color(226, 232, 240));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(241, 245, 249));
                } else {
                    g2.setColor(Color.WHITE);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(BORDER_LIGHT);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(TEXT_DARK);
        btn.setFont(FONT_BUTTON);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(width, height));
        return btn;
    }

    /**
     * Menata JTextField dengan padding dalam, tinggi ideal, dan efek border aktif saat fokus
     */
    public static void styleTextField(JTextField tf) {
        tf.setFont(FONT_INPUT);
        tf.setForeground(TEXT_DARK);
        tf.setCaretColor(PURPLE_PRIMARY);
        tf.setBackground(Color.WHITE);
        tf.setPreferredSize(new Dimension(tf.getPreferredSize().width, 38));

        CompoundBorder normalBorder = new CompoundBorder(
                new LineBorder(BORDER_LIGHT, 1, true),
                new EmptyBorder(6, 12, 6, 12)
        );
        CompoundBorder focusedBorder = new CompoundBorder(
                new LineBorder(BORDER_FOCUS, 1, true),
                new EmptyBorder(6, 12, 6, 12)
        );

        tf.setBorder(normalBorder);
        tf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                tf.setBorder(focusedBorder);
            }
            @Override
            public void focusLost(FocusEvent e) {
                tf.setBorder(normalBorder);
            }
        });
    }

    /**
     * Menata JComboBox dengan gaya flat modern dan renderer kustom
     */
    public static void styleComboBox(JComboBox<?> cmb) {
        cmb.setFont(FONT_INPUT);
        cmb.setBackground(Color.WHITE);
        cmb.setForeground(TEXT_DARK);
        cmb.setPreferredSize(new Dimension(cmb.getPreferredSize().width, 38));
        cmb.setBorder(new CompoundBorder(
                new LineBorder(BORDER_LIGHT, 1, true),
                new EmptyBorder(2, 6, 2, 6)
        ));

        cmb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setBorder(new EmptyBorder(8, 12, 8, 12));
                lbl.setFont(FONT_INPUT);
                if (isSelected) {
                    lbl.setBackground(PURPLE_LIGHT);
                    lbl.setForeground(PURPLE_PRIMARY);
                } else {
                    lbl.setBackground(Color.WHITE);
                    lbl.setForeground(TEXT_DARK);
                }
                return lbl;
            }
        });
    }

    /**
     * Menata JRadioButton dengan icon modern custom
     */
    public static void styleRadioButton(JRadioButton rb) {
        rb.setOpaque(false);
        rb.setFont(FONT_REGULAR);
        rb.setForeground(TEXT_DARK);
        rb.setFocusPainted(false);
        rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rb.setIcon(new ModernRadioIcon(false));
        rb.setSelectedIcon(new ModernRadioIcon(true));
    }

    /**
     * Menata JCheckBox dengan icon modern custom
     */
    public static void styleCheckBox(JCheckBox cb) {
        cb.setOpaque(false);
        cb.setFont(FONT_REGULAR);
        cb.setForeground(TEXT_DARK);
        cb.setFocusPainted(false);
        cb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cb.setIcon(new ModernCheckIcon(false));
        cb.setSelectedIcon(new ModernCheckIcon(true));
    }

    // ==========================================
    // STEPPER CUSTOM ICONS (Anti-Glitch / Tofu)
    // ==========================================

    public static Icon createCheckmarkIcon(int size) {
        return new Icon() {
            @Override
            public int getIconWidth() { return size; }
            @Override
            public int getIconHeight() { return size; }
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Lingkaran hijau lembut
                g2.setColor(SUCCESS_GREEN);
                g2.fillOval(x, y, size, size);
                // Checkmark putih
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int p1x = x + (int)(size * 0.25);
                int p1y = y + (int)(size * 0.52);
                int p2x = x + (int)(size * 0.44);
                int p2y = y + (int)(size * 0.72);
                int p3x = x + (int)(size * 0.75);
                int p3y = y + (int)(size * 0.30);
                g2.drawLine(p1x, p1y, p2x, p2y);
                g2.drawLine(p2x, p2y, p3x, p3y);
                g2.dispose();
            }
        };
    }

    public static Icon createActiveStepDotIcon(int size) {
        return new Icon() {
            @Override
            public int getIconWidth() { return size; }
            @Override
            public int getIconHeight() { return size; }
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PURPLE_PRIMARY);
                g2.fillOval(x + 2, y + 2, size - 4, size - 4);
                g2.dispose();
            }
        };
    }

    public static Icon createPendingStepDotIcon(int size) {
        return new Icon() {
            @Override
            public int getIconWidth() { return size; }
            @Override
            public int getIconHeight() { return size; }
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillOval(x + 2, y + 2, size - 4, size - 4);
                g2.setColor(new Color(203, 213, 225));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawOval(x + 2, y + 2, size - 5, size - 5);
                g2.dispose();
            }
        };
    }

    // ==========================================
    // CUSTOM ICON CLASSES
    // ==========================================

    public static class ModernRadioIcon implements Icon {
        private final boolean selected;

        public ModernRadioIcon(boolean selected) {
            this.selected = selected;
        }

        @Override
        public int getIconWidth() {
            return 18;
        }

        @Override
        public int getIconHeight() {
            return 18;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (selected) {
                g2.setColor(PURPLE_PRIMARY);
                g2.fillOval(x, y, 18, 18);
                g2.setColor(Color.WHITE);
                g2.fillOval(x + 5, y + 5, 8, 8);
            } else {
                g2.setColor(Color.WHITE);
                g2.fillOval(x, y, 18, 18);
                g2.setColor(new Color(203, 213, 225)); // #CBD5E1
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawOval(x, y, 17, 17);
            }
            g2.dispose();
        }
    }

    public static class ModernCheckIcon implements Icon {
        private final boolean selected;

        public ModernCheckIcon(boolean selected) {
            this.selected = selected;
        }

        @Override
        public int getIconWidth() {
            return 18;
        }

        @Override
        public int getIconHeight() {
            return 18;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (selected) {
                g2.setColor(PURPLE_PRIMARY);
                g2.fillRoundRect(x, y, 18, 18, 5, 5);
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(x + 4, y + 9, x + 7, y + 13);
                g2.drawLine(x + 7, y + 13, x + 14, y + 5);
            } else {
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(x, y, 18, 18, 5, 5);
                g2.setColor(new Color(203, 213, 225));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(x, y, 17, 17, 5, 5);
            }
            g2.dispose();
        }
    }
}
