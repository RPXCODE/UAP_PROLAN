package org.example;

public class Barang {
    public String nama, kategori, satuan;
    public int stok;
    public double harga;

    public Barang(String n, String k, int s, String st, double h) {
        this.nama = n;
        this.kategori = k;
        this.stok = s;
        this.satuan = st;
        this.harga = h;
    }
}