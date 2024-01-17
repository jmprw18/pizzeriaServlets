<%@page import="java.io.PrintWriter"%>
<!--Pagina principal con los productos-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>PAPA Pizzeria</title>

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

        <nav class="navbar navbar-expand-lg" style="color: #ffffff; background-color: #FF1520;">
            <div class="container-fluid">
                <a class = "navbar-brand">
                    <img style="" src="img/tienda.png" alt="logo" width="30" height="30">
                </a>
                <a class="navbar-brand" href="#">Papa Pizzeria</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="Controlador?accion=home">Inicio</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Controlador?accion=Carrito"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart" viewBox="0 0 16 16">
                                <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5M3.102 4l1.313 7h8.17l1.313-7zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4m7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4m-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2m7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2"/>
                                </svg>(<label style="color: orange">${contador}</label>)Carrito</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Controlador?accion=Historial">Historial de compras</a>
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

        <!-- Ventanas de productos -->
        <div class="container mt-2 p-5">
            <div class="row d-flex">
                <c:forEach var = "p" items = "${productos}">    
                    <div class="col-sm-4 p-2">
                        <div class="card text-center align-items-center p-3">
                            <div style="object-fit: cover; width: 60%; height: 60%;">
                                <img class="img-fluid" src="ControladorImg?id=${p.getId()}"/>
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">${p.getNombres()}</h5>
                                <p class="card-text">${p.getDescripcion()}</p>
                                <p class="card-text"><small class="text-muted">$ ${p.getPrecio()}</small></p>
                            </div>
                            <div class="d-flex justify-content-around">
                                <div class="btn-group" role="group">
                                    <a href="Controlador?accion=AgregarCarrito&id=${p.getId()}" class="btn btn-outline-info btn-sm m-1">Agregar a Carrito</a>
                                    <a href="Controlador?accion=Comprar&id=${p.getId()}" class="btn btn-danger btn-sm m-1">Comprar</a>
                                </div>
                            </div>



                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>



        <!-- Boostrap -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
    </body>
</html>
