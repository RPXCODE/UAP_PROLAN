package org.example;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SupplierPanel extends JPanel {
    private DefaultTableModel tableModel;

    public SupplierPanel(boolean isAdmin) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 30, 30, 30));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.add(StyleUtils.createLabel("DATA SUPPLIER", StyleUtils.FONT_HEADER), BorderLayout.WEST);

        JButton btnBack = StyleUtils.createButton("KEMBALI", Color.BLACK);
        btnBack.addActionListener(e -> SwingUtilities.getWindowAncestor(this).dispose());
        header.add(btnBack, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        String[] cols = {"NAMA SUPPLIER", "KONTAK", "ALAMAT"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(tableModel);
        StyleUtils.styleTable(table);

        if (isAdmin) {
            JPanel leftPanel = new JPanel(new BorderLayout());
            leftPanel.setBackground(Color.WHITE);
            leftPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(15,15,15,15)));
            leftPanel.setPreferredSize(new Dimension(500, 0));

            // Form
            JPanel formPanel = new JPanel(new GridLayout(0, 1, 0, 10));
            formPanel.setBackground(Color.WHITE);

            JTextField txtNama = StyleUtils.createTextField();
            JTextField txtKontak = StyleUtils.createTextField();
            JTextField txtAlamat = StyleUtils.createTextField();

            formPanel.add(StyleUtils.createLabel("NAMA TOKO/PT:", StyleUtils.FONT_LABEL));
            formPanel.add(txtNama);
            formPanel.add(StyleUtils.createLabel("KONTAK (HP):", StyleUtils.FONT_LABEL));
            formPanel.add(txtKontak);
            formPanel.add(StyleUtils.createLabel("ALAMAT:", StyleUtils.FONT_LABEL));
            formPanel.add(txtAlamat);

            leftPanel.add(formPanel, BorderLayout.NORTH);

            // Tombol
            JPanel btnPanel = new JPanel(new GridLayout(3, 1, 0, 10));
            btnPanel.setBackground(Color.WHITE);

            JButton btnAdd = StyleUtils.createButton("TAMBAH DATA", Color.BLUE);
            JButton btnEdit = StyleUtils.createButton("EDIT DATA", Color.ORANGE);
            JButton btnDel = StyleUtils.createButton("HAPUS DATA", Color.RED);

            btnPanel.add(btnAdd);
            btnPanel.add(btnEdit);
            btnPanel.add(btnDel);

            JPanel bottomWrapper = new JPanel(new BorderLayout());
            bottomWrapper.setBackground(Color.WHITE);
            bottomWrapper.add(btnPanel, BorderLayout.SOUTH);

            leftPanel.add(bottomWrapper, BorderLayout.CENTER);
            add(leftPanel, BorderLayout.WEST);

            // --- ACTIONS ---

            // 1. TOMBOL TAMBAH
            btnAdd.addActionListener(e -> {
                if(txtNama.getText().isEmpty() || txtKontak.getText().isEmpty() || txtAlamat.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Isi dulu datanya!");
                    return;
                }
                ArrayList<String[]> list = DataManager.loadSupplier();
                list.add(new String[]{txtNama.getText(), txtKontak.getText(), txtAlamat.getText()});
                DataManager.saveSupplier(list);
                MaterialMasterApp.logActivity("SUPPLIER", "Tambah: " + txtNama.getText());
                refreshData();
                txtNama.setText(""); txtKontak.setText(""); txtAlamat.setText("");
            });

            // 2. TOMBOL HAPUS (INI YANG TADI ERROR)
            btnDel.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    // AMBIL NAMA DARI TABEL DULU SEBELUM DIHAPUS
                    String namaDiTabel = (String) tableModel.getValueAt(row, 0);

                    if (JOptionPane.showConfirmDialog(this, "Hapus " + namaDiTabel + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        ArrayList<String[]> list = DataManager.loadSupplier();

                        // Hapus dari list berdasarkan nama
                        list.removeIf(s -> s[0].equals(namaDiTabel));

                        DataManager.saveSupplier(list);
                        MaterialMasterApp.logActivity("SUPPLIER", "Hapus: " + namaDiTabel);
                        refreshData();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Pilih baris yang mau dihapus!");
                }
            });

            // 3. TOMBOL EDIT
            btnEdit.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String oldNama = (String) tableModel.getValueAt(row, 0);
                    JTextField tNama = new JTextField(oldNama);
                    JTextField tKontak = new JTextField((String) tableModel.getValueAt(row, 1));
                    JTextField tAlamat = new JTextField((String) tableModel.getValueAt(row, 2));
                    Object[] msg = {"Nama:", tNama, "Kontak:", tKontak, "Alamat:", tAlamat};

                    if (JOptionPane.showConfirmDialog(null, msg, "Edit Supplier", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        ArrayList<String[]> list = DataManager.loadSupplier();
                        for(String[] s : list) {
                            if(s[0].equals(oldNama)) {
                                s[0] = tNama.getText(); s[1] = tKontak.getText(); s[2] = tAlamat.getText();
                                break;
                            }
                        }
                        DataManager.saveSupplier(list);
                        MaterialMasterApp.logActivity("SUPPLIER", "Edit: " + oldNama);
                        refreshData();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Pilih baris yang mau diedit!");
                }
            });
        }

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBorder(new EmptyBorder(0, 20, 0, 0));
        tableContainer.add(new JScrollPane(table));
        add(tableContainer, BorderLayout.CENTER);

        refreshData();
    }
    public void refreshData() {
        tableModel.setRowCount(0);
        for (String[] s : DataManager.loadSupplier()) tableModel.addRow(s);
    }
}