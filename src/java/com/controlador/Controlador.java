/*
 * Controlador
 */
package com.controlador;

import com.config.Fecha;
import com.modelo.Carrito;
import com.modelo.Cliente;
import com.modelo.Compra;
import com.modelo.Producto;
import com.modeloDAO.CompraDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.modeloDAO.productoDAO;
import java.util.ArrayList;
import java.util.List;

public class Controlador extends HttpServlet {

    //declaramos metodos de traer informacion
    productoDAO pdao = new productoDAO();
    CompraDAO cdao = new CompraDAO();
    
    //declaramos las clases con los atributos
    Producto p = new Producto();
    Compra c = new Compra();
    
    
    //crear lista para recorrer toda la tabla
    List<Producto> productos = new ArrayList<>();
    List<Compra> compras = new ArrayList<>();
    
    //recorrer productos seleccionados
    List<Carrito> listaCarrito = new ArrayList();
    
    
    int item;
    double totalPagar = 0.0;
    int cantidad = 1;

    int ipd;
    Carrito car;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        //llamamos a la funcion listar
        productos = pdao.listar();
        compras = cdao.listarC();
        
        
        switch (accion) {
            case "Comprar":
                totalPagar = 0;
                //busqueda del id del producto
                int idp = Integer.parseInt(request.getParameter("id"));
                p = pdao.listaId(idp);
                //sumatoria de todos los productos
                item = item + 1;
                Carrito car = new Carrito();
                car.setItem(item);
                car.setIdProducto(p.getId());
                car.setNombres(p.getNombres());
                car.setDescripcion(p.getDescripcion());
                car.setPrecioCompra(p.getPrecio());
                car.setCantidad(cantidad);
                car.setSubTotal(cantidad * p.getPrecio());
                //almacena todos los productos que el usuario ha agregado
                listaCarrito.add(car);
                for (int i = 0; i < listaCarrito.size(); i++) {
                    totalPagar = totalPagar + listaCarrito.get(i).getSubTotal();
                }

                request.setAttribute("totalPagar", totalPagar);
                request.setAttribute("carrito", listaCarrito);
                request.setAttribute("contador", listaCarrito.size());
                request.getRequestDispatcher("carrito.jsp").forward(request, response);
                break;
            case "AgregarCarrito":
                //posicion del podructo
                int pos = 0;
                cantidad = 1;

                //busqueda del id del producto
                idp = Integer.parseInt(request.getParameter("id"));
                p = pdao.listaId(idp);

                //recorrido para verificar si hay productos iguales dentro de la lista carrito
                if (listaCarrito.size() > 0) {
                    for (int i = 0; i < listaCarrito.size(); i++) {
                        if (idp == listaCarrito.get(i).getIdProducto()) {
                            pos = i;
                        }
                    }
                    if (idp == listaCarrito.get(pos).getIdProducto()) {
                        cantidad = listaCarrito.get(pos).getCantidad() + cantidad;
                        double subtotal = listaCarrito.get(pos).getPrecioCompra() * cantidad;
                        listaCarrito.get(pos).setCantidad(cantidad);
                        listaCarrito.get(pos).setSubTotal(subtotal);
                    } else {
                        //sumatoria de todos los productos
                        item = item + 1;

                        car = new Carrito();

                        car.setItem(item);
                        car.setIdProducto(p.getId());
                        car.setNombres(p.getNombres());
                        car.setDescripcion(p.getDescripcion());
                        car.setPrecioCompra(p.getPrecio());
                        car.setCantidad(cantidad);
                        car.setSubTotal(cantidad * p.getPrecio());
                        listaCarrito.add(car);
                    }
                } else {
                    //sumatoria de todos los productos
                    item = item + 1;

                    car = new Carrito();

                    car.setItem(item);
                    car.setIdProducto(p.getId());
                    car.setNombres(p.getNombres());
                    car.setDescripcion(p.getDescripcion());
                    car.setPrecioCompra(p.getPrecio());
                    car.setCantidad(cantidad);
                    car.setSubTotal(cantidad * p.getPrecio());
                    listaCarrito.add(car);
                }

                request.setAttribute("contador", listaCarrito.size());
                request.getRequestDispatcher("Controlador?accion=home").forward(request, response);
                break;
            case "Delete":
                int idproducto = Integer.parseInt(request.getParameter("idp"));
                //buscar id de la lista para eliminar
                for (int i = 0; i < listaCarrito.size(); i++) {
                    if (listaCarrito.get(i).getItem() == idproducto) {
                        listaCarrito.remove(i);
                    }
                }
                request.getRequestDispatcher("Controlador?accion=Carrito").forward(request, response);
                break;

            /**
             * case "ActualizarCantidad": int idpro =
             * Integer.parseInt(request.getParameter("idp")); int cant =
             * Integer.parseInt(request.getParameter(accion)); for (int i = 0; i
             * < listaCarrito.size(); i++) { if
             * (listaCarrito.get(i).getIdProducto() == idpro) {
             * listaCarrito.get(i).setCantidad(cant); //multiplicamos por la
             * nueva cantidad double st = listaCarrito.get(i).getPrecioCompra()
             * * cant; listaCarrito.get(i).setSubTotal(st); } } break;
             */
            case "GenerarCompra":
                Cliente cliente = new Cliente();
                cliente.setId(1);

                CompraDAO dao = new CompraDAO();
                Compra compra = new Compra(cliente, 1, Fecha.FechaBD(), totalPagar, "Aceptado", listaCarrito);
                int res = dao.GenerarCompra(compra);

                if (res != 0 && totalPagar > 0) {
                    
                    request.getRequestDispatcher("vistas/mensaje.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("vistas/error.jsp").forward(request, response);

                }

                break;
            case "Carrito":
                totalPagar = 0.0;
                //enviamos todos los atributos de la lista carrito
                request.setAttribute("carrito", listaCarrito);
                for (int i = 0; i < listaCarrito.size(); i++) {
                    totalPagar = totalPagar + listaCarrito.get(i).getSubTotal();
                }
                request.setAttribute("totalPagar", totalPagar);
                request.getRequestDispatcher("carrito.jsp").forward(request, response);
                break;
            case "Historial":
                request.setAttribute("compras", compras);
                request.getRequestDispatcher("historial.jsp").forward(request, response);
                break;
            default:
                //listar todos los productos
                request.setAttribute("productos", productos);
                //cargar toda a la ventana
                request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
