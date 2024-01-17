<%@page import="java.io.PrintWriter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Historial</title
        <!-- Bosstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">    
    </head>
    <body>

        <!--Especificacion de cookies-->
        <%
            String nCuenta = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("Cuenta.cookie")) {
                        nCuenta = cookies[i].getValue();
                        break;
                    }
                }
            }
            Integer conteo = null;
            if (nCuenta == null) {
                conteo = 1;
            } else {
                conteo = Integer.parseInt(nCuenta) + 1;
            }
            Cookie newCookie = new Cookie("Cuenta.cookie", conteo.toString());
            response.addCookie(newCookie);
        %>
        <!-- barra de nav -->
        <nav class="navbar navbar-expand-lg text-red-100" style="background-color: #FF1520;">
            <div class="container-fluid" href="Controlador?accion=home">
                <a class = "navbar-brand">
                    <img src="img/tienda.png" alt="logo" width="30" height="30">
                </a>
                <a class="navbar-brand" href="Controlador?accion=home">Papa Pizzeria</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="Controlador?accion=home">Inicio</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Controlador?accion=home">Seguir Comprando</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Controlador?accion=Carrito"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart" viewBox="0 0 16 16">
                                <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5M3.102 4l1.313 7h8.17l1.313-7zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4m7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4m-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2m7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2"/>
                                </svg>(<label style="color: orange">${contador}</label>)Carrito</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link">
                                Has visitado esta página: <span style="color: orange"><%= conteo%></span> veces
                            </a>
                        </li>
                    </ul>
                    <nav class="navbar" style="background-color: #FF1520;">
                        <form class="container-fluid justify-content-start" action="Inicio.jsp" method="post">
                            <button class="btn btn-outline-success me-2" type="submit">Iniciar Sesión</button>
                        </form>
                    </nav>
                </div>
            </div>
        </nav>
        <!-- /barra de nav -->

        <!-- Tabla de carrito -->
        <div class="d-flex align-items-center justify-content-center vh-100">
            <div class="col-sm-8">
                <table class="table table-hover">
                    <thead class="table-success">
                        <tr style="background-color: #4E7F1D;">
                            <th>ID COMPRA</th>
                            <th>FECHA</th>
                            <th>MONTO</th>
                            <th>ESTADO</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="his" items="${compras}">
                            <tr>
                                <td>${his.getId()}</td>
                                <td>${his.getFecha()}</td>
                                <td>${his.getMonto()}</td>
                                <td>${his.getEstado()}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Boostrap
                <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
                <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
    </body>
</html>
