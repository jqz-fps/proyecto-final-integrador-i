<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login | Líder Médica</title>
    <link rel="icon" href="${pageContext.request.contextPath}/static/icon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap-icons/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css">
    <style>
        .parent {
            display: grid;
            grid-template-columns: repeat(7, 1fr);
            grid-template-rows: repeat(1, 1fr);
            gap: 8px;
            height: 99vh;
        }

        #logo-empresa {
            max-width: 700px;
        }

        .div1 {
            grid-column: span 4 / span 4;
            display: flex;
            justify-content: center;
            align-items: center;
            border-right: 1px solid var(--background-secondary);
        }

        .div2 {
            grid-column: span 3 / span 3;
            grid-column-start: 5;
            display: flex;
            padding: 60px;
            flex-direction: column;
            justify-content: center;
            form {
                display: flex;
                flex-direction: column;
                gap: 50px;
            }
        }
    </style>
</head>
<body>
<div class="parent">
    <div class="div1">
        <img src="${pageContext.request.contextPath}/static/images/logo.png" id="logo-empresa" alt="logo">
    </div>
    <div class="div2">
        <form action="${pageContext.request.contextPath}/login" method="post" id="login-form" class="container">
            <h3 class="text-center">Cambiar Contraseña</h3>
            <div>
                <span>Contraseña</span><br><br>
                <input type="password" class="form-control" name="password" id="password" placeholder="Ingresar contraseña">
            </div>
            <div>
                <span>Ingrese nuevamente la contraseña</span><br><br>
                <input type="password" class="form-control" name="passworda" id="passworda" placeholder="Repetir contraseña">
            </div>
            <script>
                function validarContraseña() {
                    const password = document.getElementById("password").value;
                    const password2 = document.getElementById("passworda").value;
                    
                    if (password.includes(" ")) { // Cambiado de .contains a .includes
                        document.getElementById("cambiar-contraseña-btn").innerHTML = "Contraseña no puede contener espacios";
                        document.getElementById("cambiar-contraseña-btn").disabled = true;
                    } else if (password.length > 15) {
                        document.getElementById("cambiar-contraseña-btn").innerHTML = "Contraseña demasiado larga";
                        document.getElementById("cambiar-contraseña-btn").disabled = true;
                    } else if (password !== password2) {
                        document.getElementById("cambiar-contraseña-btn").innerHTML = "Contraseñas no coinciden";
                        document.getElementById("cambiar-contraseña-btn").disabled = true;
                    } else {
                        document.getElementById("cambiar-contraseña-btn").innerHTML = "Cambiar e ingresar";
                        document.getElementById("cambiar-contraseña-btn").disabled = false;
                    }
                }
                document.getElementById("password").addEventListener("input", validarContraseña);
                document.getElementById("passworda").addEventListener("input", validarContraseña);
            </script>
            <c:if test="${not empty error}">
                ${error}
            </c:if>
            <button type="submit" id="cambiar-contraseña-btn" class="btn btn-tertiary container" disabled style="max-width: 300px;">Ingrese nueva contraseña</button>
        </form>
    </div>
</div>
</body>
</html>