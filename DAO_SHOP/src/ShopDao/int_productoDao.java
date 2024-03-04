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
public interface int_productoDao {
    
    
    int_producto getProducto(int idProducto);
    //public List<int_producto> getNdata(int int_categoria_idfk);
    List<int_producto> getAllData(int int_categoria_idfk);
    public int setProducto(int int_categoria_idfk,float precio, String nombre, int stock, String descripcion);

    int setProducto(int int_categoria_idfk, float precio, String nombre, int stock, String descripcion, byte[] imagen);

    public int deleteProducto(int productID);
    int actualizarStock(String var1, String var2, int var3);
    byte[] getImg(int productID); 
        int updateProducto(String nombre, int int_categoria_idfk, float precio, String nuevoNombre, int stock, String descripcion, byte[] imagen);
}
