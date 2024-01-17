/**
 * Metodo para poder traer toda la informacion
 * del cliente
 */
package com.modeloDAO;

import com.config.Conexion;
import com.modelo.Carrito;
import com.modelo.Compra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    int r = 0;

    public int GenerarCompra(Compra compra) {

        int idcompras;
        String sql = "insert into compras(idCliente,FechaCompras,Monto,Estado,idPago)values(?,?,?,?,?)";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, compra.getCliente().getId());
            ps.setString(2, compra.getFecha());
            ps.setDouble(3, compra.getMonto());
            ps.setString(4, compra.getEstado());
            ps.setInt(5, compra.getIdpago());
            ps.executeUpdate();

            sql = "Select @@IDENTITY AS idCompras";
            rs = ps.executeQuery(sql);
            rs.next();
            idcompras = rs.getInt("idCompras");
            rs.close();

            for (Carrito detalle : compra.getDetallecompras()) {
                sql = "insert into detalle_compras(idProducto,idCompras,Cantidad,PrecioCompra)values(?,?,?,?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, detalle.getIdProducto());
                ps.setInt(2, idcompras);
                ps.setInt(3, detalle.getCantidad());
                ps.setDouble(4, detalle.getPrecioCompra());
                r = ps.executeUpdate();

            }

        } catch (Exception e) {
        }
        return r;
    }

        //listar compras
    public List listarC(){
        
        List<Compra> compras = new ArrayList<>();
        String sql = "select * from compras";   //seleccionar todo de la tabla compras
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            //ejecutamos query osea sql
            rs = ps.executeQuery();
            
            //shile para recorrer la tabla
            while (rs.next()) {
                //creamos un objeto de tipo compra para almacenar las variables
                //en donde Compra.java se declaro con atributos que se van a utilizar
                Compra c = new Compra();
                
                c.setId(rs.getInt(1));
                c.setFecha(rs.getString(4));
                c.setMonto(rs.getDouble(5));
                c.setEstado(rs.getString(6));
                //agregamos a la lista
                compras.add(c);
            }
        
        }catch(Exception e){
             e.printStackTrace(); // or log the exception
        }
        
        return compras;
        
    }
}
