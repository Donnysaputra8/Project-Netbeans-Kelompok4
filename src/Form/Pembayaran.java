package Form;
import Koneksi.Koneksi;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.JOptionPane;

/**
 *
 * @author ZeeDane
 */
public class Pembayaran extends javax.swing.JFrame {
int xx, xy;
private javax.swing.table.DefaultTableModel tabmode;
private Connection conn = new Koneksi().connect();


    /**
     * Creates new form Pembayaran
     */
    public Pembayaran() {
        initComponents();
        aktif();           
        datatable();       
        isiComboPemesanan();
        autonumber();
        kosong();
        cat.setLineWrap(true);
        cat.setWrapStyleWord(true);
    }
    
    private void aktif() {
    idbyr.requestFocus();
    idbyr.setText("");
    jmlhbyr.setText("");
    cat.setText("");
    bktbyr.setText("");
    cidpsn.setSelectedIndex(-1);
    mtdbyr.setSelectedIndex(-1);
    jnsbyr.setSelectedIndex(-1);
    jtgl.setDate(null);
    }
    
    private void datatable() {
    Object[] columns = {"ID Pembayaran", "ID Pemesanan", "Tanggal Bayar", "Jenis Pembayaran", "Jumlah Bayar", "Metode Pembayaran", "Bukti Pembayaran", "Catatan"};
    tabmode = new DefaultTableModel(null, columns);
    try {
        String sql = "SELECT * FROM pembayaran_event"; // Sesuaikan dengan nama tabelmu
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(sql);
        while (rs.next()) {
            tabmode.addRow(new Object[]{
                rs.getString("id_pembayaran"),
                rs.getString("id_pemesanan"),
                rs.getDate("tanggal_bayar"),
                rs.getString("jenis_pembayaran"),
                rs.getString("jumlah_bayar"),
                rs.getString("metode_pembayaran"),
                rs.getString("bukti_pembayaran"),
                rs.getString("catatan")
            });
        }
        tblbyr.setModel(tabmode);
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Data gagal dimuat: " + e);
        }
    }
    
    private void isiComboPemesanan() {
    try {
        String sql = "SELECT id_pemesanan FROM pemesanan_event"; 
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(sql);
        cidpsn.removeAllItems();
        while (rs.next()) {
            cidpsn.addItem(rs.getString("id_pemesanan"));
            }
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal load data ID Pemesanan: " + e.getMessage());
        }
    }

    private String copyImageToFolder(String sourcePath) {
    try {
        File sourceFile = new File(sourcePath);
        String destinationDir = "bukti_pembayaran/";
        File destFile = new File(destinationDir + sourceFile.getName());

        // Buat folder kalau belum ada
        File dir = new File(destinationDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Salin file
        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Kembalikan hanya nama file
        return sourceFile.getName();
        } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Gagal menyalin gambar: " + e.getMessage());
        return null;
        }
    }
    
    protected void autonumber() {
    try {
        String sql = "SELECT id_pembayaran FROM pembayaran_event order by id_pembayaran asc";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        idbyr.setText("IB0001");

        while (rs.next()) {
            String id_nota = rs.getString("id_pembayaran").substring(2);
            int AN = Integer.parseInt(id_nota) + 1;
            String Nol = "";

            if (AN < 10) {
                Nol = "000";
            } else if (AN < 100) {
                Nol = "00";
            } else if (AN < 1000) {
                Nol = "0";
            }

            idbyr.setText("IB" + Nol + AN);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Auto Number Gagal" + e);
    }
}
    
    private void hitungTotalPembayaran(String idPemesanan) {
    try {
        String sql = "SELECT v.harga_sewa, p.harga " +
                     "FROM pemesanan_event pm " +
                     "JOIN venue v ON pm.id_venue = v.id_venue " +
                     "JOIN paket_event p ON pm.id_paket = p.id_paket " +
                     "WHERE pm.id_pemesanan = ?";
        
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, idPemesanan);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            int hargaVenue = rs.getInt("harga_sewa");
            int hargaPaket = rs.getInt("harga");
            int total = hargaVenue + hargaPaket;
            jmlhbyr.setText(String.valueOf(total));
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Gagal menghitung jumlah bayar: " + e.getMessage());
    }
}
    
