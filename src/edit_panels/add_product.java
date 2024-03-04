/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package edit_panels;
import ShopDao.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import com.formdev.flatlaf.ui.FlatUIUtils;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author jorge
 */
public class add_product extends javax.swing.JPanel {

    public static Connection con;
    private int_productoDao intproductoDao;
    private byte[] selectedImage;

    /**
     * Creates new form edit_produc
     */
    public add_product() {
        initComponents();
        initStyles();
        loadCategoriesFromDatabase();

    }
    private void initStyles() {
        jLabel1.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h2.font")));
        jLabel2.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h2.font")));
        jLabel3.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h2.font")));
        jLabel4.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h2.font")));
        jLabel5.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h2.font")));
        jLabel6.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h2.font")));
        jLabel7.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h2.font")));
        add_button.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h3.font")));
        add_img_button.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h3.font")));
        clearFields();
    }
    private void connect(){
        conexionDB conexion = new conexionDB();
        con = conexion.establecerConexion();
    }
    private void clearFields() {
        name.setText("");
        cat.setSelectedIndex(0);
        price.setText("");
        description.setText("");
        stock.setText("");

    }
    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // O manejo adecuado de la excepción en un entorno real
        }
    }

    private void loadCategoriesFromDatabase() {
        try {
            categoriesList = new ArrayList<>();
            // Establecer la conexión
            connect();
            // Consultar las categorías desde la base de datos
            String query = "SELECT categoria FROM int_categoria";
            categoriesList.clear();
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    categoriesList.add(rs.getString("categoria"));
                }

                // Agregar las categorías al JComboBox
                cat.setModel(new DefaultComboBoxModel<>(categoriesList.toArray(new String[0])));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void addProduct() {
        try {
            connect();
            intproductoDao = new int_productoImp(con);
            // Obtener los valores de los campos de texto
            String nombre = name.getText();
            String categoria = (String) cat.getSelectedItem();
            String precio = price.getText();
            String descripcion = description.getText();
            String cantidad = stock.getText(); // Agregar esta línea para obtener la cantidad

            // Verificar que los campos no estén vacíos
            if (nombre.isEmpty() || precio.isEmpty() || cantidad.isEmpty() || descripcion.isEmpty() || selectedImage == null) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Convertir el precio y la cantidad a enteros (puedes agregar manejo de errores aquí)
            float precioFloat = Float.parseFloat(precio);
            int cantidadInt = Integer.parseInt(cantidad);

            // Obtener el ID de la categoría
            int int_categoria_id = getIntCatId(categoria);

            // Llamar al método existente en el DAO para agregar el producto con la imagen
            int rowsAffected = intproductoDao.setProducto(int_categoria_id, precioFloat, nombre, cantidadInt, descripcion, selectedImage);

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Producto agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo agregar el producto", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
    private byte[] getSelectedImage() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(null);
            File f = fileChooser.getSelectedFile();

            // Lee la imagen como un array de bytes
            return Files.readAllBytes(f.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

// Método para obtener el ID de la categoría
private int getIntCatId(String categoria) throws SQLException {
    int int_categoria_id = 0;
    String query = "SELECT id FROM int_categoria WHERE categoria = ?";
    
    try (PreparedStatement ps = con.prepareStatement(query)) {
        ps.setString(1, categoria);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int_categoria_id = rs.getInt("id");
            }
        }
    }

    return int_categoria_id;
}
    private void uploadImg() {
        String directorioInicial = "D:\\Universidad\\SEMESTRE IX\\Interfaz\\Proyecto";
        JFileChooser fileChooser = new JFileChooser(directorioInicial);
        fileChooser.showOpenDialog(null);

        File selectedFile = fileChooser.getSelectedFile();
        if (selectedFile == null) {
            return;  // No se seleccionó ningún archivo
        }

        try {
            BufferedImage originalImage = ImageIO.read(selectedFile);

            // Convertir la imagen a un array de bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);
            byte[] imagenBytes = baos.toByteArray();

            // Mostrar la imagen en el JLabel manteniendo el aspecto
            showImg(imagenBytes);

            // Almacenar la imagen en el campo selectedImage
            selectedImage = imagenBytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void showImg(byte[] imagenBytes) {
        try {
            // Crear ImageIcon desde bytes
            ImageIcon imagenIcon = new ImageIcon(imagenBytes);

            // Obtener las dimensiones originales de la imagen
            int anchoOriginal = imagenIcon.getIconWidth();
            int altoOriginal = imagenIcon.getIconHeight();

            // Obtener las dimensiones del JLabel
            int anchoLabel = jLabel6.getWidth();
            int altoLabel = jLabel6.getHeight();

            // Calcular el nuevo tamaño para mantener el aspect ratio de la imagen
            int nuevoAncho, nuevoAlto;

            if ((double) anchoOriginal / altoOriginal > (double) anchoLabel / altoLabel) {
                nuevoAncho = anchoLabel;
                nuevoAlto = (int) ((double) anchoLabel / anchoOriginal * altoOriginal);
            } else {
                nuevoAlto = altoLabel;
                nuevoAncho = (int) ((double) altoLabel / altoOriginal * anchoOriginal);
            }

            // Escalar la imagen manteniendo el aspecto
            Image imagenOriginal = imagenIcon.getImage();
            Image imagenEscalada = imagenOriginal.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);

            // Crear un nuevo ImageIcon con la imagen escalada y fondo transparente
            ImageIcon imagenEscaladaIcon = new ImageIcon(imagenEscalada);

            // Establecer el tamaño del JLabel de acuerdo con el aspect ratio de la imagen
            jLabel6.setSize(nuevoAncho, nuevoAlto);

            // Establecer la imagen escalada en el JLabel
            jLabel6.setIcon(imagenEscaladaIcon);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The Form Editor always
     * regenerates the content of this method.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        name = new javax.swing.JTextField();
        price = new javax.swing.JTextField();
        stock = new javax.swing.JTextField();
        cat = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        add_button = new javax.swing.JButton();
        add_img_button = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        description = new javax.swing.JTextArea();

        name.setText("jTextField1");
        name.setMaximumSize(new java.awt.Dimension(1024, 1024));
        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });

        price.setText("jTextField1");
        price.setMaximumSize(new java.awt.Dimension(1024, 1024));
        price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceActionPerformed(evt);
            }
        });

        stock.setText("jTextField1");
        stock.setMaximumSize(new java.awt.Dimension(1024, 1024));
        stock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockActionPerformed(evt);
            }
        });

        cat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cat.setMaximumSize(new java.awt.Dimension(1024, 1024));
        cat.setPreferredSize(new java.awt.Dimension(72, 30));
        cat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                catActionPerformed(evt);
            }
        });

        jLabel1.setText("Nombre");

        jLabel2.setText("Precio");

        jLabel3.setText("Categoría");

        jLabel4.setText("Descripción");

        jLabel5.setText("Imagen");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel7.setText("Cantidad");

        add_button.setText("Añadir Imagen");
        add_button.setBorder(null);
        add_button.setBorderPainted(false);
        add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_buttonActionPerformed(evt);
            }
        });

        add_img_button.setText("Añadir Producto");
        add_img_button.setBorder(null);
        add_img_button.setBorderPainted(false);
        add_img_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_img_buttonActionPerformed(evt);
            }
        });

        description.setColumns(20);
        description.setRows(5);
        jScrollPane1.setViewportView(description);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(100, 100, 100))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(price, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(add_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(add_img_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(price, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(45, 45, 45)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(cat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(add_img_button))
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bgLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(39, 39, 39)
                                .addComponent(add_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(98, 98, 98))
                            .addGroup(bgLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(72, 72, 72))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void add_img_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_img_buttonActionPerformed
        connect();
        addProduct();
    }//GEN-LAST:event_add_img_buttonActionPerformed

    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_buttonActionPerformed
        uploadImg();
    }//GEN-LAST:event_add_buttonActionPerformed

    private void priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceActionPerformed

    private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameActionPerformed

    private void stockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stockActionPerformed

    private void catActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_catActionPerformed
        //loadCategories();
    }//GEN-LAST:event_catActionPerformed

    private java.util.List<String> categoriesList = null;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_button;
    private javax.swing.JButton add_img_button;
    private javax.swing.JPanel bg;
    private javax.swing.JComboBox<String> cat;
    private javax.swing.JTextArea description;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField name;
    private javax.swing.JTextField price;
    private javax.swing.JTextField stock;
    // End of variables declaration//GEN-END:variables
}
