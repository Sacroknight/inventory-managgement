/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ShopDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

 /**
 * The int_usuarioDAOImp class implements the int_usuarioDAO interface in order to implement
 * all the methods to handle the int_usuario table. 
 *  
 * @author Anthony  Correa
 * @author Luis Manzano
 * @author Daniel Gonzales
 * @author Alejandro Fajardo
 */
public class int_usuarioDaoImp implements int_usuarioDao{
     /**
	 * List of all users in the int_usuario table.
	 */
	private List<int_usuario> Users;
	/**
	 * dbConn is an instance of the database connection.
	 */
	public Connection dbConn = null; 	
	
	/**
	 * Constructor of int_usuarioDAOImp class
	 * 
	 * @param dbConn Database connection.
	 */
	public int_usuarioDaoImp (Connection dbConn)
	{
		this.Users = new ArrayList<int_usuario>();
		this.dbConn = dbConn;
		
		try
		{
			Statement stm = this.dbConn.createStatement();
			ResultSet rs = stm.executeQuery("select id, usuario nombre, apellido, email, clave from int_usuario");
			while (rs.next())
			{
				int_usuario intUser = new int_usuario(rs.getString("usuario"),rs.getString("nombre"), rs.getString("apellido"));
				intUser.setClave(rs.getString("clave"));
				intUser.setEmail(rs.getString("email"));
				
				this.Users.add(intUser);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This method gets an instance of int_usuario class corresponding to a particular registry of the 
	 * int_usuario table.
	 * 
	 * @param idUser Id of the user to get from the int_usuario table.
	 */
	public int_usuario getUser(int idUser) {
    int_usuario resUser = null;

    try {
        Statement stm = this.dbConn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT id, usuario, nombre, apellido, email, clave FROM int_usuario WHERE id=" + idUser);

        while (rs.next()) {
            resUser = new int_usuario(rs.getString("usuario"), rs.getString("nombre"), rs.getString("apellido"));
            resUser.setId(rs.getInt("id"));
            resUser.setUsuario(rs.getString("usuario"));
            resUser.setNombre(rs.getString("nombre"));
            resUser.setApellido(rs.getString("apellido"));
            resUser.setEmail(rs.getString("email"));
            resUser.setClave(rs.getString("clave"));
        }
    } catch (Exception var5) {
        var5.printStackTrace();
    }

    return resUser;
}
	
	///////////////////////////////////modificando esta area de abajo
        /**
        * Retrieves an instance of the int_usuario class corresponding to a particular record
        * in the int_usuario table using the specified names, last names, and password.
        *
        * @param names      String representing the names of the user to retrieve from the int_usuario table.
        * @param lastNames  String representing the last names of the user to retrieve from the int_usuario table.
        * @param password   String representing the password of the user to retrieve from the int_usuario table.
        * @return An instance of int_usuario if a matching record is found, otherwise null.
        */
        
        @Override
     public int_usuario getUserByNameAndPass(String user, String password) {
        int_usuario resUser = null;

        try {
            Statement stm = this.dbConn.createStatement();
            ResultSet rs = stm.executeQuery("select id, usuario, nombre, apellido, email, clave from int_usuario where usuario='"+user+"' AND clave='"+password+"'");
            while(rs.next()) {
                resUser = new int_usuario(rs.getString("usuario"),rs.getString("nombre"), rs.getString("apellido"));
                resUser.setClave(rs.getString("clave"));
                resUser.setEmail(rs.getString("email"));
                resUser.setId(rs.getInt("id"));
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resUser;
    }
     /**
    * Retrieves an instance of the int_usuario class corresponding to a particular record
    * in the int_usuario table using the specified email and password.
    *
    * @param email      String representing the email of the user to retrieve from the int_usuario table.
    * @param password   String representing the password of the user to retrieve from the int_usuario table.
    * @return An instance of int_usuario if a matching record is found, otherwise null.
    */
    @Override
    public int_usuario getUserByEmailAndPass(String email, String password) {
        int_usuario resUser = null;

        try {
            Statement stm = this.dbConn.createStatement();
            ResultSet rs = stm.executeQuery("select id, usuario, nombre, apellido, email, clave from int_usuario where email='"+email+"' AND clave='"+password+"'");
            while(rs.next()) {
                resUser = new int_usuario(rs.getString("usuario"),rs.getString("nombre"), rs.getString("apellido"));
                resUser.setClave(rs.getString("clave"));
                resUser.setEmail(rs.getString("email"));
                resUser.setId(rs.getInt("id"));
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resUser;
    }
        
        ///////////////////////////////////modificando esta area de arriba	
	/**
	 * This method gets an updated list of all users saved on the int_usuario table.
	 * 
	 * @return List object with all users of int_usuario table.
	 */
	public List<int_usuario> getAllUsers()
	{
		this.Users.clear();
		
		try
		{
			Statement stm = this.dbConn.createStatement();
			ResultSet rs = stm.executeQuery("select id, usuario, nombre, apellido, email, clave from int_usuario");
			while (rs.next())
			{
				int_usuario intUser = new int_usuario(rs.getString("usuario"),rs.getString("nombre"), rs.getString("apellido"));
				intUser.setClave(rs.getString("clave"));
				intUser.setEmail(rs.getString("email"));
				intUser.setId(rs.getInt("id"));
				
				this.Users.add(intUser);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return this.Users;
	}
	
	/**
	 * This method inserts a new user into the int_usuario table.
	 * 
	 * @param name String object with the user name.
	 * @param apellido String object with the user last name.
	 * @param email String object with the email of the user.
	 * @param clave String object with the password of the user.
	 * @param int_usuario_tipo_id Integer value corresponding to the user profile in the int_usuario_tipo table.
	 */
        @Override
	public int insertUser(String user,String name, String apellido, String email, String clave)
	{
		int resRows = 0;
		
		try
		{
			Statement stm = this.dbConn.createStatement();
//			resRows = stm.executeUpdate("insert into int_usuario set usuario='"+user+"', nombre='"+name+"', apellido='"+apellido+"', email='"+email+"', clave=PASSWORD('"+clave);
			resRows = stm.executeUpdate("INSERT INTO int_usuario (usuario, nombre, apellido, email, clave) VALUES ('" + user + "', '" + name + "', '" + apellido + "', '" + email + "', '" + clave + "')");

			int_usuario newUser = new int_usuario(user,name, apellido);
			this.Users.add(newUser);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return resRows;
	}
	
	/**
	 * This method updates a registry of int_usuario table using an instance of int_usuario class.
	 * 
	 * @param user2Update int_usuario object where all information of the user is saved.
	 */
        @Override
	public int updateUser(int_usuario user2Update)
	{
		int resRows = 0;
		
		try
		{
			Statement stm = this.dbConn.createStatement();
			resRows = stm.executeUpdate("update int_usuario set nombre='"+user2Update.getNombre()+"', apellido='"+user2Update.getApellido()+"', email='"+user2Update.getEmail()+"', clave=PASSWORD("+user2Update.getClave()+"),  where id="+user2Update.getId());
			
			int_usuario newUser = new int_usuario(user2Update.getUsuario(),user2Update.getNombre(), user2Update.getApellido());
			newUser.setEmail(user2Update.getEmail());
			newUser.setClave(user2Update.getClave());
			
			//this.Users.set(user2Update.getId(), newUser);
			
			Iterator i = this.Users.iterator();
			int usersIndex = 0;
			while (i.hasNext())
			{
				int idUserDB = ((int_usuario) i.next()).getId();
				if (idUserDB == user2Update.getId())
				{
					this.Users.remove(usersIndex);
					this.Users.set(usersIndex, user2Update);
					break;
				}
				
				usersIndex++;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return resRows;
	}
	
	/**
	 * This method deletes a registry of int_usuario table.
	 * 
	 * @param userDB int_usuario object corresponding to the user to be deleted.
	 */
        @Override
	public int deleteUser(int_usuario userDB)
	{
		int resRows = 0;
		
		try
		{
			Statement stm = this.dbConn.createStatement();
			resRows = stm.executeUpdate("delete from int_usuario where id="+userDB.getId());
			
			int idUser = this.Users.indexOf(userDB);
			this.Users.remove(idUser);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return resRows;
	}
	
	/**
	 * This method deletes a registry of int_usuario table.
	 * 
	 * @param userID ID of the corresponding to the user to be deleted.
	 */
        @Override
	public int deleteUser(int userID)
	{
		int resRows = 0;
		
		try
		{
			Statement stm = this.dbConn.createStatement();
			resRows = stm.executeUpdate("delete from int_usuario where id="+userID);
			
			Iterator i = this.Users.iterator();
			int usersIndex = 0;
			while (i.hasNext())
			{
				int idUserDB = ((int_usuario) i.next()).getId();
				if (idUserDB == userID)
				{
					this.Users.remove(usersIndex);
					break;
				}
				
				usersIndex++;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return resRows;
	}

    

    
}