    public void cetak() {
    try{
        String path="./src/Form/nota_pembayaran.jasper";
        HashMap parameter = new HashMap () ;
        parameter.put ("id_pembayaran", idbyr.getText () ) ;
        JasperPrint print = JasperFillManager. fillReport (path, parameter, conn) ;
        JasperViewer. viewReport (print, false) ;
       } catch (Exception ex) {
            JOptionPane. showMessageDialog(rootPane, "Dokumen Tidak Ada" +ex);
        }
    }

    
    private void kosong() {
    cidpsn.setSelectedIndex(-1); // Reset combobox Id Pemesanan
    jmlhbyr.setText("");         // Kosongkan Jumlah Bayar
    mtdbyr.setSelectedIndex(-1); // Reset combobox Metode Pembayaran
    bktbyr.setText("");          // Kosongkan Bukti Pembayaran
    jtgl.setDate(null);          // Kosongkan Tanggal
    jnsbyr.setSelectedIndex(-1); // Reset combobox Jenis Pembayaran
    cat.setText("");              // Kosongkan Text Area Catatan
    }



    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cidpsn = new javax.swing.JComboBox<String>();
        jLabel9 = new javax.swing.JLabel();
        idbyr = new javax.swing.JTextField();
        jtgl = new com.toedter.calendar.JDateChooser();
        jnsbyr = new javax.swing.JComboBox<String>();
        jmlhbyr = new javax.swing.JTextField();
        mtdbyr = new javax.swing.JComboBox<String>();
        jScrollPane1 = new javax.swing.JScrollPane();
        cat = new javax.swing.JTextArea();
        bsimpan = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbatal = new javax.swing.JButton();
        bktbyr = new javax.swing.JTextField();
        bbrowse = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblbyr = new javax.swing.JTable();
        menu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Pembayaran");

        jLabel2.setText("Id Pemesanan :");

        jLabel3.setText("Tanggal Bayar :");

        jLabel4.setText("Jenis Pembayaran :");

        jLabel5.setText("Jumlah Bayar :");

        jLabel6.setText("Metode Pembayaran :");

        jLabel7.setText("Bukti Pembayaran :");

        jLabel8.setText("Catatan :");

