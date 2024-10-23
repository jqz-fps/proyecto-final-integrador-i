## Archivo `.env`

> [!IMPORTANT]
> El proyecto hace uso de un archivo .env para ingresar a la base de datos y usar el sistema de correos.

Para poder usar el sistema es necesario crear un archivo .env para iniciar el servidor, para ello, hay que crear un archivo `.env` en la carpeta `src/main/resources/` con el siguiente contenido:

```properties
# Variables de entorno para la base de datos
DB_URL=jdbc:mysql://localhost:3306/farmacia # Aquí poner la url de la base de datos
DB_USER=usuario # Aquí poner el usuario para acceder a la base de datos
DB_PASSWORD=contraseña # Aquí poner la contraseña para acceder a la base de datos

# Variables de entorno para el sistema de correo
GMAIL_USERNAME="Poner@Correo.Aqui"
GMAIL_APP_PASSWORD="Contraseña de 16 caracteres"
```

Luego de esto ya se puede ejecutar el proyecto sin problemas (suponiendo que la base de datos ya está creada).