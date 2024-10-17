<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="pe.edu.utp.integradori.proyectofinal.model.Trabajador" %>
<%
    if (session == null || session.getAttribute("usuario") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    Trabajador usuario = (Trabajador) session.getAttribute("usuario");
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
                    <a href="supervition" class="sidebar-label">
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
            <%= usuario.getAp_paterno() %>, <%= usuario.getAp_materno() %>
        </div>
    </div>
    <div class="dashboard">

        <div class="header">
            <div class="left">
                <h3>Venta #${venta.id}</h3>
            </div>
            <div class="right">
                <h3>Vendedor: ${venta.vendedor.nombres} ${venta.vendedor.getAp_paterno()}</h3>
            </div>
        </div>

        <div>
            <table>
                <thead>
                <tr>
                    <td>ID PRODUCTO</td>
                    <td>PRODUCTO</td>
                    <td>CANTIDAD</td>
                    <td>PRECIO/UND</td>
                    <td>TOTAL</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="detalle" items="${venta.detalles}">
                    <tr>
                        <td>${detalle.farmaco.id}</td>
                        <td>${detalle.farmaco.nombre}</td>
                        <td>${detalle.cantidad}</td>
                        <td>${detalle.precio_unidad}</td>
                        <td>${detalle.calcularTotal()}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>
</body>
</html>