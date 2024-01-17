package com.modeloDAO;

import com.config.Conexion;
import com.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Registro {

    Connection con; //conexion
    Conexion cn = new Conexion(); //ConexionBD.conectar
    PreparedStatement ps;   //sentenciaPreparada
    ResultSet rs;//resultado

    public boolean registrarUsuario(Cliente cliente) {
        boolean registro = false;

        try {

            con = cn.getConnection();
            String consulta = "INSERT INTO usuarios (Dni,Nombres,Direccion,Email,Password) VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(consulta);
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getCorreo());
            ps.setString(5, cliente.getPassword());

            ps.setString(1, cliente.getDni());

            int resultadoInsercion = ps.executeUpdate();

            if (resultadoInsercion > 0) {
                registro = true; // EL usuario se ah registrado
                System.out.println("Se hizo el alta del usuario");
            } else {
                registro = false; // EL usuario NO se ah registrado
                System.out.println("NO se hizo el alta del usuario");
            }

            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }

        System.out.println("Valor del registro: " + registro);
        return registro;
    }

    public boolean buscarUsuarioRepetidoBD(String dni) {
        boolean usuarioRepetido = false;
        try {
            con = cn.getConnection();
            String consulta = "SELECT dni FROM usuarios WHERE dni = ?";
            ps = con.prepareStatement(consulta);
            ps.setString(1, dni);
            rs = ps.executeQuery();

            if (rs.next()) {
                usuarioRepetido = true; // El usuario esta registrado en la BD
            } else {
                usuarioRepetido = false; // El usuario NO esta registrado en la BD
            }

            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }

        System.out.println("El valor del usuario Repetido en el METODO es: " + usuarioRepetido);
        return usuarioRepetido;
    }

    public boolean buscarUsuarioInicioSesion(String usuario, String contrasena) {
        boolean iniciarSesion = false;

        try {
            con = cn.getConnection();
            String consulta = "SELECT usuario_generado_automaticamente,contrasena FROM usuarios WHERE usuario_generado_automaticamente = ? AND contrasena = ?";
            ps = con.prepareStatement(consulta);
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            rs = ps.executeQuery();
            if (rs.next()) {
                iniciarSesion = true;//El usuario puede iniciar Sesion por que esta registrado en la BD
            } else {
                iniciarSesion = false;//El usuario NO puede iniciar sesion por que no esta registrado en la BD
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }

        System.out.println("El valor de iniciarSesion en el metodo es: " + iniciarSesion);
        return iniciarSesion;
    }

    public String buscarNombre(String usuario) {
        String nombre = null;
        try {
            con = cn.getConnection();
            String consulta = "SELECT nombre FROM usuarios WHERE usuario_generado_automaticamente = ?";
            ps = con.prepareStatement(consulta);
            ps.setString(1, usuario);
            rs = ps.executeQuery();
            if (rs.next()) {// El usuario fue encontrado y obtenemos el nombre 
                nombre = rs.getString("nombre");
            } else {
                nombre = null; //EL usuario no fue encontrado y NO obtenemos el nombre
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error" + e);
            }
        }

        System.out.println("El valor del nombre en los Metodos SQL es : " + nombre);
        return nombre;
    }

}
