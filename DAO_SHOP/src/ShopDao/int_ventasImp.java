/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShopDao;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 *
 * @author ajota
 */
public class int_ventasImp implements int_ventasDao {
    /**
     * Conection DB.
     */
    public Connection dbConn = null;
    
    /**
     * Constructor int_ventasIMP class
     * @param dbConn 
     */
    public int_ventasImp(Connection dbConn) {
        
        this.dbConn = dbConn;
    }
    
    /**
     * Set new data in int_ventas table
     * @param int_tipo_usuarios_idfk
     * @param total_venta
     * @param estado
     * @return 
     */
    public int set_Data(int int_tipo_usuarios_idfk, double total_venta, String estado) {
        int resRows = 0;

        GregorianCalendar ahora = new GregorianCalendar();
        String usuarioFechaSesion = ahora.get(Calendar.YEAR) + "-" + ahora.get(Calendar.MONTH) + "-" + ahora.get(Calendar.DAY_OF_MONTH);
        java.sql.Date fechaAhora = java.sql.Date.valueOf(usuarioFechaSesion);
        try{
             PreparedStatement stm = dbConn.prepareStatement("INSERT INTO int_ventas SET fecha_Venta=?, total_venta=?, estado_pago=?, int_tipos_usuario_id=?");
             stm.setDate(1, fechaAhora);
             stm.setFloat(2, (float) total_venta);
             stm.setNString(3, estado);
             stm.setInt(4, int_tipo_usuarios_idfk);
             stm.execute();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return resRows;
    }
    /**
     * Get a specific data of int_ventas table
     * @param int_tipo_ususarios_idfk
     * @return 
     */
    public int_ventas get_Data(int int_tipo_ususarios_idfk){
        int_ventas intVentas = new int_ventas();
        try{
            Statement stm = this.dbConn.createStatement();
            ResultSet rs = stm.executeQuery("select total_venta, estado_pago from int_ventas where int_tipos_usuarios_id="+int_tipo_ususarios_idfk);
            while (rs.next()) {
                intVentas.setValorVenta(rs.getDouble("total_venta"));
                intVentas.setStado(rs.getString("estado_pago"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return intVentas;
    }
    
    /**
     * Remove datas of int_ventas table
     * @param idpk
     * @return 
     */
    public int deleteData(int idpk) {
        int flag = 0;
        try {
            Statement stm = this.dbConn.createStatement();
            flag = stm.executeUpdate("delete from int_proceso_vars_data where id=" + idpk);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }
    
    
}
