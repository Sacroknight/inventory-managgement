/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShopDao;

/**
 *
 * @author ajota
 */
public class int_tipo_usuarios {
      /**
       *Field id, primary key.
      */
    private int idpk;
    /**
    * Field int_usuarios_tipo_id, foreign key of table int_usuarios
    */
    private int int_usuario_id_fk;
    
    /**
     * Field descripción
     */
    private String descripcion;
    /**
     * Field dirección
     */
    private String direccion;
    
     /**
     * get ID of int_tipo_usuario table.
     * 
     * @return ID  int_proceso.
     */
    public int getId() {
        return this.idpk;
    }

    /**
     *set ID int_tipo_usuario table.
     * 
     * @param id new ID  to set.
     */
    public void setId(int id) {
        this.idpk = id;
    }
    /**
     * get ID of the type of process associated with int_usuarios table.
     * 
     * @return  ID type process.
     */
    public int getIdUser() {
        return this.int_usuario_id_fk;
    }

    /**
     * set ID of the type of process associated with int_usuarios table.
     * 
     * @param id new ID to set.
     */
    public void setIdUser(int id) {
        this.int_usuario_id_fk = id;
    }
   
    /**
     * Set descripción of user
     * @param descripción 
     */
    public void setDescripcion(String descripción){
        this.descripcion=descripcion;
    }
    
    public void setDireccion(String direccion){
        this.direccion=direccion;
    }
}
