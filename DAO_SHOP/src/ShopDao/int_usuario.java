/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShopDao;

/**
* <b> Description</b>
/**
 * This class corresponds to the data model of table int_usuarios, of the database int_db_shop.
 * It also includes the set and get methods to modify the internal values.
 * 
 * @author Anthony Correa
 * @author Luis Manzano
 * @author Daniel Gonzales
 * @author Alejandro Fajardo
 */
public class int_usuario {
    /**
     * Field id, primary key.
     */
    private int id;

    /**
     * Field usuario, user name.
     */
    private String usuario;

    /**
     * Field nombre, user's name.
     */
    private String nombre;

    /**
     * Field apellido, user's last names.
     */
    private String apellido;

    /**
     * Field email, user's email.
     */
    private String email;

    /**
     * Field clave, user's password.
     */
    private String clave;

    /**
     * Constructor of the class IntUsuario.
     * 
     * @param usuario User name
     * @param nombre  User names to be initialized.
     * @param apellido User last names to be initialized.
     */
    public int_usuario (String usuario, String nombre, String apellido) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
    }   

    /**
     * To get the user id of the current instance.
     * 
     * @return Integer value with user id.
     */
    public int getId() {
        return id;
    }

    /**
     * To change the user id to the current instance.
     * 
     * @param id Integer value holding the user id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * To get the user of the current instance.
     * 
     * @return String object with user.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * To change the user to the current instance.
     * 
     * @param usuario String object holding the user names.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * To get the user names of the current instance.
     * 
     * @return String object with user names.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * To change the user names to the current instance.
     * 
     * @param nombre String object holding the user names.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * To get the user last names of the current instance.
     * 
     * @return String object with user last names.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * To change the user last names to the current instance.
     * 
     * @param apellido String object holding the user last names.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * To get the user email of the current instance.
     * 
     * @return String object with user email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * To change the user email to the current instance.
     * 
     * @param email String object holding the user email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * To get the user password of the current instance.
     * 
     * @return String object with user password.
     */
    public String getClave() {
        return clave;
    }

    /**
     * To change the user password to the current instance.
     * 
     * @param clave String object holding the user password.
     */
    public void setClave(String clave) {
        this.clave = clave;
    }
}

