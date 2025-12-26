# 🏗️ Sistem Informasi Manajemen Gudang (Inventory System)

> **Catatan:** Proyek ini dikembangkan sebagai bagian dari Tugas Besar/UAP Pemrograman Berorientasi Objek (PBO).

## 📖 Deskripsi Proyek
Aplikasi ini adalah perangkat lunak berbasis **Java** yang dirancang untuk mengelola inventaris pada sebuah **Gudang Bahan Bangunan**. Tujuan utama aplikasi ini adalah untuk mempermudah pencatatan stok barang, memantau transaksi barang masuk dan keluar, serta meminimalisir kesalahan pencatatan manual.

Aplikasi ini menggunakan antarmuka grafis (GUI) berbasis **Java Swing** untuk memudahkan interaksi pengguna.

## ✨ Fitur Utama
Berikut adalah fitur-fitur unggulan dalam aplikasi:

* **Dashboard Interaktif**: Menampilkan ringkasan total barang dan stok yang tersedia.
* **Manajemen Data Barang (CRUD)**:
    * Tambah barang baru (Nama, Kategori, Harga, Stok Awal).
    * Edit informasi barang.
    * Hapus barang dari sistem.
* **Transaksi Stok**:
    * **Barang Masuk (Restock)**: Menambah jumlah stok barang yang ada.
    * **Barang Keluar (Penjualan)**: Mengurangi stok secara otomatis saat terjadi transaksi.
* **Pencarian & Filter**: Memudahkan pencarian barang berdasarkan nama atau ID.

## 🛠️ Teknologi yang Digunakan
* **Bahasa Pemrograman**: Java (JDK 17 atau lebih baru)
* **GUI Framework**: Java Swing (javax.swing)
* **IDE**: IntelliJ IDEA
* **Konsep**: OOP (Encapsulation, Inheritance, Polymorphism), MVC Pattern (Model-View-Controller).

## 📂 Struktur Folder
Struktur paket (package) dalam proyek ini disusun sebagai berikut.

```text
src/
├── main/
│   ├── Main.java           # Entry point aplikasi
│
├── model/                  # Representasi data (Objek Barang, Transaksi)
│   └── Barang.java
│
├── view/                   # Antarmuka Pengguna (GUI Forms)
│   ├── DashboardFrame.java
│   ├── LoginFrame.java
│   └── InputBarangFrame.java
│
├── controller/             # Logika bisnis dan penghubung View-Model
│   └── BarangController.java
│
└── util/                   # Kelas pembantu (Koneksi Database/Helper)