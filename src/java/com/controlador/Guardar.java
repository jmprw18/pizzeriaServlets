package com.controlador;

import com.modelo.Cliente;
import com.modeloDAO.Registro;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Guardar extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    
    Registro metodos = new Registro();
    
    String txtNombre = request.getParameter("txtNombre");
    String txtApellidos = request.getParameter("txtApellidos");
    String txtContrasena = request.getParameter("txtContrasena");
    String txtCorreo = request.getParameter("txtCorreo");

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<body>");
    out.println("<script type=\"text/javascript\">");

    // Verificar si el correo está repetido en la base de datos
    boolean correoRepetido = metodos.buscarUsuarioRepetidoBD(txtCorreo);

    if (correoRepetido) { // El correo ya está registrado en la BD
        out.println("alert('¡Atención!, El correo ya está registrado en la BD')");
        out.println("location='index.html'");
    } else {
        // Crear un objeto Cliente con los datos del formulario
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(txtNombre);
        nuevoCliente.setDireccion(txtApellidos);
        nuevoCliente.setCorreo(txtCorreo);
        nuevoCliente.setPassword(txtContrasena);

        // Registrar al nuevo cliente en la base de datos
        boolean registroExitoso = metodos.registrarUsuario(nuevoCliente);

        if (registroExitoso) {
            out.println("alert('El usuario se ha registrado con éxito :) ')");
            out.println("location='index.html'");
        } else {
            out.println("alert('ERROR, el usuario no se ha registrado :( ')");
            out.println("location='index.html'");
        }
    }

    out.println("</script>");
    out.println("</body>");
    out.println("</html>");

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
