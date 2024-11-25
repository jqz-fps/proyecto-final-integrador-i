<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 27/10/2024
  Time: 17:07
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
    <title>Laboratorios | Líder Médica</title>
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
            <div class="radio checked">
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
        <div style="padding: 20px; display: flex; gap: 20px; align-items: center; justify-content: center; overflow: hidden;">
            <i class="bi bi-person-circle" style="font-size: 50px;"></i>
            Bienvenido <br>
            <%= usuario.getAp_paterno() %>, <%= usuario.getNombres() %>
        </div>
    </div>

    <div class="dashboard">
        <div class="header">
            <div class="left">
                <h3>Laboratorios</h3>
                <div class="search-container">
                    <i class="bi bi-search"></i>
                    <input autocomplete="off" type="text" placeholder="Buscar..." id="input-buscar-tabla" class="form-control" (input)="applyFilter($event)">
                </div>
            </div>
            <% if (esSupervisor) { %>
            <div class="right">
                <button class="btn btn-tertiary" data-bs-toggle="modal" data-bs-target="#addLabModal"><i class="bi bi-plus"></i> Laboratorio</button>
                <div class="modal fade" id="addLabModal" tabindex="-1" aria-labelledby="addLabModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addLabModalLabel">Agregar laboratorio</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <form method="post">
                                <div class="modal-body">
                                    <div class="group">
                                        <label for="nombreLaboratorio" class="form-label oblig">Nombre</label>
                                        <input autocomplete="off" type="text" class="form-control" name="nombreLaboratorio" id="nombreLaboratorio" placeholder="Nombre" required>
                                    </div>
                                    <br>
                                    <div class="group">
                                        <label for="direccionLaboratorio" class="form-label oblig">Dirección</label>
                                        <input autocomplete="off" type="text" class="form-control" name="direccionLaboratorio" id="direccionLaboratorio" placeholder="Dirección" required>
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
            </div>
            <% } %>
        </div>

        <div>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>NOMBRE</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="laboratorio" items="${laboratoriosCargados}">
                    <tr>
                        <td>${laboratorio.id}</td>
                        <td>${laboratorio.nombre}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
<script defer>
  document.getElementById('input-buscar-tabla').addEventListener('keyup', function () {
    const filter = this.value.toLowerCase();
    const rows = document.querySelectorAll('table tbody tr');
    let visibleRowIndex = 0;

    rows.forEach(row => {
      const cells = Array.from(row.cells);
      const matches = cells.some(cell => cell.textContent.toLowerCase().includes(filter));
      row.style.display = matches ? '' : 'none';

      if (matches) {
        row.style.backgroundColor = visibleRowIndex % 2 === 0 
          ? 'var(--background-primary)' 
          : 'var(--background-secondary)';
        visibleRowIndex++;
      }
    });
  });
</script>
</html>
