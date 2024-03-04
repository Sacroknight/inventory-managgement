/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShopDao;

/**
 *
 * @author ajota
 */
public class int_producto {
    
    private int idpk;
    
    private int int_categoria_id_fk;
    
    private String nombre_producto;
    
    private double precio;
    
    private int stock;
    
    private String descripcion;
    
    private byte[] imagen;
    
    
    public int getIdpk(){
        return idpk;
    }
    
    public void setIdpk(int idpk){
        this.idpk=idpk;
    }
    
    public int getIntCategoriaId(){
        return int_categoria_id_fk;
    }
    
    public void setIntCategoriaId(int int_categoria_idfk){
        this.int_categoria_id_fk=int_categoria_idfk;
    }
    
    public String getNombre(){
        return nombre_producto;
    }
    
    public void setNombre(String nombre_producto){
        this.nombre_producto=nombre_producto;
    }
    
    public double getPrecio(){
        return precio;
    }
    
    public void setPrecio(double precio){
        this.precio=precio;
    }
    
    public int getStock(){
        return stock;
    }
    
    public void setStock(int stock){
        this.stock=stock;
    }
    
    public String getDescripción(){
        return descripcion;
    }
    
    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }
    
    public void setImagen (byte[] imagen){
        this.imagen=imagen;
    }
    public byte[] getImagen() {
        return this.imagen;
    }
}
