/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShopDao;


import java.sql.*;
/**
 *
 * @author ajota
 */
public class int_tipo_usuariosIMP implements int_tipo_usuariosDao {
     public Connection dbConn = null;
     
     public int_tipo_usuariosIMP(Connection dbConn) {
        this.dbConn = dbConn;
    }

    @Override
    public int set_Data(int int_usuario_idfk, String descripcion, String direccion) {
        int resRows = 0;
        try{
            PreparedStatement stm = dbConn.prepareStatement("INSERT INTO int_tipos_usuario SET descripcion=?, direccion=?, int_usuario_id=?");
            stm.setString(1, descripcion);
            stm.setString(2, direccion);
            stm.setInt(3,int_usuario_idfk);
            stm.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
        return resRows;
    }

    @Override
    public int_tipo_usuarios get_Data(int int_tipos_usuarios_idfk) {
        int_tipo_usuarios intTipoUsuarios = new int_tipo_usuarios();
        try{
            Statement stm = this.dbConn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT descripcion, direccion FROM int_tipo_usuarios where int_tipo_usuarios_id="+int_tipos_usuarios_idfk);
            while(rs.next()){
                intTipoUsuarios.setDescripcion(rs.getString("descripcion"));
                intTipoUsuarios.setDireccion(rs.getString("direccion"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return intTipoUsuarios;
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
