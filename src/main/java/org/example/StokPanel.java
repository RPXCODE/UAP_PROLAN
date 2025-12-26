package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StokPanel extends JPanel {
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtSearch;

    public StokPanel(boolean isAdmin, boolean isLowStockMode) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 30, 30, 30));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        String headerText = isLowStockMode ? "PERINGATAN: STOK BARANG MENIPIS (< 5)" : "DATA STOK BARANG";
        JLabel lblHeader = StyleUtils.createLabel(headerText, StyleUtils.FONT_HEADER);
        if(isLowStockMode) lblHeader.setForeground(Color.RED);

        JButton btnBack = StyleUtils.createButton("KEMBALI", Color.BLACK);
        btnBack.addActionListener(e -> SwingUtilities.getWindowAncestor(this).dispose());
        header.add(lblHeader, BorderLayout.WEST);
        header.add(btnBack, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        JPanel tools = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tools.setBackground(Color.WHITE);
        txtSearch = StyleUtils.createTextField();
        txtSearch.setPreferredSize(new Dimension(250, 40));

        tools.add(StyleUtils.createLabel("CARI: ", StyleUtils.FONT_LABEL));
        tools.add(txtSearch);

        JButton btnEdit = null;
        JButton btnDel = null;

        if (isAdmin) {
            btnEdit = StyleUtils.createButton("EDIT DATA", Color.BLUE);
            btnDel = StyleUtils.createButton("HAPUS DATA", Color.RED);
            tools.add(Box.createHorizontalStrut(10));
            tools.add(btnEdit);
            tools.add(Box.createHorizontalStrut(10));
            tools.add(btnDel);
        }
        add(tools, BorderLayout.SOUTH);

        // Tabel PATEN (Gak bisa diedit langsung)
        String[] cols = {"NAMA BARANG", "KATEGORI", "STOK", "SATUAN", "HARGA (RP)"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(tableModel);
        StyleUtils.styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);

        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                String text = txtSearch.getText();
                sorter.setRowFilter(text.length() == 0 ? null : RowFilter.regexFilter("(?i)" + text));
            }
        });

        ArrayList<Barang> dataList = DataManager.loadBarang();
        for (Barang b : dataList)
            tableModel.addRow(new Object[]{b.nama, b.kategori, StyleUtils.formatNumber(b.stok), b.satuan, StyleUtils.formatNumber(b.harga)});

        if (isLowStockMode) {
            sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    double val = StyleUtils.parseNumber((String) entry.getValue(2));
                    return val < 5;
                }
            });
            txtSearch.setText("FILTER: LOW STOCK");
            txtSearch.setEnabled(false);
        }

        if (isAdmin && btnEdit != null) {
            btnEdit.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    int modelRow = table.convertRowIndexToModel(row);
                    String oldNama = (String) tableModel.getValueAt(modelRow, 0);

                    JTextField tNama = new JTextField(oldNama);
                    JTextField tKat = new JTextField((String) tableModel.getValueAt(modelRow, 1));
                    JTextField tStok = new JTextField(String.valueOf((int)StyleUtils.parseNumber((String)tableModel.getValueAt(modelRow, 2))));
                    JTextField tSat = new JTextField((String) tableModel.getValueAt(modelRow, 3));
                    JTextField tHarga = new JTextField(String.valueOf((int)StyleUtils.parseNumber((String)tableModel.getValueAt(modelRow, 4))));

                    Object[] message = {"Nama:", tNama, "Kategori:", tKat, "Stok:", tStok, "Satuan:", tSat, "Harga:", tHarga};
                    if (JOptionPane.showConfirmDialog(null, message, "Edit Barang", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        try {
                            for(Barang b : dataList) {
                                if(b.nama.equals(oldNama)) {
                                    b.nama = tNama.getText(); b.kategori = tKat.getText();
                                    b.stok = Integer.parseInt(tStok.getText()); b.satuan = tSat.getText();
                                    b.harga = Double.parseDouble(tHarga.getText()); break;
                                }
                            }
                            DataManager.saveBarang(dataList);
                            MaterialMasterApp.logActivity("EDIT", "Edit Barang: " + oldNama);
                            tableModel.setValueAt(tNama.getText(), modelRow, 0);
                            tableModel.setValueAt(tKat.getText(), modelRow, 1);
                            tableModel.setValueAt(StyleUtils.formatNumber(Integer.parseInt(tStok.getText())), modelRow, 2);
                            tableModel.setValueAt(tSat.getText(), modelRow, 3);
                            tableModel.setValueAt(StyleUtils.formatNumber(Double.parseDouble(tHarga.getText())), modelRow, 4);
                            JOptionPane.showMessageDialog(this, "Data Berhasil Diupdate!");
                        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Input Error!"); }
                    }
                } else JOptionPane.showMessageDialog(this, "Pilih baris yang mau diedit!");
            });
        }

        if (isAdmin && btnDel != null) {
            btnDel.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    if (JOptionPane.showConfirmDialog(this, "Hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        int modelRow = table.convertRowIndexToModel(row);
                        dataList.removeIf(b -> b.nama.equals(tableModel.getValueAt(modelRow, 0)));
                        DataManager.saveBarang(dataList);
                        MaterialMasterApp.logActivity("HAPUS", "Hapus Barang: " + tableModel.getValueAt(modelRow, 0));
                        tableModel.removeRow(modelRow);
                    }
                } else JOptionPane.showMessageDialog(this, "Pilih baris tabel dulu!");
            });
        }
    }
}