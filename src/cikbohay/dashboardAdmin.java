/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cikbohay;

import java.awt.CardLayout;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author macbookairm12020
 */
public class dashboardAdmin extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(dashboardAdmin.class.getName());
    private CardLayout CardLayout;

    /**
     * Creates new form cikbohayy
     */
    public boolean modeEdit = false;

    public dashboardAdmin() {
        initComponents();

        pindahCard();
        load_tabel_menu_semua();
        load_tabel_menu_seblak();
        load_tabel_menu_geprek();
        load_tabel_menu_minuman();
        load_tabel_menu_mie();
        load_tabel_menu_snack();
        editMenu.setVisible(false);
        setKodeOtomatis();
        comboKategori();
    }

    private void editTabelMenu() {

        javax.swing.JTable tabelAktif = null;

        // 1. Cek tabel mana yang aktif
        if (tblMenuSemua.getSelectedRow() != -1) {
            tabelAktif = tblMenuSemua;
        } else if (tblMenuSeblak.getSelectedRow() != -1) {
            tabelAktif = tblMenuSeblak;
        } else if (tblMenuGeprek.getSelectedRow() != -1) {
            tabelAktif = tblMenuGeprek;
        } else if (tblMenuMinuman.getSelectedRow() != -1) {
            tabelAktif = tblMenuMinuman;
        } else if (tblMenuSnack.getSelectedRow() != -1) {
            tabelAktif = tblMenuSnack;
        } else if (tblMenuMie.getSelectedRow() != -1) {
            tabelAktif = tblMenuMie;
        }

        // 2. Peringatan kalau belum pilih baris
        if (tabelAktif == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Silahkan pilih menu yang akan diedit terlebih dahulu");
            return;
        }

        // 3. Sedot datanya dari baris yang diklik
        int barisTerpilih = tabelAktif.getSelectedRow();
        String idMenu = tabelAktif.getValueAt(barisTerpilih, 0).toString();
        String namaMenu = tabelAktif.getValueAt(barisTerpilih, 1).toString();
        String hargaMenu = tabelAktif.getValueAt(barisTerpilih, 2).toString();
        String idKategori = tabelAktif.getValueAt(barisTerpilih, 3).toString();

        // 4. TEMBAKKAN DATA LANGSUNG KE TEXT FIELD (Nggak perlu pakai embel-embel panelEdit lagi)
        tIdMenu.setText(idMenu);
        tIdMenu.setEditable(false);
        tNamaMenu.setText(namaMenu);
        tHarga.setText(hargaMenu);
        cKategori.setSelectedItem(idKategori);

        // Cocokkan ID Kategori kembali menjadi Teks untuk Combo Box
        if (idKategori.equals("k001")) {
            cKategori.setSelectedItem("Seblak");
        } else if (idKategori.equals("k002")) {
            cKategori.setSelectedItem("Geprek");
        } else if (idKategori.equals("k003")) {
            cKategori.setSelectedItem("Minuman");
        } else if (idKategori.equals("k004")) {
            cKategori.setSelectedItem("Snack");
        } else if (idKategori.equals("k005")) {
            cKategori.setSelectedItem("Mie LVL");
        }

        // 5. Kasih tahu sistem kalau ini mode EDIT
        // Pastikan kamu udah deklarasi 'public boolean modeEdit = false;' di bagian paling atas class dashboardAdmin ya!
        modeEdit = true;
    }

    void comboKategori() {
        try {
            String sql = "SELECT * FROM kategori";

            Connection con = koneksi.konek();

            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                cKategori.addItem(resultSet.getString("nama_kategori"));
            }
        } catch (SQLException sQLException) {

        }
        cKategori.setSelectedItem(null);
    }

    void load_tabel_menu_snack() {

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID MENU");

        model.addColumn("Nama Menu");

        model.addColumn("Harga");

        model.addColumn("Kategori");

        String sql = "SELECT * FROM menu WHERE id_kategori = 'k004'";

        try {
            Connection con = koneksi.konek();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                String idMenu = rs.getString("id_menu");

                String namaMenu = rs.getString("nama_menu");

                String formatTitik = String.format("%,d", rs.getLong("harga")).replace(',', '.');
                // Set kembali ke text field ditambah tulisan "Rp " di depannya
                String harga = ("Rp " + formatTitik);

                String idKategori = rs.getString("id_kategori");

                Object[] baris = {idMenu, namaMenu, harga, idKategori};
                model.addRow(baris);
            }

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil data!");
        }
        tblMenuSnack.setModel(model);
    }

    void load_tabel_menu_mie() {

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID MENU");

        model.addColumn("Nama Menu");

        model.addColumn("Harga");

        model.addColumn("Kategori");

        String sql = "SELECT * FROM menu WHERE id_kategori = 'k005'";

        try {
            Connection con = koneksi.konek();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                String idMenu = rs.getString("id_menu");

                String namaMenu = rs.getString("nama_menu");

                String formatTitik = String.format("%,d", rs.getLong("harga")).replace(',', '.');
                // Set kembali ke text field ditambah tulisan "Rp " di depannya
                String harga = ("Rp " + formatTitik);

                String idKategori = rs.getString("id_kategori");

                Object[] baris = {idMenu, namaMenu, harga, idKategori};
                model.addRow(baris);
            }

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil data!");
        }
        tblMenuMie.setModel(model);
    }

    void load_tabel_menu_semua() {

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID MENU");

        model.addColumn("Nama Menu");

        model.addColumn("Harga");

        model.addColumn("Kategori");

        String sql = "SELECT * FROM menu";

        try {
            Connection con = koneksi.konek();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                String idMenu = rs.getString("id_menu");

                String namaMenu = rs.getString("nama_menu");
                String formatTitik = String.format("%,d", rs.getLong("harga")).replace(',', '.');
                // Set kembali ke text field ditambah tulisan "Rp " di depannya
                String harga = ("Rp " + formatTitik);

                String idKategori = rs.getString("id_kategori");

                Object[] baris = {idMenu, namaMenu, harga, idKategori};
                model.addRow(baris);
            }

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil data!");
        }
        tblMenuSemua.setModel(model);
    }

    void load_tabel_menu_seblak() {

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID MENU");

        model.addColumn("Nama Menu");

        model.addColumn("Harga");

        model.addColumn("Kategori");

        String sql = "SELECT * FROM menu WHERE id_kategori = 'k001'";

        try {
            Connection con = koneksi.konek();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                String idMenu = rs.getString("id_menu");

                String namaMenu = rs.getString("nama_menu");

                String formatTitik = String.format("%,d", rs.getLong("harga")).replace(',', '.');
                // Set kembali ke text field ditambah tulisan "Rp " di depannya
                String harga = ("Rp " + formatTitik);

                String idKategori = rs.getString("id_kategori");

                Object[] baris = {idMenu, namaMenu, harga, idKategori};
                model.addRow(baris);
            }

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil data!");
        }
        tblMenuSeblak.setModel(model);
    }

    void load_tabel_menu_geprek() {

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID MENU");

        model.addColumn("Nama Menu");

        model.addColumn("Harga");

        model.addColumn("Kategori");

        String sql = "SELECT * FROM menu WHERE id_kategori = 'k002'";

        try {
            Connection con = koneksi.konek();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                String idMenu = rs.getString("id_menu");

                String namaMenu = rs.getString("nama_menu");

                String formatTitik = String.format("%,d", rs.getLong("harga")).replace(',', '.');
                // Set kembali ke text field ditambah tulisan "Rp " di depannya
                String harga = ("Rp " + formatTitik);

                String idKategori = rs.getString("id_kategori");

                Object[] baris = {idMenu, namaMenu, harga, idKategori};
                model.addRow(baris);
            }

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil data!");
        }
        tblMenuGeprek.setModel(model);
    }

    void load_tabel_menu_minuman() {

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID MENU");

        model.addColumn("Nama Menu");

        model.addColumn("Harga");

        model.addColumn("Kategori");

        String sql = "SELECT * FROM menu WHERE id_kategori = 'k003'";

        try {
            Connection con = koneksi.konek();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                String idMenu = rs.getString("id_menu");

                String namaMenu = rs.getString("nama_menu");

                String formatTitik = String.format("%,d", rs.getLong("harga")).replace(',', '.');
                // Set kembali ke text field ditambah tulisan "Rp " di depannya
                String harga = ("Rp " + formatTitik);

                String idKategori = rs.getString("id_kategori");

                Object[] baris = {idMenu, namaMenu, harga, idKategori};
                model.addRow(baris);
            }

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil data!");
        }
        tblMenuMinuman.setModel(model);
    }

    void pindahCard() {
        CardLayout = (CardLayout) panelContent.getLayout();
        panelContent.add(new laporanadmin(), "Laporan Admin");
        panelContent.add(new PNTNkrgJLSStok_Barang_Admin(), "Stok Admin");
        panelContent.add(tampilanMenu, "kartuMenu");
//        panelContent.add(new tampilanMenu(), "Semua");
        //panelContent.add(new menu)
    }

    void reset() {
        if (modeEdit == true) {
            tIdMenu.setText(null);
        }
        cKategori.setSelectedItem(null);
        tNamaMenu.setText(null);
        tHarga.setText(null);
    }

    private void pindahKartuMenu(String namaKartuMenu) {
        CardLayout cl
                =//variabel cl (dengan tipe data atau apalah) CardLayout 
                (CardLayout) panelMenu.getLayout(); //mengambil kartu dari panel menu

        cl.show //menunjukkan kartu
                (panelMenu, namaKartuMenu); //dari kartu utama, diambil kartu yang mana
    }

    private void setKodeOtomatis() {
        // Sesuaikan query-nya dengan table di database kamu
        try {
            String sql = "SELECT MAX(id_menu) FROM menu"; // Ganti 'menu' dengan nama tabelmu
            java.sql.Connection conn = (java.sql.Connection) koneksi.konek();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);

            if (rs.next() && rs.getString(1) != null) {
                String idTerakhir = rs.getString(1); // Misal: mnu0001

                // Ambil karakter dari indeks 3 sampai akhir ("0001")
                String angkaStr = idTerakhir.substring(3);
                int angka = Integer.parseInt(angkaStr) + 1;

                // Format jadi 4 digit ("0002")
                tIdMenu.setText("mnu" + String.format("%04d", angka));
            } else {
                // Kalau tabel kosong
                tIdMenu.setText("mnu0001");
            }
        } catch (Exception e) {
            // Kalau error, kasih ID default biar aplikasi nggak crash
            tIdMenu.setText("mnu0001");
            System.out.println("Error setKodeOtomatis: " + e.getMessage());
        }
        tIdMenu.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSideBar = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tLaporanAdmin = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        tMenuAdmin = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        tStokAdmin = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        tExit = new javax.swing.JLabel();
        panelContent = new javax.swing.JPanel();
        tampilanMenu = new javax.swing.JPanel();
        tampilan = new javax.swing.JPanel();
        tombol = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnTambahMenu = new javax.swing.JButton();
        btnEditMenu = new javax.swing.JButton();
        btnHapusMenu = new javax.swing.JButton();
        bawah = new javax.swing.JPanel();
        tombolMenu = new javax.swing.JPanel();
        btnMenuSemua = new javax.swing.JButton();
        btnMenuSeblak = new javax.swing.JButton();
        btnMenuGeprek = new javax.swing.JButton();
        btnMenuMinuman = new javax.swing.JButton();
        btnMenuSnack = new javax.swing.JButton();
        btnMenuMie = new javax.swing.JButton();
        panelMenu = new javax.swing.JPanel();
        cardMenuSemua = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMenuSemua = new javax.swing.JTable();
        cardMenuSeblak = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMenuSeblak = new javax.swing.JTable();
        cardMenuGeprek = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMenuGeprek = new javax.swing.JTable();
        cardMenuMinuman = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblMenuMinuman = new javax.swing.JTable();
        cardMenuSnack = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblMenuSnack = new javax.swing.JTable();
        cardMenuMie = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblMenuMie = new javax.swing.JTable();
        editMenu = new javax.swing.JPanel();
        editMenuAdmin = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tIdMenu = new javax.swing.JTextField();
        tHarga = new javax.swing.JTextField();
        tNamaMenu = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();
        tPesanMenu = new javax.swing.JLabel();
        cKategori = new javax.swing.JComboBox<>();
        btnTambahEditMenu = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnTutupEditMenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelSideBar.setBackground(new java.awt.Color(255, 255, 255));
        panelSideBar.setPreferredSize(new java.awt.Dimension(150, 533));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cikbohay/logo_cikbohay3.jpeg"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 153, 51));
        jLabel6.setText("Cik Bohay");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 153, 51));
        jLabel7.setText("Menegemen System");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(181, 72, 72));
        jPanel4.setPreferredSize(new java.awt.Dimension(130, 36));

        tLaporanAdmin.setBackground(new java.awt.Color(255, 255, 255));
        tLaporanAdmin.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        tLaporanAdmin.setForeground(new java.awt.Color(255, 255, 255));
        tLaporanAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cikbohay/icons8-document-20.png"))); // NOI18N
        tLaporanAdmin.setText("Laporan");
        tLaporanAdmin.setIconTextGap(10);
        tLaporanAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tLaporanAdminMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tLaporanAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tLaporanAdmin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(181, 72, 72));
        jPanel5.setPreferredSize(new java.awt.Dimension(130, 36));

        tMenuAdmin.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        tMenuAdmin.setForeground(new java.awt.Color(255, 255, 255));
        tMenuAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cikbohay/icons8-restaurant-20.png"))); // NOI18N
        tMenuAdmin.setText("Menu");
        tMenuAdmin.setIconTextGap(10);
        tMenuAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tMenuAdminMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tMenuAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tMenuAdmin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(181, 72, 72));
        jPanel6.setPreferredSize(new java.awt.Dimension(130, 36));

        tStokAdmin.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        tStokAdmin.setForeground(new java.awt.Color(255, 255, 255));
        tStokAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cikbohay/icons8-package-20.png"))); // NOI18N
        tStokAdmin.setText("Stok Barang");
        tStokAdmin.setIconTextGap(10);
        tStokAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tStokAdminMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tStokAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(tStokAdmin)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(181, 72, 72));
        jPanel7.setPreferredSize(new java.awt.Dimension(130, 36));

        tExit.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        tExit.setForeground(new java.awt.Color(255, 255, 255));
        tExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cikbohay/icons8-logout-20.png"))); // NOI18N
        tExit.setText("Keluar");
        tExit.setIconTextGap(10);
        tExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tExitMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tExit)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelSideBarLayout = new javax.swing.GroupLayout(panelSideBar);
        panelSideBar.setLayout(panelSideBarLayout);
        panelSideBarLayout.setHorizontalGroup(
            panelSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelSideBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelSideBarLayout.setVerticalGroup(
            panelSideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSideBarLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 283, Short.MAX_VALUE))
        );

        getContentPane().add(panelSideBar, java.awt.BorderLayout.LINE_START);

        panelContent.setBackground(new java.awt.Color(181, 72, 72));
        panelContent.setLayout(new java.awt.CardLayout());

        tampilanMenu.setLayout(new java.awt.BorderLayout());

        tampilan.setLayout(new java.awt.BorderLayout());

        tombol.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 20)); // NOI18N
        jLabel1.setText("Menu Cik Bohay");
        tombol.add(jLabel1);

        btnTambahMenu.setBackground(new java.awt.Color(0, 204, 51));
        btnTambahMenu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTambahMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cikbohay/icons8-plus-20.png"))); // NOI18N
        btnTambahMenu.setText("Tambah Menu Baru");
        btnTambahMenu.addActionListener(this::btnTambahMenuActionPerformed);
        tombol.add(btnTambahMenu);

        btnEditMenu.setBackground(new java.awt.Color(255, 204, 0));
        btnEditMenu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnEditMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cikbohay/icone/icons8-white-edit-20.png"))); // NOI18N
        btnEditMenu.setText("Edit Menu");
        btnEditMenu.addActionListener(this::btnEditMenuActionPerformed);
        tombol.add(btnEditMenu);

        btnHapusMenu.setBackground(new java.awt.Color(255, 51, 51));
        btnHapusMenu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHapusMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnHapusMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cikbohay/icone/icons8-remove-20.png"))); // NOI18N
        btnHapusMenu.setText("Hapus Menu");
        btnHapusMenu.addActionListener(this::btnHapusMenuActionPerformed);
        tombol.add(btnHapusMenu);

        tampilan.add(tombol, java.awt.BorderLayout.PAGE_START);

        bawah.setLayout(new java.awt.BorderLayout());

        tombolMenu.setLayout(new java.awt.GridLayout(1, 0));

        btnMenuSemua.setText("Semua");
        btnMenuSemua.addActionListener(this::btnMenuSemuaActionPerformed);
        tombolMenu.add(btnMenuSemua);

        btnMenuSeblak.setText("Seblak");
        btnMenuSeblak.addActionListener(this::btnMenuSeblakActionPerformed);
        tombolMenu.add(btnMenuSeblak);

        btnMenuGeprek.setText("Geprek");
        btnMenuGeprek.addActionListener(this::btnMenuGeprekActionPerformed);
        tombolMenu.add(btnMenuGeprek);

        btnMenuMinuman.setText("Minuman");
        btnMenuMinuman.addActionListener(this::btnMenuMinumanActionPerformed);
        tombolMenu.add(btnMenuMinuman);

        btnMenuSnack.setText("Snack");
        btnMenuSnack.addActionListener(this::btnMenuSnackActionPerformed);
        tombolMenu.add(btnMenuSnack);

        btnMenuMie.setText("Mie LVL");
        btnMenuMie.addActionListener(this::btnMenuMieActionPerformed);
        tombolMenu.add(btnMenuMie);

        bawah.add(tombolMenu, java.awt.BorderLayout.PAGE_START);

        panelMenu.setLayout(new java.awt.CardLayout());

        cardMenuSemua.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 0), 5));
        cardMenuSemua.setLayout(new java.awt.GridLayout(1, 0));

        tblMenuSemua.setModel(new javax.swing.table.DefaultTableModel(
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
        tblMenuSemua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMenuSemuaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMenuSemua);

        cardMenuSemua.add(jScrollPane1);

        panelMenu.add(cardMenuSemua, "cardMenuSemua");

        cardMenuSeblak.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0), 5));
        cardMenuSeblak.setLayout(new java.awt.GridLayout(1, 0));

        tblMenuSeblak.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblMenuSeblak);

        cardMenuSeblak.add(jScrollPane2);

        panelMenu.add(cardMenuSeblak, "cardMenuSeblak");

        cardMenuGeprek.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 51), 5));
        cardMenuGeprek.setLayout(new java.awt.GridLayout(1, 0));

        tblMenuGeprek.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblMenuGeprek);

        cardMenuGeprek.add(jScrollPane3);

        panelMenu.add(cardMenuGeprek, "cardMenuGeprek");

        cardMenuMinuman.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255), 5));
        cardMenuMinuman.setLayout(new java.awt.GridLayout(1, 0));

        tblMenuMinuman.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblMenuMinuman);

        cardMenuMinuman.add(jScrollPane4);

        panelMenu.add(cardMenuMinuman, "cardMenuMinuman");

        cardMenuSnack.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 255, 51), 5));
        cardMenuSnack.setLayout(new java.awt.GridLayout(1, 0));

        tblMenuSnack.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblMenuSnack);

        cardMenuSnack.add(jScrollPane5);

        panelMenu.add(cardMenuSnack, "cardMenuSnack");

        cardMenuMie.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 102), 5));
        cardMenuMie.setLayout(new java.awt.GridLayout(1, 0));

        tblMenuMie.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(tblMenuMie);

        cardMenuMie.add(jScrollPane6);

        panelMenu.add(cardMenuMie, "cardMenuMie");

        bawah.add(panelMenu, java.awt.BorderLayout.CENTER);

        tampilan.add(bawah, java.awt.BorderLayout.CENTER);

        tampilanMenu.add(tampilan, java.awt.BorderLayout.CENTER);

        editMenu.setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setBackground(new java.awt.Color(181, 72, 72));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel2.setText("ID MENU               :");

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel3.setText("KATEGORI            :");

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel4.setText("HARGA                  :");

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel8.setText("NAMA MENU         :");

        tIdMenu.setBackground(new java.awt.Color(204, 204, 204));
        tIdMenu.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        tHarga.setBackground(new java.awt.Color(204, 204, 204));
        tHarga.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tHargaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tHargaKeyTyped(evt);
            }
        });

        tNamaMenu.setBackground(new java.awt.Color(204, 204, 204));
        tNamaMenu.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        btnReset.setBackground(new java.awt.Color(255, 204, 0));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cikbohay/icons8-reset-20.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(this::btnResetActionPerformed);

        tPesanMenu.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        tPesanMenu.setText("TAMBAH MENU BARU");

        cKategori.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N

        btnTambahEditMenu.setBackground(new java.awt.Color(0, 204, 51));
        btnTambahEditMenu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnTambahEditMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahEditMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cikbohay/icons8-plus-20.png"))); // NOI18N
        btnTambahEditMenu.setText("Tambahkan Menu");
        btnTambahEditMenu.addActionListener(this::btnTambahEditMenuActionPerformed);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tNamaMenu))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tHarga)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(0, 40, Short.MAX_VALUE)
                                .addComponent(btnTambahEditMenu))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tIdMenu)
                            .addComponent(cKategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(60, 60, 60))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(tPesanMenu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(tPesanMenu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tIdMenu)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tNamaMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tHarga, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahEditMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cikbohay/logo_cikbohay31.jpeg"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 153, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("CIK BOHAY");
        jLabel10.setToolTipText("");

        jLabel11.setBackground(new java.awt.Color(255, 153, 51));
        jLabel11.setForeground(new java.awt.Color(255, 153, 51));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Managemen System");

        btnTutupEditMenu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTutupEditMenu.setForeground(new java.awt.Color(255, 0, 0));
        btnTutupEditMenu.setText("Tutup");
        btnTutupEditMenu.addActionListener(this::btnTutupEditMenuActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnTutupEditMenu)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel9))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnTutupEditMenu)
                                .addGap(44, 44, 44)))
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout editMenuAdminLayout = new javax.swing.GroupLayout(editMenuAdmin);
        editMenuAdmin.setLayout(editMenuAdminLayout);
        editMenuAdminLayout.setHorizontalGroup(
            editMenuAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        editMenuAdminLayout.setVerticalGroup(
            editMenuAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        editMenu.add(editMenuAdmin);

        tampilanMenu.add(editMenu, java.awt.BorderLayout.LINE_END);

        panelContent.add(tampilanMenu, "kartuMenu");

        getContentPane().add(panelContent, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tLaporanAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tLaporanAdminMouseClicked
        // TODO add your handling code here:
        CardLayout.show(panelContent, "Laporan Admin");
    }//GEN-LAST:event_tLaporanAdminMouseClicked

    private void tMenuAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tMenuAdminMouseClicked
//        // TODO add your handling code here:
//         CardLayout.show(panelContent, "Semua");
//        new tampilanMenu().setVisible(true);
        CardLayout.show(panelContent, "kartuMenu");
// Memastikan tombol tambah menu muncul untuk Admin
//        menuJendela.btnTambahMenu.setVisible(true);
//        menuJendela.btnEditMenu.setVisible(true);
//        menuJendela.btnHapusMenu.setVisible(true);
//        menuJendela.setVisible(true);
    }//GEN-LAST:event_tMenuAdminMouseClicked

    private void tStokAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tStokAdminMouseClicked
        // TODO add your handling code here:
        CardLayout.show(panelContent, "Stok Admin");
    }//GEN-LAST:event_tStokAdminMouseClicked

    private void tExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tExitMouseClicked
        // TODO add your handling code here:
        int pilihan = JOptionPane.showConfirmDialog(
                null,
                "Apakah Anda yakin ingin keluar ?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        switch (pilihan) {
            case JOptionPane.YES_OPTION:
                dispose();
                new login().setVisible(true);
                break;
            case JOptionPane.NO_OPTION:
                break;
            default:
                break;
        }
    }//GEN-LAST:event_tExitMouseClicked

    private void btnTambahMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahMenuActionPerformed
        // TODO add your handling code here:
        // 5. Kasih tahu sistem kalau ini mode EDIT
        // Pastikan kamu udah deklarasi 'public boolean modeEdit = false;' di bagian paling atas class dashboardAdmin ya!
        modeEdit = false;

        // Ganti tulisan di panel kanan biar sesuai
        btnTambahMenu.setText("Tambahkan Menu");
        tPesanMenu.setText("TAMBAH MENU BARU");

        // 6. BUKA LACI FORM-NYA! 
        // Ganti 'editMenu' dengan nama variabel JPanel kananmu
        editMenu.setVisible(true);
        setKodeOtomatis();
        comboKategori();
        reset();
        // 7. Refresh UI paksa agar tabel kiri otomatis minggir dan laci kanan muncul
        // Ganti 'panelContent' dengan nama panel GridLayout yang membungkus area kiri dan kanan
        panelContent.revalidate();
        panelContent.repaint();
    }//GEN-LAST:event_btnTambahMenuActionPerformed

    private void btnEditMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMenuActionPerformed
        // TODO add your handling code here:
        javax.swing.JTable tabelAktif = null;

        // 1. Cek tabel mana yang aktif
        if (tblMenuSemua.getSelectedRow() != -1) {
            tabelAktif = tblMenuSemua;
        } else if (tblMenuSeblak.getSelectedRow() != -1) {
            tabelAktif = tblMenuSeblak;
        } else if (tblMenuGeprek.getSelectedRow() != -1) {
            tabelAktif = tblMenuGeprek;
        } else if (tblMenuMinuman.getSelectedRow() != -1) {
            tabelAktif = tblMenuMinuman;
        } else if (tblMenuSnack.getSelectedRow() != -1) {
            tabelAktif = tblMenuSnack;
        } else if (tblMenuMie.getSelectedRow() != -1) {
            tabelAktif = tblMenuMie;
        }

        // 2. Peringatan kalau belum pilih baris
        if (tabelAktif == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Silahkan pilih menu yang akan diedit terlebih dahulu");
            return;
        }

        // 3. Sedot datanya dari baris yang diklik
        int barisTerpilih = tabelAktif.getSelectedRow();
        String idMenu = tabelAktif.getValueAt(barisTerpilih, 0).toString();
        String namaMenu = tabelAktif.getValueAt(barisTerpilih, 1).toString();
        String hargaMenu = tabelAktif.getValueAt(barisTerpilih, 2).toString();
        String idKategori = tabelAktif.getValueAt(barisTerpilih, 3).toString();

        // 4. TEMBAKKAN DATA LANGSUNG KE TEXT FIELD (Nggak perlu pakai embel-embel panelEdit lagi)
        tIdMenu.setText(idMenu);
        tIdMenu.setEditable(false);
        tNamaMenu.setText(namaMenu);
        tHarga.setText(hargaMenu);
        cKategori.setSelectedItem(idKategori);

        // Cocokkan ID Kategori kembali menjadi Teks untuk Combo Box
        if (idKategori.equals("k001")) {
            cKategori.setSelectedItem("Seblak");
        } else if (idKategori.equals("k002")) {
            cKategori.setSelectedItem("Geprek");
        } else if (idKategori.equals("k003")) {
            cKategori.setSelectedItem("Minuman");
        } else if (idKategori.equals("k004")) {
            cKategori.setSelectedItem("Snack");
        } else if (idKategori.equals("k005")) {
            cKategori.setSelectedItem("Mie LVL");
        }

        // 5. Kasih tahu sistem kalau ini mode EDIT
        // Pastikan kamu udah deklarasi 'public boolean modeEdit = false;' di bagian paling atas class dashboardAdmin ya!
        modeEdit = true;

        // Ganti tulisan di panel kanan biar sesuai
        btnTambahEditMenu.setText("Simpan Perubahan");
        tPesanMenu.setText("Edit Menu");

        // 6. BUKA LACI FORM-NYA! 
        // Ganti 'editMenu' dengan nama variabel JPanel kananmu
        editMenu.setVisible(true);

        // 7. Refresh UI paksa agar tabel kiri otomatis minggir dan laci kanan muncul
        // Ganti 'panelContent' dengan nama panel GridLayout yang membungkus area kiri dan kanan
