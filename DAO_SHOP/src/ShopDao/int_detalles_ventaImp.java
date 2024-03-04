/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShopDao;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.*;
/**
 *
 * @author ajota
 */
public class int_detalles_ventaImp implements int_detalles_ventaDao {
    
    /**
     * Conection DB.
     */
     public Connection dbConn = null;
     /**
     * Constructor int_detalles_ventaImp class
     * @param dbConn 
     */
    public int_detalles_ventaImp(Connection dbConn) {
        
        this.dbConn = dbConn;
    }
    
    /**
     * Set new detail into int_detalles_venta table
     * @param int_producto_idfk
     * @param int_ventas_idfk
     * @param cantidad
     * @param precio_unitario
     * @return 
     */
    @Override
    public int set_Data(int int_producto_idfk, int int_ventas_idfk, int cantidad, double precio_unitario) {
        int resRows = 0;
        try{
            PreparedStatement stm = dbConn.prepareStatement("INSERT INTO int_detalles_ventas SET cantidad=?, precio=?, int_producto_id=?, int_ventas_id");
            stm.setInt(1,cantidad);
            stm.setFloat(2, (float)precio_unitario);
            stm.setInt(3, int_producto_idfk);
            stm.setInt(4, int_ventas_idfk);
            stm.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
        return resRows;

    }

    @Override
    public int_detalles_venta get_Data(int int_producto_idfk, int int_ventas_idfk) {
        int_detalles_venta intDetallesVentas = new int_detalles_venta();
        try{
             Statement stm = this.dbConn.createStatement();
            ResultSet rs = stm.executeQuery("select cantidad, precio from int_detalles_venta where int_producto_id="+int_producto_idfk+"AND int_ventas_id="+int_ventas_idfk);
        }catch(Exception e){
            e.printStackTrace();
        }
        return intDetallesVentas;
    }

    @Override
    public int deleteData(int idpk) {
        int flag = 0;
        try {
            Statement stm = this.dbConn.createStatement();
            flag = stm.executeUpdate("delete from int_detalles_venta where id=" + idpk);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }
    
}
