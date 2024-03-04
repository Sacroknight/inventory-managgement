/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShopDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ajota
 */
public class int_productoImp implements int_productoDao {
     /**
     * process list of int_producto table.
     */
    //public List<int_producto> producto;
    private List<int_producto> producto;

    /**
     * Conection DB.
     */
    public Connection dbConn = null;
    
     private List<int_producto> n_data;
    /**
     * Constructor of int_proceso_vars_dataIMP class
     * 
     * @param dbConn Database connection.
     */
    public int_productoImp(Connection dbConn) {
        this.producto = new ArrayList<int_producto>();
        this.dbConn = dbConn;
    }
    @Override
    public int_producto getProducto(int idProducto) {
        
        int_producto intProducto =null;
        try{
            Statement stm = this.dbConn.createStatement();
            ResultSet rs = stm.executeQuery("select nombre_producto, precio, stock, descripcion from int_producto where int_categoria_id="+idProducto);
            while(rs.next()){
               intProducto = new int_producto();
               intProducto.setNombre(rs.getString("nombre_producto"));
               intProducto.setPrecio(rs.getDouble("precio"));
               intProducto.setStock(rs.getInt("stock"));
               intProducto.setDescripcion(rs.getString("descripcion"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return intProducto;
    }

    
    public List<int_producto> getAllData(int int_categoria_idfk){
        //n_data = new ArrayList<int_producto>();
        //n_data.clear();
        
        try {
            Statement stm = this.dbConn.createStatement();
            ResultSet rs = stm.executeQuery("select * from int_producto where int_categoria_id=" + int_categoria_idfk);
            while (rs.next()) {
                int_producto intUser = new int_producto();
                intUser.setNombre(rs.getString("nombre_producto"));
                intUser.setPrecio(rs.getDouble("precio"));
                intUser.setStock(rs.getInt("stock"));
                intUser.setDescripcion(rs.getString("descripcion"));
                n_data.add(intUser);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return n_data;
    
    }

    @Override
    public int setProducto(int int_categoria_idfk, float precio, String nombre, int stock, String descripcion) {
        return 0;
    }


    @Override
    public int setProducto(int int_categoria_idfk, float precio, String nombre, int stock, String descripcion, byte[] imagen) {
        int resRows = 0;

        try {
            // Asegúrate de que estás utilizando PreparedStatement para evitar SQL Injection
            PreparedStatement stm = this.dbConn.prepareStatement("INSERT INTO int_producto (nombre_producto, precio, stock, descripcion, int_categoria_id, imagen) VALUES (?, ?, ?, ?, ?, ?)");
            stm.setString(1, nombre);
            stm.setFloat(2, precio);
            stm.setInt(3, stock);
            stm.setString(4, descripcion);
            stm.setInt(5, int_categoria_idfk);
            stm.setBytes(6, imagen);

            resRows = stm.executeUpdate();
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return resRows;
    }

    public void setDataWithImag(int_producto data) {
        try (PreparedStatement stm = this.dbConn.prepareStatement("INSERT INTO INT_PRODUCTO (nombre_producto, precio, stock, descripcion, imagen, int_categoria_id) VALUES (?, ?, ?, ?, ?, ?)")) {
            stm.setString(1, data.getNombre());
            stm.setFloat(2, (float) data.getPrecio());
            stm.setInt(3, data.getStock());
            stm.setString(4, data.getDescripción());
            stm.setBytes(5, data.getImagen());
            stm.setInt(6, data.getIntCategoriaId());
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<int_producto> getDataWithImag(int int_categoria_idfk) {
        ArrayList<int_producto> lista = new ArrayList<>();

        try (Statement stm = this.dbConn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM int_producto WHERE int_categoria_id=" + int_categoria_idfk)) {

            while (rs.next()) {
                int_producto intProducto = new int_producto();
                intProducto.setNombre(rs.getString("nombre_producto"));
                intProducto.setPrecio(rs.getDouble("precio"));
                intProducto.setStock(rs.getInt("stock"));
                intProducto.setDescripcion(rs.getString("descripcion"));
                intProducto.setImagen(rs.getBytes("imagen"));
                lista.add(intProducto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }




    @Override
    public int deleteProducto(int productID) {
        int flag = 0;
        try {
            Statement stm = this.dbConn.createStatement();
            flag = stm.executeUpdate("delete from int_producto where id=" + productID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    public int actualizarStock(String categoria, String nombre, int nuevoStock) {
        int rowsAffected = 0;

        try {
            String updateQuery = "UPDATE int_producto SET stock = ? WHERE int_categoria_id = (SELECT id FROM int_categoria WHERE categoria = ? LIMIT 1) AND nombre_producto = ?";
            PreparedStatement updatePs = this.dbConn.prepareStatement(updateQuery);

            try {
                updatePs.setInt(1, nuevoStock);
                updatePs.setString(2, categoria);
                updatePs.setString(3, nombre);
                rowsAffected = updatePs.executeUpdate();
            } catch (Throwable var10) {
                if (updatePs != null) {
                    try {
                        updatePs.close();
                    } catch (Throwable var9) {
                        var10.addSuppressed(var9);
                    }
                }

                throw var10;
            }

            if (updatePs != null) {
                updatePs.close();
            }
        } catch (SQLException var11) {
            var11.printStackTrace();
        }

        return rowsAffected;
    }
    
    public byte[] getImg(int productID) {
        byte[] imagenBytes = null;

        try {
            PreparedStatement stm = this.dbConn.prepareStatement("SELECT imagen FROM int_producto WHERE id = ?");
            stm.setInt(1, productID);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                imagenBytes = rs.getBytes("imagen");
            }

            rs.close();
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        }

        return imagenBytes;
    }
   @Override
public int updateProducto(String nombre, int int_categoria_idfk, float precio, String nuevoNombre, int stock, String descripcion, byte[] imagen) {
    int resRows = 0;

    try {
        // Asegúrate de que estás utilizando PreparedStatement para evitar SQL Injection
        PreparedStatement stm = this.dbConn.prepareStatement("UPDATE int_producto SET nombre_producto = ?, precio = ?, stock = ?, descripcion = ?, int_categoria_id = ?, imagen = ? WHERE nombre_producto = ?");
        stm.setString(1, nuevoNombre);
        stm.setFloat(2, precio);
        stm.setInt(3, stock);
        stm.setString(4, descripcion);
        stm.setInt(5, int_categoria_idfk);
        stm.setBytes(6, imagen);
        stm.setString(7, nombre);

        resRows = stm.executeUpdate();
    } catch (Exception var8) {
        var8.printStackTrace();
    }

    return resRows;
}

    
    
}