//        panelContent.revalidate();
//        panelContent.repaint();
        // CATATAN: Hapus fungsi load_tabel di bawah sini. 
        // Tabel cukup di-refresh nanti setelah tombol 'Simpan Perubahan' (warna hijau) diklik.
    }//GEN-LAST:event_btnEditMenuActionPerformed

    private void btnHapusMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusMenuActionPerformed
        // TODO add your handling code here:
        // 1. Kita bikin wadah kosong untuk menentukan tabel mana yang lagi aktif
        javax.swing.JTable tabelAktif = null;

        // 2. Kita cek satu-satu, tabel mana yang barisnya lagi diklik (nilainya bukan -1)
        if (tblMenuSemua.getSelectedRow() != -1) {
            tabelAktif = tblMenuSemua;
        } else if (tblMenuSeblak.getSelectedRow() != -1) {
            tabelAktif = tblMenuSeblak;
        } else if (tblMenuGeprek.getSelectedRow() != -1) {
            tabelAktif = tblMenuGeprek;
        } else if (tblMenuMinuman.getSelectedRow() != -1) {
            tabelAktif = tblMenuMinuman;
        } else if (tblMenuSnack.getSelectedRow() != -1) {
            tabelAktif = tblMenuSnack;
        } else if (tblMenuMie.getSelectedRow() != -1) {
            tabelAktif = tblMenuMie;
        }

        // 3. Kalau admin belum klik tabel apa-apa di tab manapun, kasih peringatan
        if (tabelAktif == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Silahkan pilih menu yang akan dihapus terlebih dahulu!");
            return;
        }

        // 4. Ambil ID dan Nama dari tabel yang terdeteksi aktif tadi
        int barisTerpilih = tabelAktif.getSelectedRow();
        String idMenu = tabelAktif.getValueAt(barisTerpilih, 0).toString();
        String namaMenu = tabelAktif.getValueAt(barisTerpilih, 1).toString();
        String hargaMenu = tabelAktif.getValueAt(barisTerpilih, 2).toString();
        String kategoriMenu = tabelAktif.getValueAt(barisTerpilih, 3).toString();
        // 5. Pop-up Konfirmasi
        int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus " + namaMenu + ", " + hargaMenu + ", " + kategoriMenu + ", " + "(" + idMenu + ")?",
                "Konfirmasi Hapus",
                javax.swing.JOptionPane.YES_NO_OPTION);

        if (konfirmasi == javax.swing.JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM menu WHERE id_menu = ?";
                java.sql.Connection conn = koneksi.konek();
                java.sql.PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, idMenu);
                ps.execute();

                javax.swing.JOptionPane.showMessageDialog(this, "Menu berhasil dihapus.");

                // 6. Refresh semua tabel
                load_tabel_menu_semua();
                load_tabel_menu_seblak();
                load_tabel_menu_geprek();
                load_tabel_menu_minuman();
                load_tabel_menu_snack();
                load_tabel_menu_mie();

            } catch (java.sql.SQLException e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Gagal menghapus data ");
            }
        }
    }//GEN-LAST:event_btnHapusMenuActionPerformed

    private void tblMenuSemuaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMenuSemuaMouseClicked
        // TODO add your handling code here:
        editTabelMenu();
    }//GEN-LAST:event_tblMenuSemuaMouseClicked

    private void btnMenuMieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuMieActionPerformed
        // TODO add your handling code here:
        pindahKartuMenu("cardMenuMie");//menamppilkan menu Mie LVL
    }//GEN-LAST:event_btnMenuMieActionPerformed

    private void btnMenuSnackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuSnackActionPerformed
        // TODO add your handling code here:
        pindahKartuMenu("cardMenuSnack");//menamppilkan menu Snack
    }//GEN-LAST:event_btnMenuSnackActionPerformed

    private void btnMenuMinumanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuMinumanActionPerformed
        // TODO add your handling code here:
        pindahKartuMenu("cardMenuMinuman");//menamppilkan menu Minuman
    }//GEN-LAST:event_btnMenuMinumanActionPerformed

    private void btnMenuGeprekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuGeprekActionPerformed
        // TODO add your handling code here:
        pindahKartuMenu("cardMenuGeprek");//menamppilkan menu Geprek
    }//GEN-LAST:event_btnMenuGeprekActionPerformed

    private void btnMenuSeblakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuSeblakActionPerformed
        // TODO add your handling code here:
        pindahKartuMenu("cardMenuSeblak");//menamppilkan menu seblak
    }//GEN-LAST:event_btnMenuSeblakActionPerformed

    private void btnMenuSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuSemuaActionPerformed
        // TODO add your handling code here:
        pindahKartuMenu("cardMenuSemua");//menamppilkan semua menu
    }//GEN-LAST:event_btnMenuSemuaActionPerformed

    private void tHargaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tHargaKeyReleased
        // TODO add your handling code here:
        // 1. Ambil teks yang ada sekarang, lalu hapus semua karakter selain angka
        String hargaRaw = tHarga.getText().replaceAll("[^\\d]", "");

        // 2. Kalau field-nya nggak kosong, kita format angkanya
        if (!hargaRaw.isEmpty()) {
            try {
                long angka = Long.parseLong(hargaRaw);

                // Format angka menjadi ribuan dengan titik (contoh: 15.000)
                String formatTitik = String.format("%,d", angka).replace(',', '.');

                // Set kembali ke text field ditambah tulisan "Rp " di depannya
                tHarga.setText("Rp " + formatTitik);

            } catch (NumberFormatException e) {
                System.out.println("Error format harga: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_tHargaKeyReleased

    private void tHargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tHargaKeyTyped
        // TODO add your handling code here:
        char huruf = evt.getKeyChar();
        // Jika yang diketik bukan angka, abaikan/tolak
        if (!Character.isDigit(huruf)) {
            evt.consume();
        }
    }//GEN-LAST:event_tHargaKeyTyped

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    String IdKategori(String Kategori) {
        try {
            String sql = "SELECT * FROM kategori WHERE nama_kategori = ?";

            Connection con = koneksi.konek();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, Kategori);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                return resultSet.getString("id_kategori");
            }
        } catch (SQLException sQLException) {

        }
        return "";
    }

    private void btnTambahEditMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahEditMenuActionPerformed
        // TODO add your handling code here:

        String IdMenu = tIdMenu.getText();

        String Kategori = IdKategori(cKategori.getSelectedItem().toString());

        String NamaMenu = tNamaMenu.getText();

        // GANTI dengan baris di bawah ini biar yang masuk ke DB cuma ANGKA-nya saja (15000)
        String Harga = tHarga.getText().replaceAll("[^\\d]", "");

        Object itemKategori = cKategori.getSelectedItem();
        String NamaKategori = (itemKategori != null) ? itemKategori.toString() : "";

        // 2. PASANG SATPAM (VALIDASI KOSONG) DI SINI
        if (IdMenu.isEmpty() || NamaMenu.isEmpty() || Harga.isEmpty() || NamaKategori.isEmpty()) {
            // Munculkan pop-up peringatan
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Harap isi semua kolom terlebih dahulu!",
                    "Peringatan",
                    javax.swing.JOptionPane.WARNING_MESSAGE);

            // RETURN sangat penting! Ini yang bikin Java berhenti dan batal masuk ke try-catch SQL
            return;
        }

        try {
            Connection con = koneksi.konek();
            PreparedStatement statement;

            if (modeEdit == true) {
                // MODE EDIT: Jalankan UPDATE
                String sql = "UPDATE menu SET id_kategori=?, nama_menu=?, harga=? WHERE id_menu=?";
                statement = con.prepareStatement(sql);
                statement.setString(1, Kategori);
                statement.setString(2, NamaMenu);
                statement.setString(3, Harga);
                statement.setString(4, IdMenu); // ID taruh di akhir karena WHERE
                statement.execute();
                javax.swing.JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
            } else {
                // MODE TAMBAH BARU: Jalankan INSERT
                String sql = "INSERT INTO menu (id_menu, id_kategori, nama_menu, harga) VALUES (?, ?, ?, ?)";
                statement = con.prepareStatement(sql);
                statement.setString(1, IdMenu);
                statement.setString(2, Kategori);
                statement.setString(3, NamaMenu);
                statement.setString(4, Harga);
                statement.execute();
                javax.swing.JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            }

            //            // Tutup pop-up
            //            javax.swing.wi window = javax.swing.SwingUtilities.getWindowAncestor(this);
            //            if (window instanceof javax.swing.JDialog) {
            //                window.dispose();
            //            }
        } catch (SQLException sQLException) {
            javax.swing.JOptionPane.showMessageDialog(null, "Data gagal diproses! " + sQLException.getMessage());
        }
        load_tabel_menu_geprek();
        load_tabel_menu_mie();
        load_tabel_menu_minuman();
        load_tabel_menu_seblak();
        load_tabel_menu_semua();
        load_tabel_menu_snack();
        setKodeOtomatis();
        reset();

    }//GEN-LAST:event_btnTambahEditMenuActionPerformed

    private void btnTutupEditMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTutupEditMenuActionPerformed
        // TODO add your handling code here:
        reset();
        // 1. Tutup lacinya
        editMenu.setVisible(false);

        // 2. Refresh UI biar tabel kiri langsung melar lagi
        panelContent.revalidate();
        panelContent.repaint();
    }//GEN-LAST:event_btnTutupEditMenuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new dashboardAdmin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bawah;
    public javax.swing.JButton btnEditMenu;
    public javax.swing.JButton btnHapusMenu;
    private javax.swing.JButton btnMenuGeprek;
    private javax.swing.JButton btnMenuMie;
    private javax.swing.JButton btnMenuMinuman;
    private javax.swing.JButton btnMenuSeblak;
    private javax.swing.JButton btnMenuSemua;
    private javax.swing.JButton btnMenuSnack;
    private javax.swing.JButton btnReset;
    public javax.swing.JButton btnTambahEditMenu;
    public javax.swing.JButton btnTambahMenu;
    private javax.swing.JButton btnTutupEditMenu;
    public javax.swing.JComboBox<String> cKategori;
    private javax.swing.JPanel cardMenuGeprek;
    private javax.swing.JPanel cardMenuMie;
    private javax.swing.JPanel cardMenuMinuman;
    private javax.swing.JPanel cardMenuSeblak;
    private javax.swing.JPanel cardMenuSemua;
    private javax.swing.JPanel cardMenuSnack;
    private javax.swing.JPanel editMenu;
    private javax.swing.JPanel editMenuAdmin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel panelContent;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelSideBar;
    private javax.swing.JLabel tExit;
    public javax.swing.JTextField tHarga;
    public javax.swing.JTextField tIdMenu;
    private javax.swing.JLabel tLaporanAdmin;
    private javax.swing.JLabel tMenuAdmin;
    public javax.swing.JTextField tNamaMenu;
    public javax.swing.JLabel tPesanMenu;
    private javax.swing.JLabel tStokAdmin;
    private javax.swing.JPanel tampilan;
    private javax.swing.JPanel tampilanMenu;
    private javax.swing.JTable tblMenuGeprek;
    private javax.swing.JTable tblMenuMie;
    private javax.swing.JTable tblMenuMinuman;
    private javax.swing.JTable tblMenuSeblak;
    private javax.swing.JTable tblMenuSemua;
    private javax.swing.JTable tblMenuSnack;
    private javax.swing.JPanel tombol;
    private javax.swing.JPanel tombolMenu;
    // End of variables declaration//GEN-END:variables
}
