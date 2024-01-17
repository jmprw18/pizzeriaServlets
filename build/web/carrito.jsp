<!--pagina de carrito de compra-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Carrito</title>

        <!-- Bosstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">

    </head>
    <body class="color: #4E7F1D">
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
                            <a class="nav-link" href="Controlador?accion=Historial" method="post">Historial de compras</a>
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
        <div class="container mt-4">
            <h3>Carrito</h3>
            <br>
            <div class="row">
                <div class="col-sm-8 table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="thead">
                            <tr>
                                <th>ITEM</th>
                                <th>NOMBRES</th>
                                <th>DESCRIPCION</th>
                                <th>PRECIO</th>
                                <th>CANTIDAD</th>
                                <th>SUBTOTAL</th>
                                <th>ACCIONES</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="car" items="${carrito}">
                                <tr>
                                    <td>${car.getItem()}</td>
                                    <td>${car.getNombres()}
                                        <img src="ControladorImg?id=${car.getIdProducto()}" width="100" height="100"/>
                                    </td>
                                    <td>${car.getDescripcion()}</td>
                                    <td>${car.getPrecioCompra()}</td>
                                    <td>
                                        <!-- comment
                                        <input type="hidden" id="idpro" value="${car.getIdProducto()}">
                                        <input type="number" id="Cantidad" value = "${car.getCantidad()}" onchange="window.location.reload(true)" class="form-control text-center" min="1">
                                        -->
                                        <input readonly="" type="number" id="Cantidad" value = "${car.getCantidad()}" class="form-control text-center">
                                    </td>
                                    <td>${car.getSubTotal()}</td>
                                    <td>
                                        <input type="hidden" id="idp" value="${car.getIdProducto()}">
                                        <a href="Controlador?accion=Delete&idp=${car.getItem()}" id="btnDelete">Eliminar</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table> 
                </div>
                <div class="col-sm-4">
                    <div class="card">
                        <div class="card-header">
                            <h3 class="d-flex align-items-center">
                                <img src="img/pizzacortada.png" widht="40" height="40" alt="pizza"/>
                                Generar Compra
                            </h3>
                        </div>
                        <div class="card-body">
                            <label>Subtotal: </label>
                            <input type="text" value="$ ${totalPagar}" readonly="" class="form-control">
                            <label>Total Pagar: </label>
                            <input type="text" value="$ ${totalPagar}" readonly="" class="form-control">
                        </div>
                        <div class="card-footer d-flex justify-content-center">
                            <a href="Controlador?accion=GenerarCompra" class="btn btn-danger btn-block">Generar Compra</a>
                        </div>
                    </div>
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
