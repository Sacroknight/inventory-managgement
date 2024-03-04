/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShopDao;

/**
 *
 * @author ajota
 */
public class int_detalles_venta {
    
    private int idpk;
    
    private int int_producto_idfk;
    
    private int int_ventas_idfk;
    
    private int cantidad;
    
    private double precio_unitario;
    
    public int getIdpk(){
        return idpk;
    }
    
    public void setIdpk(int idpk){
        this.idpk=idpk;
    }
    
    public int getIdproducto(){
        return int_producto_idfk;
    }
    
    public void setIdproducto(int int_producto_idfk){
        this.int_producto_idfk=int_producto_idfk;
    }
    
    public int getIdventas(){
        return int_ventas_idfk;
    }
    
    public void setIdventas(int int_ventas_idfk){
        this.int_ventas_idfk=int_ventas_idfk;
    }
    
    public int getCantidad(){
        return cantidad;
    }
    
    public void setCantidad(int cantidad){
        this.cantidad=cantidad;
    }
    
    public double getPrecioUnitario(){
        return precio_unitario;
    }
    
    public void setPrecioUnitario(double precio_unitario){
        this.precio_unitario=precio_unitario;
    }
}
