/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ShopDao;
import java.util.List;
/**
 *
 * @author ajota
 */
public interface int_ventasDao {
    
    public int set_Data(int int_tipo_usuarios_idfk,double total_venta, String estado);
    
    public int_ventas get_Data(int int_tipo_usuarios_idfk);
    
    public int deleteData(int idpk);
}
