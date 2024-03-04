/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShopDao;

/**
 *
 * @author ajota
 */
public class int_ventas {
    /**
    *Field id, primary key.
    */
    private int idpk;
    /**
     * Field id from int_tipo_uasrios table, foreing key
     */
    private int int_tipo_usuarios_idfk;
    /**
     * Field value total
     */
    private double total_venta;
    /**
     * Field state 
     */
    private String estado;
    
    public int getIdpk (){
        return this.idpk;
    }
    
    public void setIdpk(int idpk){
        this.idpk=idpk;
    }
    
    public int getIdTiposUsuarios(){
        return this.int_tipo_usuarios_idfk;
    }
    
    public void setIdTiposUsuarios(int int_tipo_usuarios_idfk){
        this.int_tipo_usuarios_idfk=int_tipo_usuarios_idfk;
    }
    
    public double getValorVenta(){
        return this.total_venta;
    }
    
    public void setValorVenta(double total_venta){
        this.total_venta=total_venta;
    }
    
    public String getStado (){
        return this.estado;
    }
    
    public void setStado(String estado){
        this.estado=estado;
    }
}
