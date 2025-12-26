package org.example;

import java.io.*;
import java.util.ArrayList;

public class DataManager {
    private static final String FILE_BARANG = "data_barang.csv";
    private static final String FILE_SUPPLIER = "data_supplier.csv";

    public static ArrayList<Barang> loadBarang() {
        ArrayList<Barang> list = new ArrayList<>();
        if (!new File(FILE_BARANG).exists()) return list;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_BARANG))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) list.add(new Barang(data[0], data[1], Integer.parseInt(data[2]), data[3], Double.parseDouble(data[4])));
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    public static void saveBarang(ArrayList<Barang> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_BARANG))) {
            for (Barang b : list) {
                writer.write(b.nama + "," + b.kategori + "," + b.stok + "," + b.satuan + "," + b.harga);
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static ArrayList<String[]> loadSupplier() {
        ArrayList<String[]> list = new ArrayList<>();
        if (!new File(FILE_SUPPLIER).exists()) return list;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_SUPPLIER))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) list.add(data);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    public static void saveSupplier(ArrayList<String[]> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_SUPPLIER))) {
            for (String[] s : list) {
                writer.write(s[0] + "," + s[1] + "," + s[2]);
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}