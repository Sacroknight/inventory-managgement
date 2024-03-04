/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package edit_panels;

import ShopDao.conexionDB;
import ShopDao.int_producto;
import ShopDao.int_productoDao;
import ShopDao.int_productoImp;
import com.formdev.flatlaf.ui.FlatUIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class del_product extends JPanel {

    public static Connection con;
    private int_productoDao intproductoDao;

    /**
     * Creates new form edit_produc
     */
    public del_product() {
        initComponents();
        initStyles();
        loadCategoriesFromDatabase();
        loadProductsFromDatabase();
    }

    private void initStyles() {
        jLabel1.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h2.font")));
        jLabel3.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h2.font")));
        jLabel5.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h2.font")));
        jLabel6.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h2.font")));
        jLabel7.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h2.font")));
        charge_img.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h3.font")));
        del_button.setFont(FlatUIUtils.nonUIResource(UIManager.getFont("h3.font")));
        clearFields();
    }

    private void connect() {
        conexionDB conexion = new conexionDB();
        con = conexion.establecerConexion();
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

    private void clearFields() {
        name.setSelectedIndex(0);
        cat.setSelectedIndex(0);
        stock.setText("");
        jLabel6.setText("");

    }

    public void loadCategoriesFromDatabase() {
        try {
            // Establecer la conexión
            connect();
            // Consultar las categorías desde la base de datos
            String query = "SELECT categoria FROM int_categoria";
            ArrayList<Object> categoriesList = new ArrayList<>();
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

    private void loadProductsFromDatabase() {
        try {
            connect();
            //Consultar Productos
            String query = "SELECT nombre_producto FROM int_producto";
            ArrayList<Object> producList = new ArrayList<>();
            producList.clear();
            try {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    producList.add(rs.getString("nombre_producto"));
                }
                name.setModel(new DefaultComboBoxModel<>(producList.toArray(new String[0])));

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            closeConnection();
        }
    }


    private int getIntProductId(String nombreProducto) throws SQLException {
        int idProducto = -1;
        String query = "SELECT id FROM int_producto WHERE nombre_producto = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, nombreProducto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idProducto = rs.getInt("id");
                }
            }
        }
        return idProducto;
    }

    private void deleteProduct() {
        try {
            connect();
            intproductoDao = new int_productoImp(con);

            String nombre = (String) name.getSelectedItem();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El campo de nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = getIntProductId(nombre);

            // Obtener el producto antes de realizar la eliminación o actualización del inventario
            int_producto producto = intproductoDao.getProducto(id);

            String cantidad = stock.getText();
            if (cantidad.isEmpty()) {
                // Eliminar producto
                delProduct(id);
            } else {
                // Actualizar inventario
                updateStock(nombre, cantidad);
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    private void delProduct(int id) {
        int rowsAffected = intproductoDao.deleteProducto(id);

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo encontrar el producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStock(String nombre, String cantidad) {
        // Verificar si la cantidad ingresada es un número válido
        try {
            int cantidadInt = Integer.parseInt(cantidad);
            System.out.println(nombre);
            String categoria = (String) cat.getSelectedItem();
            // Consultar la cantidad actual en la base de datos
            String selectQuery = "SELECT stock FROM int_producto WHERE int_categoria_id = (SELECT id FROM int_categoria WHERE categoria = ? LIMIT 1) AND nombre_producto = ?";
            try (PreparedStatement selectPs = con.prepareStatement(selectQuery)) {
                selectPs.setString(1, categoria);
                selectPs.setString(2, nombre);

                try (ResultSet rs = selectPs.executeQuery()) {
                    if (rs.next()) {
                        int stockActual = rs.getInt("stock");

                        // Verificar si hay suficiente stock para restar la cantidad especificada
                        if (cantidadInt > 0 && cantidadInt <= stockActual) {
                            // Actualizar la cantidad en la base de datos
                            String updateQuery = "UPDATE int_producto SET stock = ? WHERE int_categoria_id = (SELECT id FROM int_categoria WHERE categoria = ? LIMIT 1) AND nombre_producto = ?";
                            try (PreparedStatement updatePs = con.prepareStatement(updateQuery)) {
                                int nuevoStock = stockActual - cantidadInt;
                                updatePs.setInt(1, nuevoStock);
                                updatePs.setString(2, categoria);
                                updatePs.setString(3, nombre);

                                int rowsAffected = updatePs.executeUpdate();
                                if (rowsAffected > 0) {
                                    JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(this, "No se pudo actualizar el producto", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida. El inventario disponible es: " + stockActual, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró el producto", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido en la casilla de cantidad", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace(); // o manejo adecuado de la excepción en un entorno real
    }

    private void chargeImg() {
        try {
            connect();
            intproductoDao = new int_productoImp(con);
            String nombre = (String) name.getSelectedItem();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El campo de nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = getIntProductId(nombre);

            int_producto producto = intproductoDao.getProducto(id);

            byte[] imagenBytes = intproductoDao.getImg(id);

            if (imagenBytes != null && imagenBytes.length > 0) {
                showImg(imagenBytes);
            } else {
                jLabel6.setIcon(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener la imagen desde la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeConnection();
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
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        name = new javax.swing.JComboBox<>();
        cat = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        charge_img = new javax.swing.JButton();
        del_button = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        stock = new javax.swing.JTextField();

        name.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        name.setMaximumSize(new java.awt.Dimension(1024, 1024));
        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });

        cat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cat.setMaximumSize(new java.awt.Dimension(1024, 1024));
        cat.setPreferredSize(new java.awt.Dimension(72, 30));

        jLabel1.setText("Nombre");

        jLabel3.setText("Categoría");

        jLabel5.setText("Imagen");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        charge_img.setText("Cargar Imagen");
        charge_img.setBorder(null);
        charge_img.setBorderPainted(false);
        charge_img.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                charge_imgActionPerformed(evt);
            }
        });

        del_button.setText("Eliminar Producto");
        del_button.setBorder(null);
        del_button.setBorderPainted(false);
        del_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                del_buttonActionPerformed(evt);
            }
        });

        jLabel7.setText("Cantidad");

        stock.setText("jTextField1");
        stock.setMaximumSize(new java.awt.Dimension(1024, 1024));
        stock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(79, 79, 79)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(charge_img, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(22, 22, 22))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(del_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(stock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(39, 39, 39))))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(45, 45, 45)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(del_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)))
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(charge_img, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                        .addGap(98, 98, 98))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)))
                .addContainerGap())
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

    private void del_buttonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_del_buttonActionPerformed
        deleteProduct();
        loadProductsFromDatabase();
    }//GEN-LAST:event_del_buttonActionPerformed

    private void charge_imgActionPerformed(ActionEvent evt)  {//GEN-FIRST:event_charge_imgActionPerformed
        chargeImg();
        }//GEN-LAST:event_charge_imgActionPerformed

    private void nameActionPerformed(ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameActionPerformed

    private void stockActionPerformed(ActionEvent evt) {//GEN-FIRST:event_stockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stockActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JComboBox<String> cat;
    private javax.swing.JButton charge_img;
    private javax.swing.JButton del_button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JComboBox name;
    private javax.swing.JTextField stock;
    // End of variables declaration//GEN-END:variables
}
