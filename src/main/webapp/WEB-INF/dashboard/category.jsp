<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 27/10/2024
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
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
    <title>Categorías | Líder Médica</title>
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
            <div class="radio">
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
            <div class="radio checked">
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
            <% if (esSupervisor) { %>
            <div class="radio">
                <a href="supervision/workers" class="sidebar-label">
                    <i class="bi bi-person-fill-lock"></i>
                    <span>Panel de supervisores</span>
                </a>
            </div>
            <% } %>
            <div class="radio" data-bs-toggle="modal" data-bs-target="#sessionExitModal">
                <label class="sidebar-label" style="--background-secondary: #fa5050; --color-primary: #161616">
                    <i class="bi bi-box-arrow-right"></i>
                    <span>Cerrar Sesión</span>
                </label>
            </div>
        </div>
        <div style="padding: 20px; display: flex; gap: 20px; align-items: center; justify-content: center; overflow: hidden;">
            <i class="bi bi-person-circle" style="font-size: 50px;"></i>
            Bienvenido <br>
            <%= usuario.getAp_paterno() %>, <%= usuario.getNombres() %>
        </div>
    </div>

    <div class="dashboard">
        <div class="header">
            <h3>Categorías</h3>
            <% if (esSupervisor) { %>
            <button class="btn btn-tertiary" data-bs-toggle="modal" data-bs-target="#addCategoryModal"><i class="bi bi-plus"></i> Agregar Categoría</button>
            <div class="modal fade" id="addCategoryModal" tabindex="-1" aria-labelledby="addCategoryModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addCategoryModalLabel">Agregar Nueva Categoría</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form method="post">
                            <div class="modal-body">
                                <div class="group">
                                    <label for="nombreCategoria" class="form-label oblig">Nombre</label>
                                    <input autocomplete="off" type="text" class="form-control" name="nombre" id="nombreCategoria" placeholder="Nombre" required>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                <button type="submit" class="btn btn-tertiary">Agregar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <% } %>
        </div>

        <div>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>NOMBRE</th>
                    <% if (esSupervisor) { %>
                    <th>ACCIONES</th>
                    <% } %>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="categoria" items="${categoriasCargadas}">
                    <tr>
                        <td>${categoria.id}</td>
                        <td>${categoria.nombre}</td>
                        <% if (esSupervisor) { %>
                        <td>
                            <form action="${pageContext.request.contextPath}/dashboard/categories" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${categoria.id}">
                                <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                            </form>
                        </td>
                        <% } %>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
