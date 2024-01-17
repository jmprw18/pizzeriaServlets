package com.controlador;

import com.modeloDAO.Registro;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InicioSesion extends HttpServlet {

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

        HttpSession sesion = request.getSession();

        Registro metodos = new Registro();
        String txtUsuario = request.getParameter("txtUsuario");
        String txtContrasena = request.getParameter("txtContrasena");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");
        out.println("<script type=\"text/javascript\">");

        boolean iniciarSesion = metodos.buscarUsuarioInicioSesion(txtUsuario, txtContrasena);

        if (iniciarSesion) { // EL usuario puede acceder porque está registrado
            String nombre = metodos.buscarNombre(txtUsuario);
            // Muestra un mensaje de bienvenida usando JavaScript
            out.println("alert('¡Bienvenido a mi página!\\nIniciaste sesión como: " + nombre + "')");
            // Redirige a la página de inicio
            out.println("location = 'paginaInicio.jsp'");

            // Almacena el nombre y el usuario en la sesión
            sesion.setAttribute("nombre", nombre);
            sesion.setAttribute("txtUsuario", txtUsuario);
        } else {
            // Muestra un mensaje de error usando JavaScript
            out.println("alert('Datos Incorrectos, verifica tus credenciales o date de alta en el sistema')");
            // Redirige de nuevo a la página de inicio de sesión
            out.println("location = 'index.html'");
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
