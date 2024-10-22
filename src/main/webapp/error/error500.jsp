<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error 500</title>
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
        }
        h1 {
            color: var(--color-secondary);
            font-weight: bold;
            font-size: 80px;
        }
        a {
            background-color: var(--color-secondary);
            padding: 8px 15px;
            border-radius: 20px;
            color: white;
        }
    </style>
</head>
<body>
<h1>500 <i class="bi bi-exclamation-triangle-fill"></i></h1>
<h2>Error interno del servidor</h2>

<%
    // Obtener la excepción de error
    Throwable error = (Throwable) request.getAttribute("javax.servlet.error.exception");
    if (error != null) {
        out.println("<h3>Error: " + error.getMessage() + "</h3>");
    } else {
        String exception = request.getParameter("exception");
        if (exception != null) {
            out.println("<h3>Error: " + exception + "</h3>");
        }
    }
%>

<a href="${pageContext.request.contextPath}/login">Volver a la pantalla principal</a>
</body>
</html>
