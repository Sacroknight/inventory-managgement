/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ShopDao;

import java.util.List;

/**
 * Interface corresponding to the int_usuarios table defining all the operations to be performed on
 * the int_usuarios table. It includes: listing all users, getting a particular user, inserting a new
 * user, updating data of one user, and deleting a particular user.
 * 
 * @author Anthony  Correa
 * @author Luis Manzano
 * @author Daniel Gonzales
 * @author Alejandro Fajardo
 */
public interface int_usuarioDao {
        public List<int_usuario> getAllUsers();
	public int_usuario getUser(int idUser);
        public int_usuario getUserByNameAndPass(String user, String password);
        public int_usuario getUserByEmailAndPass(String email, String password);
	//public int_usuarios getUser(String username, String password, boolean isNameSearch);
	public int insertUser(String user,String name, String apellido, String email, String clave);
	public int updateUser(int_usuario user2Update);
	public int deleteUser(int_usuario userDB);
	public int deleteUser(int userID);
}
