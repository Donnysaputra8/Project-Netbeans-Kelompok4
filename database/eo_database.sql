-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 23 Jun 2025 pada 13.19
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eo_database`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `paket_event`
--

CREATE TABLE `paket_event` (
  `id_paket` varchar(11) NOT NULL,
  `nama_paket` varchar(100) NOT NULL,
  `deskripsi` text DEFAULT NULL,
  `harga` decimal(15,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `paket_event`
--

INSERT INTO `paket_event` (`id_paket`, `nama_paket`, `deskripsi`, `harga`) VALUES
('PKT001', 'Paket Silver', 'Cocok untuk acara keluarga kecil dengan dekorasi standar.', 1500000),
('PKT002', 'Paket Gold', 'Termasuk MC, dekorasi, dan sound system sederhana.', 3500000),
('PKT003', 'Paket Platinum', 'Lengkap dengan catering dan dokumentasi profesional.', 7000000),
('PKT004', 'Paket Wedding Basic', 'Untuk resepsi pernikahan kecil dengan 100 tamu.', 9000000),
('PKT005', 'Paket Wedding Medium', 'Lengkap: venue, dekorasi, MC, catering, hiburan.', 25000000),
('PKT006', 'Paket Seminar Hemat', 'Untuk seminar skala kecil, include proyektor dan sound.', 2000000),
('PKT007', 'Paket Seminar Lengkap', 'Termasuk konsumsi, dokumentasi, sertifikat, dan MC.', 4500000),
('PKT008', 'Paket Ulang Tahun A', 'Dekorasi balon, kue ulang tahun, MC anak-anak.', 1750000),
('PKT009', 'Paket Ulang Tahun B', 'Lengkap dengan badut, game show, dan snack anak-anak.', 2500000),
('PKT010', 'Paket Gathering Karyawan', 'Untuk acara santai perusahaan (outbound + konsumsi).', 8000000),
('PKT011', 'Paket Konser Mini', 'Sound system, lighting, panggung kecil, crew.', 12000000),
('PKT012', 'Paket Konser Premium', 'Full stage, lighting, LED, keamanan, dan promosi.', 30000000),
('PKT013', 'Paket Pameran Standar', 'Booth kecil, meja, kursi, backdrop.', 5000000),
('PKT014', 'Paket Pameran Premium', 'Booth besar, desain custom, display produk, tenaga sales.', 12000000),
('PKT015', 'Paket Lamaran Hemat', 'Dekorasi rumah, rias, MC, dan konsumsi ringan.', 4500000),
('PKT016', 'Paket Lamaran Eksklusif', 'Rias pengantin, backdrop lamaran, MC, dokumentasi, catering.', 9500000),
('PKT017', 'Paket Reuni Sekolah', 'Termasuk rundown acara, MC, dekorasi, dan foto booth.', 7000000),
('PKT018', 'Paket Festival Budaya', 'Acara outdoor dengan panggung budaya, MC, promosi, dan tenant UMKM.', 20000000),
('PKT019', 'Paket Custom', 'Paket disesuaikan dengan kebutuhan klien, fleksibel dari segi layanan dan harga.', 0);

-- --------------------------------------------------------

--
-- Struktur dari tabel `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id_pel` varchar(11) NOT NULL,
  `nm_pel` varchar(100) NOT NULL,
  `alamat` text DEFAULT NULL,
  `no_tel` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `perusahaan` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `pelanggan`
--

INSERT INTO `pelanggan` (`id_pel`, `nm_pel`, `alamat`, `no_tel`, `email`, `perusahaan`) VALUES
('PLG001', 'Andi Saputra', 'Jl. Merpati No. 10, Bandung', '081234567890', 'andi.saputra@email.com', 'PT Harmoni Event'),
('PLG002', 'Rina Marlina', 'Jl. Anggrek Raya No. 5, Jakarta', '081223344556', 'rina.marlina@gmail.com', 'CV Indah Wedding'),
('PLG003', 'Ahmad Fauzi', 'Komplek Bumi Asri, Yogyakarta', '085612345678', 'ahmad.fauzi@yahoo.com', '-'),
('PLG004', 'Siska Widya', 'Jl. Pandan Wangi No. 3, Surabaya', '082134567891', 'siska.widya@email.com', 'PT Kreativa Nusantara'),
('PLG005', 'Dedi Firmansyah', 'Jl. Melati 8, Tangerang Selatan', '081778899000', 'dedi.firmansyah@mail.com', 'Dedi Catering'),
('PLG006', 'Lina Marlina', 'Jl. Teratai 2, Bekasi', '089901122334', 'lina.marlina@event.id', 'Wedding by Lina'),
('PLG007', 'Bambang Sutrisno', 'Jl. Semangka No. 7, Semarang', '082211334455', 'bambang.sutrisno@gmail.com', '-'),
('PLG008', 'Sarah Amelia', 'Jl. Kemuning 14, Depok', '085800112233', 'sarah.amelia@company.com', 'PT Mandiri Acara'),
('PLG009', 'Tono Prasetyo', 'Jl. Cempaka Baru, Malang', '081266778899', 'tono.prasetyo@mail.com', 'Prasetyo Organizer'),
('PLG010', 'Melani Sari', 'Jl. Kenanga Selatan, Bandung', '081399112244', 'melani.sari@gmail.com', '-');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pembayaran_event`
--

