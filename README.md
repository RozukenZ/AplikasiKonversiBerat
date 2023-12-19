# Kalkulator Konversi Berat

Aplikasi Kalkulator Konversi Berat adalah sebuah program sederhana yang memungkinkan pengguna untuk mengonversi berat dari satu unit ke unit lainnya. Program ini ditulis dalam bahasa pemrograman Java dan menggunakan JavaFX untuk antarmuka pengguna.

## Petunjuk Penggunaan

1. Jalankan program dengan menjalankan kelas `Main.java`.
2. Isi berat yang ingin Anda konversi dalam TextField yang disediakan.
3. Pilih unit berat awal dari ComboBox `inputUnit`.
4. Pilih unit berat yang diinginkan untuk konversi dari ComboBox `outputUnit`.
5. Klik tombol "Konversi" untuk melihat hasil konversi.
6. Hasil konversi akan ditampilkan di bawah tombol konversi.

## Unit Berat yang Didukung

Program mendukung konversi berat untuk unit-unit berikut:
- Kilogram (KG)
- Gram (G)
- Ton
- Kuintal

## Formula Konversi

1. **Konversi dari Unit Input ke Gram:**
    - KG ke Gram: `inputWeight * 1000.0`
    - Ton ke Gram: `inputWeight * 1000000.0`
    - Kuintal ke Gram: `inputWeight * 100000.0`
    - Gram (input) ke Gram: `inputWeight`

2. **Konversi dari Gram ke Unit Output:**
    - Gram ke KG: `weightInGrams / 1000.0`
    - Gram ke Ton: `weightInGrams / 1000000.0`
    - Gram ke Kuintal: `weightInGrams / 100000.0`
    - Gram ke Gram (output): `weightInGrams`

## Penanganan Kesalahan

Jika pengguna memasukkan berat yang tidak valid (bukan angka), program akan menampilkan pesan kesalahan.

## Contoh Penggunaan

Misalnya, jika pengguna memasukkan berat 5 KG dan memilih unit output "Ton", hasil konversi akan menunjukkan berat tersebut dalam ton.

## Lisensi

Program ini dilisensikan di bawah [lisensi MIT](LICENSE).