        cidpsn.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cidpsn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cidpsnActionPerformed(evt);
            }
        });

        jLabel9.setText("Id Pembayaran :");

        jnsbyr.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Transfer", "Tunai" }));

        mtdbyr.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DP", "Lunas" }));

        cat.setColumns(20);
        cat.setRows(5);
        jScrollPane1.setViewportView(cat);

        bsimpan.setBackground(new java.awt.Color(153, 153, 153));
        bsimpan.setText("Simpan");
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });

        bhapus.setBackground(new java.awt.Color(153, 153, 153));
        bhapus.setText("Hapus");
        bhapus.setPreferredSize(new java.awt.Dimension(67, 23));
        bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapusActionPerformed(evt);
            }
        });

        bbatal.setBackground(new java.awt.Color(153, 153, 153));
        bbatal.setText("Batal");
        bbatal.setPreferredSize(new java.awt.Dimension(67, 23));
        bbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbatalActionPerformed(evt);
            }
        });

        bbrowse.setText("Browse");
        bbrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbrowseActionPerformed(evt);
            }
        });

        tblbyr.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblbyr);

        menu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        menu.setForeground(new java.awt.Color(255, 0, 51));
        menu.setText("X");
        menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 36, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(352, 352, 352)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(menu)
                        .addGap(43, 43, 43))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(bktbyr, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(bbrowse)
                                        .addGap(157, 157, 157))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(mtdbyr, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(cidpsn, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jmlhbyr)
                                                .addComponent(idbyr, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addGap(27, 27, 27)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtgl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jnsbyr, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(menu))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(idbyr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel3)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cidpsn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jmlhbyr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jnsbyr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mtdbyr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(bktbyr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bbrowse)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bbrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbrowseActionPerformed
        JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    int result = fileChooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        // Simpan path gambar yang dipilih ke TextField
        bktbyr.setText(selectedFile.getAbsolutePath());
    }
    }//GEN-LAST:event_bbrowseActionPerformed

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
        String id_byr = idbyr.getText().trim();
    String id_pmsn = (cidpsn.getSelectedItem() != null) ? cidpsn.getSelectedItem().toString().trim() : ""; 
    String jumlah = jmlhbyr.getText().trim();
    String metode = (mtdbyr.getSelectedItem() != null) ? mtdbyr.getSelectedItem().toString().trim() : ""; 
    String bukti = bktbyr.getText().trim();
    String jenis = (jnsbyr.getSelectedItem() != null) ? jnsbyr.getSelectedItem().toString().trim() : ""; 
    String catatan = cat.getText().trim();

    if (id_byr.isEmpty() || id_pmsn.isEmpty() || jumlah.isEmpty() || metode.isEmpty() || bukti.isEmpty() || jenis.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        java.util.Date utilDate = jtgl.getDate();
        if (utilDate == null) {
            JOptionPane.showMessageDialog(this, "Tanggal belum diisi!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            return;
        }
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        String sql = "INSERT INTO pembayaran_event (id_pembayaran, id_pemesanan, jumlah_bayar, metode_pembayaran, bukti_pembayaran, tanggal_bayar, jenis_pembayaran, catatan) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement stat = conn.prepareStatement(sql);
        stat.setString(1, id_byr);
        stat.setString(2, id_pmsn);
        stat.setString(3, jumlah);
        stat.setString(4, metode);
        stat.setString(5, bukti);
        stat.setDate(6, sqlDate);
        stat.setString(7, jenis);
        stat.setString(8, catatan);

        stat.executeUpdate();
        JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
        cetak();
        kosong();
        aktif();
        autonumber();
        datatable();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Data gagal disimpan!\n" + e);
    }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
        String id_byr = idbyr.getText().trim();

    if (id_byr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Pilih data yang mau dihapus!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data dengan ID: " + id_byr + " ?", 
                                                  "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        try {
            String sql = "DELETE FROM pembayaran_event WHERE id_pembayaran=?";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, id_byr);
            stat.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
            kosong();
            aktif();
            autonumber();
            datatable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal dihapus!\n" + e);
        }
    }
    }//GEN-LAST:event_bhapusActionPerformed

    private void bbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbatalActionPerformed
        kosong();
        aktif();
    }//GEN-LAST:event_bbatalActionPerformed

    private void menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuActionPerformed
        Menu mm = new Menu();
        mm.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_menuActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_formMouseDragged

    private void cidpsnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cidpsnActionPerformed
        String id = (String) cidpsn.getSelectedItem();
        if (id != null) {
            hitungTotalPembayaran(id);
        }
    }//GEN-LAST:event_cidpsnActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pembayaran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbatal;
    private javax.swing.JButton bbrowse;
    private javax.swing.JButton bhapus;
    private javax.swing.JTextField bktbyr;
    private javax.swing.JButton bsimpan;
    private javax.swing.JTextArea cat;
    private javax.swing.JComboBox<String> cidpsn;
    private javax.swing.JTextField idbyr;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jmlhbyr;
    private javax.swing.JComboBox<String> jnsbyr;
    private com.toedter.calendar.JDateChooser jtgl;
    private javax.swing.JButton menu;
    private javax.swing.JComboBox<String> mtdbyr;
    private javax.swing.JTable tblbyr;
    // End of variables declaration//GEN-END:variables
}
