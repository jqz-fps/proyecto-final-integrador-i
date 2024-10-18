<%--
  Created by IntelliJ IDEA.
  User: Joaquin Requejo
  Date: 16/10/2024
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap-icons/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
    <style>
      body {
        height: 95vh;
        display: flex;
        flex-direction: column;
        gap: 20px;
        justify-content: center;
        align-items: center;
        text-align: center;
        user-select: none;
        h1 {
          color: var(--color-secondary);
          font-weight: bold;
          font-size: 80px;
        }
        a {
          background-color: var(--color-secondary);
          padding: 8px 15px;
          border-radius: 20px;
        }
      }
    </style>
</head>
<body>
    <h1>404 <i class="bi bi-emoji-frown-fill"></i></h1>
    <h2>No se ha encontrado la página</h2>
    <a href="${pageContext.request.contextPath}/login">Volver a la pantalla principal</a>
</body>
</html>
