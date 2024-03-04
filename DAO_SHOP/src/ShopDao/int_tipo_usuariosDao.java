/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ShopDao;

/**
 *
 * @author ajota
 */
public interface int_tipo_usuariosDao {
   
    public int set_Data(int int_usuario_idfk, String descripcion, String direccion);
    
    public int_tipo_usuarios get_Data(int int_tipos_usuarios_idfk);
    
    public int deleteData(int idpk);
}
