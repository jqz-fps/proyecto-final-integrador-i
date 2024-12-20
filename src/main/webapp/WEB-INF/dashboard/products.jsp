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
  <title>Productos | Líder Médica</title>
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
        <a id="sells-link" href="sells" class="sidebar-label">
          <i class="bi bi-receipt"></i>
          <span>Ventas</span>
        </a>
      </div>
      <div class="radio checked">
        <a id="products-link" href="products" class="sidebar-label">
          <i class="bi bi-box"></i>
          <span>Productos</span>
        </a>
      </div>
      <div class="radio">
        <a id="labs-link" href="labs" class="sidebar-label">
          <i class="bi bi-shop"></i>
          <span>Laboratorios</span>
        </a>
      </div>
      <div class="radio">
        <input type="radio" class="sidebar-radio" id="sidebar-categories" name="sidebar-radio-group">
        <a id="categories-link" href="categories" class="sidebar-label">
          <i class="bi bi-diagram-3"></i>
          <span>Categorías</span>
        </a>
      </div>
      <div class="radio">
        <a id="distributors-link" href="distributors" class="sidebar-label">
          <i class="bi bi-truck"></i>
          <span>Distribuidoras</span>
        </a>
      </div>
      <c:if test="${usuario != null && usuario.hasRole('Supervisor')}">
        <div class="radio">
          <a id="supervision-workers-link" href="supervision/workers" class="sidebar-label">
            <i class="bi bi-person-fill-lock"></i>
            <span>Panel de supervisores</span>
          </a>
        </div>
      </c:if>
      <div class="radio" data-bs-toggle="modal" data-bs-target="#sessionExitModal">
        <label id="session-exit-label" class="sidebar-label"
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
              <button id="btn-cerrar-sesion-fin" type="button" class="btn btn-tertiary" data-bs-dismiss="modal" onclick="document.getElementById('logout-form').submit();">Cerrar Sesión</button>
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
        <h3>Medicamentos</h3>
        <div class="search-container">
          <i class="bi bi-search"></i>
          <input autocomplete="off" type="text" placeholder="Buscar..." id="input-buscar-tabla" class="form-control" (input)="applyFilter($event)">
        </div>
      </div>
      <% if (esSupervisor) { %>
      <div class="right">
        <button class="btn btn-tertiary" data-bs-toggle="modal" data-bs-target="#addModal"><i class="bi bi-plus"></i> Medicamento</button>
        <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="addModalLabel">Agregar producto</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <form method="post">
                <div class="modal-body">
                  <div class="row">
                    <div class="group col">
                      <label for="nombre" class="form-label oblig">Nombre</label>
                      <input autocomplete="off" type="text" class="form-control" name="nombre" id="nombre" placeholder="Nombre" required>
                    </div>
                    <div class="group col">
                      <label for="presentacion" class="form-label oblig">Presentación</label>
                      <input autocomplete="off" type="text" class="form-control" name="presentacion" id="presentacion" placeholder="Presentación" required>
                    </div>
                  </div><br>
                  <div class="row">
                    <div class="group col">
                      <label for="composicion" class="form-label oblig">Composición</label>
                      <input autocomplete="off" type="text" class="form-control" name="composicion" id="composicion" placeholder="Composición" required>
                    </div>
                    <div class="group col">
                      <label for="precio" class="form-label oblig">Precio</label>
                      <input autocomplete="off" type="text" class="form-control" name="precio" id="precio" placeholder="Precio" required>
                    </div>
                    <div class="group col">
                      <label for="stock" class="form-label oblig">Stock</label>
                      <input autocomplete="off" type="text" class="form-control" id="stock" name="stock" placeholder="Stock" required>
                    </div>
                  </div><br>
                  <div class="row">
                    <div class="group col">
                      <label for="laboratorio" class="form-label oblig">Laboratorio</label>
                      <select name="laboratorio" id="laboratorio" class="form-select" required>
                        <option value="laboratorio" style="display: none;">Laboratorio</option>
                        <c:forEach var="laboratorio" items="${laboratoriosCargados}">
                          <option value="${laboratorio.id}">${laboratorio.nombre}</option>
                        </c:forEach>
                      </select>
                    </div>
                    <div class="group col">
                      <label for="categoria" class="form-label">Categoria</label>
                      <select name="categoria" id="categoria" class="form-select" required>
                        <option value="categoria" style="display: none;">Categoria</option>
                        <c:forEach var="categoria" items="${categoriasCargadas}">
                          <option value="${categoria.id}">${categoria.nombre}</option>
                        </c:forEach>
                      </select>
                    </div>
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
            <td>ID</td>
            <td>NOMBRE</td>
            <td>PRESENTACIÓN</td>
            <td>COMPOSICIÓN</td>
            <td>STOCK</td>
            <td>PRECIO</td>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="producto" items="${productosCargados}">
            <tr>
                <td>${producto.id}</td>
                <td>${producto.nombre}</td>
                <td>${producto.presentacion}</td>
                <td>${producto.composicion}</td>
                <td>${producto.stock}</td>
                <td>${producto.precio}</td>
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