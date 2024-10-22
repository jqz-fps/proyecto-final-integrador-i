<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="pe.edu.utp.integradori.proyectofinal.model.Trabajador" %>
<%@ page import="pe.edu.utp.integradori.proyectofinal.model.Rol" %>
<%
    if (session == null || session.getAttribute("usuario") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    Trabajador usuario = (Trabajador) session.getAttribute("usuario");

    boolean esSupervisor = false;
    for (Rol rol : usuario.getRoles()) {
        if (rol == Rol.Supervisor) {
            esSupervisor = true;
            break;
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Ventas | Líder Médica</title>
    <link rel="icon" href="${pageContext.request.contextPath}/static/icon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap-icons/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/dashboard.css">
</head>
<body>

<div class="parent">
    <div class="sidebar-grid">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/static/images/logo-row.png" class="container logo" alt="logo">
        </div>
        <br>
        <div class="sidebar">
            <div class="radio checked">
                <a href="sells" class="sidebar-label">
                    <i class="bi bi-receipt"></i>
                    <span>Ventas</span>
                </a>
            </div>
            <div class="radio">
                <a href="products" class="sidebar-label">
                    <i class="bi bi-box"></i>
                    <span>Productos</span>
                </a>
            </div>
            <div class="radio">
                <a href="labs" class="sidebar-label">
                    <i class="bi bi-shop"></i>
                    <span>Laboratorios</span>
                </a>
            </div>
            <div class="radio">
                <input type="radio" class="sidebar-radio" id="sidebar-categories" name="sidebar-radio-group">
                <a href="categories" class="sidebar-label">
                    <i class="bi bi-diagram-3"></i>
                    <span>Categorías</span>
                </a>
            </div>
            <div class="radio">
                <a href="distributors" class="sidebar-label">
                    <i class="bi bi-truck"></i>
                    <span>Distribuidoras</span>
                </a>
            </div>
            <c:if test="${usuario != null && usuario.hasRole('Supervisor')}">
                <div class="radio">
                    <a href="supervition/workers" class="sidebar-label">
                        <i class="bi bi-person-fill-lock"></i>
                        <span>Panel de supervisores</span>
                    </a>
                </div>
            </c:if>
            <div class="radio" data-bs-toggle="modal" data-bs-target="#sessionExitModal">
                <label class="sidebar-label"
                       style="--background-secondary: #fa5050; --color-primary: #161616">
                    <i class="bi bi-box-arrow-right"></i>
                    <span>Cerrar Sesión</span>
                </label>
            </div>
            <div class="modal fade" id="sessionExitModal" tabindex="-1" aria-labelledby="sessionExitModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exitModalLabel">¿Estás seguro de salir?</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p>¿Estás seguro de cerrar sesión?</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-tertiary" data-bs-dismiss="modal" onclick="document.getElementById('logout-form').submit();">Cerrar Sesión</button>
                            <form id="logout-form" action="${pageContext.request.contextPath}/logout" method="post" style="display:none;">
                                <input type="hidden" name="_method" value="POST">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div style="padding: 20px;display: flex;gap: 20px;align-items: center;justify-content: center;overflow: hidden;">
            <i class="bi bi-person-circle" style="font-size: 50px;"></i>
            Bienvenido <br>
            <%= usuario.getAp_paterno() %>, <%= usuario.getNombres() %>
        </div>
    </div>
    <div class="dashboard">

        <div class="header">
            <div class="left">
                <h3>Ventas</h3>
                <div class="search-container">
                    <i class="bi bi-search"></i>
                    <input autocomplete="off" type="text" placeholder="Buscar..." class="form-control" (input)="applyFilter($event)">
                </div>
            </div>
            <div class="right">
                <% if (esSupervisor) { %>
                    <form action="${pageContext.request.contextPath}/dashboard/sells/download" method="post">
                        <button class="btn btn-tertiary" type="submit"><i class="bi bi-download"></i> Descargar XLSX</button>
                    </form>
                <% } %>
                <button class="btn btn-tertiary" data-bs-toggle="modal" data-bs-target="#addModal"><i class="bi bi-plus"></i> Venta</button>
            </div>
        </div>
        <c:if test="${not empty sessionScope.errorDNI}">
            ${sessionScope.errorDNI}
            <script>
                setTimeout(function() {
                    var alert = document.querySelector('.alert');
                    if (alert) {
                        alert.remove();
                    }
                }, 5000);
            </script>
            <%
                session.removeAttribute("errorDNI");
            %>
        </c:if>
        <div>
            <table>
                <thead>
                <tr>
                    <td>ID</td>
                    <td>FECHA</td>
                    <td>VENDEDOR</td>
                    <td>DNI COMPRADOR</td>
                    <td>TOTAL</td>
                    <td></td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="venta" items="${ventasCargadas}">
                    <tr>
                        <td>${venta.id}</td>
                        <td>${venta.getFechaFormateada()}</td>
                        <td>${venta.vendedor.getNombres()} ${venta.vendedor.getAp_paterno()}</td>
                        <td>${venta.dni_comprador}</td>
                        <td>${venta.getTotal()}</td>
                        <td>
                            <form action="sells/details" method="post">
                                <input type="text" style="display: none;" name="idVenta" value="${venta.id}">
                                <button class="btn btn-secondary btn-sm" type="submit"><i class="bi bi-list-ol"></i> Detalles</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addModalLabel">Crear nueva venta</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form method="post">
                        <div class="modal-body">
                            <div class="row">
                                <div class="group col">
                                    <label for="dnicomprador" class="form-label oblig">DNI del comprador</label>
                                    <input autocomplete="off" type="text" class="form-control" name="dnicomprador" id="dnicomprador" placeholder="DNI" required>
                                </div>
                            </div><br>
                            <h5>Productos:</h5>
                            <div class="modal-products" style="max-height: 190px;overflow-y: scroll;overflow-x:hidden">
                                <div class="product-row">
                                    <label class="oblig">Producto 1:</label>
                                    <div class="row">
                                        <div class="group col-md-8">
                                            <select name="idp1" id="idp1" class="form-control" required>
                                                <c:forEach var="producto" items="${productosCargados}">
                                                    <option value="${producto.id}">${producto.getId()} - ${producto.getNombre()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="group col">
                                            <input type="number" name="cantidadp1" id="cantidadp1" class="form-control" placeholder="Cantidad" min="0" required>
                                        </div>
                                    </div>
                                </div>
                                <style>
                                    .product-row {
                                        margin-top: 20px;
                                    }
                                </style>
                                <script>
                                    let prodActual = 1;
                                    
                                    function addProductRow() {
                                        prodActual++;
                                        const productos = `
                                        <div class="product-row">
                                            <label class="oblig">Producto ` + prodActual + `:</label>
                                            <div class="row">
                                                <div class="group col-md-8">
                                                    <select name="idp` + prodActual + `" id="idp` + prodActual + `" class="form-control" required>
                                                        <c:forEach var="producto" items="${productosCargados}">
                                                            <option value="${producto.id}">${producto.id} - ${producto.nombre}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="group col">
                                                    <input type="number" name="cantidadp` + prodActual + `" id="cantidadp` + prodActual + `" class="form-control" placeholder="Cantidad" min="0" required>
                                                </div>
                                            </div>
                                        </div>`;
                                        
                                        document.querySelector('.modal-products').innerHTML += productos;
                                    }
                                
                                    function removeLastRow() {
                                        const productRows = document.querySelectorAll('.modal-products .product-row');
                                        if (productRows.length > 1) {
                                            productRows[productRows.length - 1].remove();
                                            prodActual--;
                                        }
                                    }
                                </script>
                            </div><br>
                            <div class="row">
                                <div class="col-md-6">
                                    <button type="button" class="btn btn-tertiary" style="border-radius: 50%;" onclick="removeLastRow()">-</button>
                                </div>
                                <div class="col-md-6" style="display: flex;justify-content: end;">
                                    <button type="button" class="btn btn-tertiary" style="border-radius: 50%;" onclick="addProductRow()">+</button>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <button type="submit" class="btn btn-tertiary">Guardar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>