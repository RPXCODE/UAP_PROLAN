package org.example;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class StyleUtils {
    public static final Font FONT_HEADER = new Font("Arial", Font.BOLD, 28);
    public static final Font FONT_LABEL = new Font("Arial", Font.BOLD, 12);
    public static final Font FONT_TEXT = new Font("Arial", Font.PLAIN, 14);

    public static String formatNumber(double number) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        nf.setMaximumFractionDigits(0);
        return nf.format(number);
    }

    public static double parseNumber(String str) {
        try { return Double.parseDouble(str.replace(",", "")); } catch (Exception e) { return 0; }
    }

    public static JLabel createLabel(String text, Font font) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        lbl.setForeground(Color.BLACK);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    public static JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorder(new CompoundBorder(new LineBorder(bg.darker(), 1), new EmptyBorder(10, 20, 10, 20)));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        return btn;
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(35);
        table.setFont(FONT_TEXT);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(230, 240, 255));
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(Color.decode("#2C3E50"));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(String.class, centerRenderer);
    }

    public static JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(FONT_TEXT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY, 1, true),
                new EmptyBorder(5, 10, 5, 10)));
        return field;
    }

    public static void stylePasswordField(JPasswordField field) {
        field.setFont(FONT_TEXT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY, 1, true),
                new EmptyBorder(5, 10, 5, 10)));
    }
}