/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ShopDao;

/**
 *
 * @author ajota
 */
public interface int_detalles_ventaDao {
    public int set_Data(int int_producto_idfk,int int_ventas_idfk,int cantidad, double precio_unitario);
    
    public int_detalles_venta get_Data(int int_producto_idfk,int int_ventas_idfk);
    
    public int deleteData(int idpk);
}
