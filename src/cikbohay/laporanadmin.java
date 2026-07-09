/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package cikbohay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author macbookairm12020
 */
public class laporanadmin extends javax.swing.JPanel {

    /**
     * Creates new form laporanadmin
     */
    public laporanadmin() {
        initComponents();
        loadDataDashboard();
        loadMenuTerlaris();
        loadTransaksiTerakhir();
    }

    public void loadTransaksiTerakhir() {
    javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // Membersihkan isi tabel sebelum dimuat ulang

    try {
        java.sql.Connection conn = (java.sql.Connection) koneksi.konek();
        
        // Query untuk mengambil 10 transaksi paling baru
        String sql = "SELECT id_nota, waktu_transaksi, total_pesanan " +
                     "FROM nota " +
                     "ORDER BY waktu_transaksi DESC " +
                     "LIMIT 10";
                     
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        java.sql.ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String idNota = rs.getString("id_nota");
            String waktu = rs.getString("waktu_transaksi");
            double total = rs.getDouble("total_pesanan");
            
            // Format nominal menjadi Rupiah
            String formatTotal = String.format(new java.util.Locale("id", "ID"), "Rp %,.0f", total);
            
            // Status di-hardcode "Selesai" karena data sudah masuk nota
            String status = "Selesai";
            
            // Masukkan data ke baris tabel (jTable1)
            model.addRow(new Object[]{idNota, waktu, formatTotal, status});
        }
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error memuat riwayat transaksi: " + e.getMessage());
    }
}
    
    public void loadMenuTerlaris() {
    // 1. Atur ulang model tabel dan berikan nama kolom yang rapi
    javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
    model.addColumn("No");
    model.addColumn("Nama Menu");
    model.addColumn("Terjual (Porsi)");
    model.addColumn("Total Pendapatan");
    tblMenuTerlaris.setModel(model);

    try {
        // Panggil koneksi (Sesuaikan dengan file koneksi.java milikmu)
        java.sql.Connection conn = (java.sql.Connection) koneksi.konek();
        
        // 2. Query JOIN untuk mengambil 5 menu dengan jumlah terjual paling tinggi
        String sql = "SELECT m.nama_menu, SUM(p.jumlah) AS total_terjual, SUM(p.total) AS total_pendapatan " +
                     "FROM pesanan p " +
                     "JOIN menu m ON p.id_menu = m.id_menu " +
                     "GROUP BY m.id_menu, m.nama_menu " +
                     "ORDER BY total_terjual DESC " +
                     "LIMIT 3"; 
        
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        java.sql.ResultSet rs = ps.executeQuery();

        int no = 1;
        while (rs.next()) {
            String namaMenu = rs.getString("nama_menu");
            int terjual = rs.getInt("total_terjual");
            double pendapatan = rs.getDouble("total_pendapatan");
            
            // Format rupiah biar tampilan di tabel rapi
            String formatPendapatan = String.format(new java.util.Locale("id", "ID"), "Rp %,.0f", pendapatan);
            
            // 3. Masukkan data ke dalam baris tabel
            model.addRow(new Object[]{no++, namaMenu, terjual, formatPendapatan});
        }
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error memuat menu terlaris: " + e.getMessage());
    }
}
    
    public void loadDataDashboard() {
        try {
            // 1. Panggil koneksi dari class koneksi.java milikmu
            // (Ganti "configDB()" dengan nama method yang ada di dalam file koneksi.java kamu)
            Connection conn = (Connection) koneksi.konek();

           // =========================================================
// A. BLOK KODE UNTUK TOTAL PENJUALAN
// =========================================================
            String sql = "SELECT SUM(total_pesanan) FROM nota";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Ambil datanya sebagai angka (Double), bukan String
                double total = rs.getDouble(1);

                // Pastikan hasil SUM tidak null (misal jika tabel nota masih kosong)
                if (rs.wasNull()) {
                    tTotalPenjualan.setText("Rp 0");
                } else {
                    // Format otomatis menjadi ada separator ribuannya (contoh: Rp 150.000)
                    String formatTotal = String.format(new java.util.Locale("id", "ID"), "Rp %,.0f", total);
                    tTotalPenjualan.setText(formatTotal);
                }
            }
// =========================================================
            // B. BLOK KODE UNTUK TOTAL TRANSAKSI
            // =========================================================
            String sqlTransaksi = "SELECT COUNT(id_nota) FROM nota";
            java.sql.PreparedStatement psTransaksi = conn.prepareStatement(sqlTransaksi);
            java.sql.ResultSet rsTransaksi = psTransaksi.executeQuery();

            if (rsTransaksi.next()) {
                String jumlahTransaksi = rsTransaksi.getString(1);
                if (jumlahTransaksi != null) {
                    // Tidak perlu "Rp." karena ini adalah jumlah nota/orang
                    tTotalTransaksi.setText(jumlahTransaksi + " Transaksi");
                } else {
                    tTotalTransaksi.setText("0 Transaksi");
                }
            }
// =========================================================
            // 3. RATA-RATA PESANAN (AVG)
            // =========================================================
            String sqlRataRata = "SELECT AVG(total_pesanan) FROM nota";
            java.sql.PreparedStatement psRataRata = conn.prepareStatement(sqlRataRata);
            java.sql.ResultSet rsRataRata = psRataRata.executeQuery();

            if (rsRataRata.next()) {
                double rataRata = rsRataRata.getDouble(1);
                tRataPesanan.setText(String.format(new java.util.Locale("id", "ID"), "Rp %,.0f", rataRata));
            } else {
                tRataPesanan.setText("Rp 0");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error load data: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tTotalPenjualan = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        tTotalTransaksi = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        tRataPesanan = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        tEkporData = new javax.swing.JPanel();
        btnEksporData = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMenuTerlaris = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(505, 533));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(553, 50));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 20)); // NOI18N
        jLabel1.setText("Laporan Penjualan  Admin");

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cikbohay/icons8-bell-20.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cikbohay/icons8-profile-20.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 247, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel3))
                .addGap(16, 16, 16))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(181, 72, 72));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(505, 533));

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel2.setText("Total Penjualan");

        tTotalPenjualan.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        tTotalPenjualan.setText("RP.");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(tTotalPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tTotalPenjualan)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setPreferredSize(new java.awt.Dimension(161, 100));

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel7.setText("Total Transaksi");

        tTotalTransaksi.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        tTotalTransaksi.setText("RP.");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(tTotalTransaksi))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tTotalTransaksi)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setPreferredSize(new java.awt.Dimension(161, 100));

        jLabel10.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel10.setText("Rata-Rata Pesanan");

        tRataPesanan.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        tRataPesanan.setText("RP.");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(tRataPesanan))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tRataPesanan)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));

        jLabel13.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel13.setText("Transaksi Terahkir");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "Waktu", "Total", "Status"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                .addContainerGap())
        );

        tEkporData.setBackground(new java.awt.Color(181, 72, 72));

        btnEksporData.setFont(new java.awt.Font("Helvetica Neue", 1, 10)); // NOI18N
        btnEksporData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cikbohay/icons8-export-20.png"))); // NOI18N
        btnEksporData.setText("Expor Data");
        btnEksporData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEksporDataMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout tEkporDataLayout = new javax.swing.GroupLayout(tEkporData);
        tEkporData.setLayout(tEkporDataLayout);
        tEkporDataLayout.setHorizontalGroup(
            tEkporDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tEkporDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEksporData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        tEkporDataLayout.setVerticalGroup(
            tEkporDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tEkporDataLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEksporData)
                .addContainerGap())
        );

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Menu Terlaris");

        tblMenuTerlaris.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblMenuTerlaris);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addContainerGap(124, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(17, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(tEkporData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tEkporData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEksporDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEksporDataMouseClicked
        // TODO add your handling code here:
        // 1. MEMBUKA DIALOG PEMILIHAN TEMPAT SIMPAN
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Ekspor E-Statement Laporan Penjualan");
        fileChooser.setSelectedFile(new java.io.File("E-Statement_CikBohay.html"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Memastikan filenya berekstensi .html
            if (!filePath.endsWith(".html")) {
                filePath += ".html";
                fileToSave = new java.io.File(filePath);
            }

            try {
                java.sql.Connection conn = (java.sql.Connection) koneksi.konek();
                java.util.Locale idLocale = new java.util.Locale("id", "ID");

                // ====================================================
                // A. AMBIL DATA RENTANG WAKTU (PERIODE)
                // ====================================================
                String tglAwal = "-", tglAkhir = "-";
                String sqlPeriode = "SELECT MIN(waktu_transaksi), MAX(waktu_transaksi) FROM nota";
                java.sql.PreparedStatement psPeriode = conn.prepareStatement(sqlPeriode);
                java.sql.ResultSet rsPeriode = psPeriode.executeQuery();
                if (rsPeriode.next() && rsPeriode.getTimestamp(1) != null) {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMMM yyyy", idLocale);
                    tglAwal = sdf.format(rsPeriode.getTimestamp(1));
                    tglAkhir = sdf.format(rsPeriode.getTimestamp(2));
                }

                // ====================================================
                // B. AMBIL DATA HIGHLIGHT: TOP 3 MENU TERLARIS
                // ====================================================
                StringBuilder barisMenuTerlaris = new StringBuilder();
                String sqlTop3 = "SELECT m.nama_menu, SUM(p.jumlah) AS total_terjual "
                        + "FROM pesanan p JOIN menu m ON p.id_menu = m.id_menu "
                        + "GROUP BY m.id_menu, m.nama_menu "
                        + "ORDER BY total_terjual DESC LIMIT 3";
                java.sql.PreparedStatement psTop3 = conn.prepareStatement(sqlTop3);
                java.sql.ResultSet rsTop3 = psTop3.executeQuery();
                int urutan = 1;
                while (rsTop3.next()) {
                    barisMenuTerlaris.append("<tr>")
                            .append("<td>").append(urutan++).append("</td>")
                            .append("<td>").append(rsTop3.getString("nama_menu")).append("</td>")
                            .append("<td>").append(rsTop3.getInt("total_terjual")).append(" Porsi</td>")
                            .append("</tr>");
                }

                // ====================================================
                // C. AMBIL MUTASI DETAIL TRANSAKSI (JOIN NOTA & PESANAN)
                // ====================================================
                StringBuilder barisTransaksi = new StringBuilder();
                String sqlDetail = "SELECT n.waktu_transaksi, n.id_nota, m.nama_menu, p.jumlah, p.total, n.metode_pembayaran "
                        + "FROM nota n JOIN pesanan p ON n.id_nota = p.id_nota "
                        + "JOIN menu m ON p.id_menu = m.id_menu "
                        + "ORDER BY n.waktu_transaksi DESC";
                java.sql.PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
                java.sql.ResultSet rsDetail = psDetail.executeQuery();

                java.text.SimpleDateFormat sdfJam = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm", idLocale);
                while (rsDetail.next()) {
                    String waktu = sdfJam.format(rsDetail.getTimestamp("waktu_transaksi"));
                    String nota = rsDetail.getString("id_nota");
                    String menu = rsDetail.getString("nama_menu");
                    int qty = rsDetail.getInt("jumlah");
                    double subtotal = rsDetail.getDouble("total");
                    String metode = rsDetail.getString("metode_pembayaran");

                    String formatSubtotal = String.format(idLocale, "Rp %,.0f", subtotal);

                    barisTransaksi.append("<tr>")
                            .append("<td>").append(waktu).append("</td>")
                            .append("<td><b>").append(nota).append("</b></td>")
                            .append("<td>").append(menu).append("</td>")
                            .append("<td style='text-align:center;'>").append(qty).append("</td>")
                            .append("<td style='text-align:right;'>").append(formatSubtotal).append("</td>")
                            .append("<td><span class='badge ").append(metode.toLowerCase()).append("'>").append(metode).append("</span></td>")
                            .append("</tr>");
                }

                // ====================================================
                // D. PROSES MENULIS ANTARMUKA HTML SECARA STRUKTUR
                // ====================================================
                java.io.FileWriter fw = new java.io.FileWriter(fileToSave);
                java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);

                // Teks HTML dengan CSS modern agar mirip e-statement perbankan
                String strukturHTML
                        = "<html><head><style>"
                        + "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 40px; color: #333; }"
                        + ".header { text-align: center; border-bottom: 3px double #b54848; padding-bottom: 20px; }"
                        + ".header h1 { margin: 0; color: #b54848; font-size: 28px; }"
                        + ".info-table { width: 100%; margin-top: 20px; margin-bottom: 30px; font-size: 14px; }"
                        + "table.data-table { width: 100%; border-collapse: collapse; margin-top: 10px; font-size: 14px; }"
                        + "table.data-table th { background-color: #b54848; color: white; padding: 10px; text-align: left; }"
                        + "table.data-table td { padding: 10px; border-bottom: 1px solid #ddd; }"
                        + "table.data-table tr:nth-child(even) { background-color: #f9f9f9; }"
                        + "h3 { color: #b54848; border-left: 4px solid #b54848; padding-left: 10px; margin-top: 30px; }"
                        + ".badge { padding: 4px 8px; border-radius: 4px; font-size: 12px; font-weight: bold; color: white; }"
                        + ".cash { background-color: #28a745; }"
                        + ".qris { background-color: #007bff; }"
                        + "</style></head><body>"
                        + "<div class='header'>"
                        + "<h1>CIK BOHAY MANAGEMENT SYSTEM</h1>"
                        + "<p style='margin:5px 0 0 0; font-size:14px; color:#666;'>E-Statement Laporan Rekapitulasi Penjualan Resmi</p>"
                        + "</div>"
                        + "<table class='info-table'>"
                        + "<tr><td style='width:15%;'><b>Periode Laporan</b></td><td>: " + tglAwal + " s/d " + tglAkhir + "</td>"
                        + "<td style='text-align:right; color:#888;'>Dicetak pada: " + new java.util.Date() + "</td></tr>"
                        + "</table>"
                        + "<h3>🔥 INSIGHT: 3 MENU TERLARIS PERIODE INI</h3>"
                        + "<table class='data-table' style='width: 50%;'>"
                        + "<tr><th style='width:10%;'>Rank</th><th>Nama Menu</th><th style='width:30%;'>Total Terjual</th></tr>"
                        + barisMenuTerlaris.toString()
                        + "</table>"
                        + "<h3>📊 REKAPITULASI MUTASI TRANSAKSI</h3>"
                        + "<table class='data-table'>"
                        + "<tr><th>Waktu Transaksi</th><th>ID Nota</th><th>Nama Menu</th><th style='text-align:center;'>Jumlah</th><th style='text-align:right;'>Subtotal</th><th>Metode</th></tr>"
                        + barisTransaksi.toString()
                        + "</table>"
                        + "<div style='margin-top:50px; text-align:center; font-size:11px; color:#aaa;'>"
                        + "Dokumen ini di-generate otomatis oleh Sistem Kasir Cik Bohay. Segala bentuk kecurangan data di luar sistem merupakan tanggung jawab penuh pengguna."
                        + "</div>"
                        + "</body></html>";

                bw.write(strukturHTML);
                bw.close();
                fw.close();

                javax.swing.JOptionPane.showMessageDialog(this, "E-Statement Berhasil Diekspor ke:\n" + fileToSave.getAbsolutePath(), "Sukses Ekspor", javax.swing.JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Gagal mengekspor data: " + e.getMessage(), "Error Ekspor", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEksporDataMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnEksporData;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTable jTable1;
    private javax.swing.JPanel tEkporData;
    private javax.swing.JLabel tRataPesanan;
    private javax.swing.JLabel tTotalPenjualan;
    private javax.swing.JLabel tTotalTransaksi;
    private javax.swing.JTable tblMenuTerlaris;
    // End of variables declaration//GEN-END:variables
}