CREATE TABLE `pembayaran_event` (
  `id_pembayaran` varchar(11) NOT NULL,
  `id_pemesanan` varchar(11) DEFAULT NULL,
  `tanggal_bayar` date DEFAULT NULL,
  `jenis_pembayaran` varchar(20) DEFAULT NULL,
  `jumlah_bayar` decimal(15,2) DEFAULT NULL,
  `metode_pembayaran` varchar(50) DEFAULT NULL,
  `bukti_pembayaran` varchar(255) DEFAULT NULL,
  `catatan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `pembayaran_event`
--

INSERT INTO `pembayaran_event` (`id_pembayaran`, `id_pemesanan`, `tanggal_bayar`, `jenis_pembayaran`, `jumlah_bayar`, `metode_pembayaran`, `bukti_pembayaran`, `catatan`) VALUES
('BYR001', 'PSN001', '2025-06-23', 'Transfer', 11750000.00, 'Lunas', 'C:\\Users\\Gilang Herlambang\\OneDrive\\Pictures\\Bukti-Transfer-BCA1.png', '-');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pemesanan_event`
--

CREATE TABLE `pemesanan_event` (
  `id_pemesanan` varchar(11) NOT NULL,
  `id_pelanggan` varchar(11) DEFAULT NULL,
  `id_paket` varchar(11) DEFAULT NULL,
  `id_venue` varchar(11) DEFAULT NULL,
  `tanggal_acara` date DEFAULT NULL,
  `jumlah_tamu` int(11) DEFAULT NULL,
  `request_tambahan` text DEFAULT NULL,
  `status_pemesanan` varchar(50) DEFAULT 'Menunggu'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `pemesanan_event`
--

INSERT INTO `pemesanan_event` (`id_pemesanan`, `id_pelanggan`, `id_paket`, `id_venue`, `tanggal_acara`, `jumlah_tamu`, `request_tambahan`, `status_pemesanan`) VALUES
('PSN001', 'PLG001', 'PKT008', 'VEN002', '2025-06-23', 150, 'badut ulang tahun', 'Disetujui'),
('PSN002', 'PLG002', 'PKT006', 'VEN005', '2025-06-23', 150, '-', 'Disetujui'),
('PSN003', 'PLG003', 'PKT004', 'VEN009', '2025-09-10', 500, '-', 'Disetujui');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user_admin`
--

CREATE TABLE `user_admin` (
  `id_admin` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `user_admin`
--

INSERT INTO `user_admin` (`id_admin`, `username`, `password`, `nama`) VALUES
(1, 'dane', '1610', 'Muhammad Zidane Ramadhan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `venue`
--

CREATE TABLE `venue` (
  `id_venue` varchar(11) NOT NULL,
  `nama_venue` varchar(100) NOT NULL,
  `alamat_venue` text DEFAULT NULL,
  `kapasitas` int(11) DEFAULT NULL,
  `harga_sewa` decimal(15,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `venue`
--

INSERT INTO `venue` (`id_venue`, `nama_venue`, `alamat_venue`, `kapasitas`, `harga_sewa`) VALUES
('VEN001', 'Graha Permata', 'Jl. Melati No. 10, Jakarta Selatan', 500, 15000000.00),
('VEN002', 'Gedung Harmoni', 'Jl. Kenanga No. 5, Bandung', 300, 10000000.00),
('VEN003', 'Hall Serbaguna Sakura', 'Jl. Sakura Raya No. 21, Bekasi', 200, 7500000.00),
('VEN004', 'Grand Ballroom Arjuna', 'Jl. Arjuna No. 88, Yogyakarta', 1000, 25000000.00),
('VEN005', 'Ruang Pertemuan Cendana', 'Jl. Cendana No. 12, Surabaya', 150, 5000000.00),
('VEN006', 'Outdoor Garden Venue', 'Jl. Pahlawan No. 3, Bogor', 400, 12000000.00),
('VEN007', 'The Sky Rooftop', 'Jl. Bunga Kamboja No. 17, Medan', 250, 9500000.00),
('VEN008', 'Sasana Budaya', 'Jl. Raya Sukabumi No. 33, Sukabumi', 800, 18000000.00),
('VEN009', 'Gedung Mutiara', 'Jl. Ahmad Yani No. 2, Semarang', 600, 17000000.00),
('VEN010', 'Venue Keluarga Asri', 'Jl. Cemara Indah, Malang', 100, 3500000.00);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `paket_event`
--
ALTER TABLE `paket_event`
  ADD PRIMARY KEY (`id_paket`);

--
-- Indeks untuk tabel `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`id_pel`);

--
-- Indeks untuk tabel `pembayaran_event`
--
ALTER TABLE `pembayaran_event`
  ADD PRIMARY KEY (`id_pembayaran`),
  ADD KEY `id_pemesanan` (`id_pemesanan`);

--
-- Indeks untuk tabel `pemesanan_event`
--
ALTER TABLE `pemesanan_event`
  ADD PRIMARY KEY (`id_pemesanan`),
  ADD KEY `id_pelanggan` (`id_pelanggan`),
  ADD KEY `id_paket` (`id_paket`),
  ADD KEY `id_venue` (`id_venue`);

--
-- Indeks untuk tabel `user_admin`
--
ALTER TABLE `user_admin`
  ADD PRIMARY KEY (`id_admin`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indeks untuk tabel `venue`
--
ALTER TABLE `venue`
  ADD PRIMARY KEY (`id_venue`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `user_admin`
--
ALTER TABLE `user_admin`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
