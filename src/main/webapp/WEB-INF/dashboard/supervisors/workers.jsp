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
  <title>Supervisión | Líder Médica</title>
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
        <a href="../sells" class="sidebar-label">
          <i class="bi bi-receipt"></i>
          <span>Ventas</span>
        </a>
      </div>
      <div class="radio">
        <a href="../products" class="sidebar-label">
          <i class="bi bi-box"></i>
          <span>Productos</span>
        </a>
      </div>
      <div class="radio">
        <a href="../labs" class="sidebar-label">
          <i class="bi bi-shop"></i>
          <span>Laboratorios</span>
        </a>
      </div>
      <div class="radio">
        <input type="radio" class="sidebar-radio" id="sidebar-categories" name="sidebar-radio-group">
        <a href="../categories" class="sidebar-label">
          <i class="bi bi-diagram-3"></i>
          <span>Categorías</span>
        </a>
      </div>
      <div class="radio">
        <a href="../distributors" class="sidebar-label">
          <i class="bi bi-truck"></i>
          <span>Distribuidoras</span>
        </a>
      </div>
      <c:if test="${usuario != null && usuario.hasRole('Supervisor')}">
        <div class="radio checked">
          <a href="../supervition" class="sidebar-label">
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
        <h3>Trabajadores</h3>
        <div class="search-container">
          <i class="bi bi-search"></i>
          <input autocomplete="off" type="text" placeholder="Buscar..." class="form-control">
        </div>
      </div>
      <div class="right">
        <button class="btn btn-tertiary" data-bs-toggle="modal" data-bs-target="#addModal"><i class="bi bi-plus"></i>  Trabajador</button>
        <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="addModalLabel">Agregar trabajador</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <form method="post">
                <div class="modal-body" style="display: flex;flex-direction: column;gap: 20px;">
                  <div>
                    <label for="dni" class="form-label oblig">DNI</label>
                    <input autocomplete="off" type="text" class="form-control" name="dni" id="dni" placeholder="DNI" required>
                  </div>
                  <div>
                    <label for="nombre" class="form-label oblig">Nombres</label>
                    <input autocomplete="off" type="text" class="form-control" name="nombres" id="nombres" placeholder="Nombres" required>
                  </div>
                  <div class="row">
                    <div class="group col">
                      <label for="apellido_paterno" class="form-label oblig">Apellido Paterno</label>
                      <input autocomplete="off" type="text" class="form-control" name="apellido_paterno" id="apellido_paterno" placeholder="Apellido Paterno" required>
                    </div>
                    <div class="group col">
                      <label for="apellido_materno" class="form-label oblig">Apellido Materno</label>
                      <input autocomplete="off" type="text" class="form-control" name="apellido_materno" id="apellido_materno" placeholder="Apellido Materno" required>
                    </div>
                  </div>
                  <div class="row">
                    <div class="group col">
                      <label for="genero" class="form-label oblig">Genero</label>
                      <select name="genero" id="genero" class="form-control" required>
                        <option value="genero" style="display: none;">Genero</option>
                        <option value="M">Masculino</option>
                        <option value="F">Femenino</option>
                      </select>
                    </div>
                    <div class="group col">
                      <label for="fecha_nacimiento" class="form-label oblig">Fecha Nacimiento</label>
                      <input autocomplete="off" type="date" class="form-control" name="fecha_nacimiento" id="fecha_nacimiento" placeholder="Fecha Nacimiento" required>
                    </div>
                  </div>
                  <div>
                    <label for="correo" class="form-label oblig">Correo</label>
                    <input autocomplete="off" type="text" class="form-control" name="correo" id="correo" placeholder="Correo" required>
                  </div>
                  <div>
                    <label for="telefono" class="form-label oblig">Telefono</label>
                    <input autocomplete="off" type="text" class="form-control" name="telefono" id="telefono" placeholder="Telefono" required>
                  </div>
                  <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="supervisor" name="supervisor" value="supervisor">
                    <label class="form-check-label">Supervisor</label>
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
    <c:if test="${not empty sessionScope.errorCorreo}">
      ${sessionScope.errorCorreo}
      <script>
        setTimeout(function() {
          var alert = document.querySelector('.alert');
          if (alert) {
            alert.remove();
          }
        }, 5000);
      </script>
      <%
        session.removeAttribute("errorCorreo");
      %>
    </c:if>
    <c:if test="${not empty sessionScope.errorTelefono}">
      ${sessionScope.errorTelefono}
      <script>
        setTimeout(function() {
          var alert = document.querySelector('.alert');
          if (alert) {
            alert.remove();
          }
        }, 5000);
      </script>
      <%
        session.removeAttribute("errorTelefono");
      %>
    </c:if>
    <c:if test="${not empty sessionScope.errorFechaNacimiento}">
      ${sessionScope.errorFechaNacimiento}
      <script>
        setTimeout(function() {
          var alert = document.querySelector('.alert');
          if (alert) {
            alert.remove();
          }
        }, 5000);
      </script>
      <%
        session.removeAttribute("errorFechaNacimiento");
      %>
    </c:if>
    <c:if test="${not empty sessionScope.finalAgregado}">
      ${sessionScope.finalAgregado}
      <script>
        setTimeout(function() {
          var alert = document.querySelector('.alert');
          if (alert) {
            alert.remove();
          }
        }, 5000);
      </script>
      <%
        session.removeAttribute("finalAgregado");
      %>
    </c:if>
    <div>
      <table style="font-size:13px">
        <thead>
          <tr>
            <td>ID</td>
            <td>NOMBRES</td>
            <td>APELLIDOS</td>
            <td>GENERO</td>
            <td>FECHA DE REGISTRO</td>
            <td>CORREO/USUARIO</td>
            <td>DNI</td>
            <td>ROLES</td>
            <td></td>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="trabajador" items="${trabajadoresCargados}">
            <tr>
                <td>${trabajador.id}</td>
                <td>${trabajador.getNombres()}</td>
                <td>${trabajador.getApellidos()}</td>
                <td>${trabajador.getGeneroParseado()}</td>
                <td>${trabajador.getFechaRegistroFormateada()}</td>
                <td>${trabajador.getCorreo()}</td>
                <td>${trabajador.getDni()}</td>
                <td style="display: flex; flex-direction: column;justify-content: center;align-items: center;">
                  <c:forEach var="rol" items="${trabajador.getRoles()}">
                    <span style="margin-right: 5px;">${rol.toString()}</span>
                  </c:forEach>
                </td>
                <td>
                  <button class="btn" data-bs-toggle="modal" data-bs-target="#editWorker${trabajador.id}"><i class="bi bi-pencil" style="color: var(--color-secondary);"></i></button>
                  <div class="modal fade" id="editWorker${trabajador.id}" tabindex="-1" aria-labelledby="editWorker${trabajador.id}Label" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-lg">
                      <div class="modal-content" style="text-align: left;font-size: initial;">
                        <div class="modal-header">
                          <h5 class="modal-title" id="addModalLabel">Editar trabajador ${trabajador.id}</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <form method="post">
                          <input type="text" name="edit-id" id="edit-id" style="display: none;" value="${trabajador.id}">
                          <div class="modal-body" style="display: flex;flex-direction: column;gap: 20px;">
                            <div>
                              <label for="dni" class="form-label oblig">DNI</label>
                              <input autocomplete="off" type="text" class="form-control" value="${trabajador.getDni()}" name="edit-dni" id="dni" placeholder="DNI" required>
                            </div>
                            <div>
                              <label for="nombre" class="form-label oblig">Nombres</label>
                              <input autocomplete="off" type="text" class="form-control" value="${trabajador.getNombres()}" name="edit-nombres" id="nombres" placeholder="Nombres" required>
                            </div>
                            <div class="row">
                              <div class="group col">
                                <label for="apellido_paterno" class="form-label oblig">Apellido Paterno</label>
                                <input autocomplete="off" type="text" class="form-control" name="edit-apellido_paterno" id="apellido_paterno" value="${trabajador.getAp_paterno()}" placeholder="Apellido Paterno" required>
                              </div>
                              <div class="group col">
                                <label for="apellido_materno" class="form-label oblig">Apellido Materno</label>
                                <input autocomplete="off" type="text" class="form-control" name="edit-apellido_materno" id="apellido_materno" value="${trabajador.getAp_materno()}" placeholder="Apellido Materno" required>
                              </div>
                            </div>
                            <div class="row">
                              <div class="group col">
                                <label for="genero" class="form-label oblig">Genero</label>
                                <select name="edit-genero" id="genero" class="form-control" required>
                                  <option value="genero" style="display: none;">Genero</option>
                                  <option value="M" selected>Masculino</option>
                                  <option value="F">Femenino</option>
                                </select>
                              </div>
                              <div class="group col">
                                <label for="fecha_nacimiento" class="form-label oblig">Fecha Nacimiento</label>
                                <input autocomplete="off" type="date" class="form-control" name="edit-fecha_nacimiento" value="${trabajador.getFecha_nacimientoFormatoInput()}" id="fecha_nacimiento" placeholder="Fecha Nacimiento" required>
                              </div>
                            </div>
                            <div>
                              <label for="correo" class="form-label oblig">Correo</label>
                              <input autocomplete="off" type="text" class="form-control" value="${trabajador.correo}" name="edit-correo" id="correo" placeholder="Correo" required>
                            </div>
                            <div>
                              <label for="telefono" class="form-label oblig">Telefono</label>
                              <input autocomplete="off" type="text" class="form-control" value="${trabajador.telefono}" name="edit-telefono" id="telefono" placeholder="Telefono" required>
                            </div>
                            <div class="form-check">
                              <c:if test="${trabajador.hasRole(Rol.Supervisor)}">
                                  <input class="form-check-input" type="checkbox" id="supervisor" name="edit-supervisor" value="supervisor" checked>
                              </c:if>
                              <c:if test="${!trabajador.hasRole(Rol.Supervisor)}">
                                  <input class="form-check-input" type="checkbox" id="supervisor" name="edit-supervisor" value="supervisor">
                              </c:if>
                              <label class="form-check-label">Supervisor</label>
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
                </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

  </div>
</div>
</body>
</html>