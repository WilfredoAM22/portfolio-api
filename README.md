# Portfolio API

Backend de mi portafolio personal, desarrollado con **Java y Spring Boot**. Su función principal es gestionar los mensajes enviados desde el formulario de contacto, validar los datos de entrada y notificarme mediante correo electrónico con formato HTML.

## Tecnologías utilizadas

- **Java 25**
- **Spring Boot** *(verifica la versión exacta en tu `pom.xml`)*
- **Spring Web MVC**
- **Spring Validation (Jakarta Bean Validation)**
- **Spring Mail (JavaMailSender & MimeMessageHelper)**
- **Maven**
- **Gmail SMTP**

## Arquitectura del proyecto

El proyecto sigue una estructura en capas organizada por responsabilidades:

- **`controller`**: Expone los endpoints REST y activa la validación de las peticiones.
- **`dto`**: Define los contratos inmutables (`records`) de entrada y salida junto con sus restricciones.
- **`service`**: Contiene la lógica de negocio para procesar mensajes (`ContactService`), el contrato de correo (`EmailService`) y su implementación (`SmtpEmailService`).
- **`exception`**: Centraliza el manejo global de errores mediante `@RestControllerAdvice` para devolver respuestas JSON limpias y seguras.

## Funcionalidades principales

- Recepción de mensajes de contacto mediante solicitudes HTTP `POST`.
- Validación estricta de campos (`name`, `email`, `message`) con mensajes personalizados.
- Generación de notificaciones mediante una plantilla HTML estilizada para clientes de correo.
- Protección contra inyección HTML (`HtmlUtils.htmlEscape`) en los datos ingresados por el usuario.
- Manejo estandarizado de errores de validación (`400 Bad Request`) y fallos internos (`500 Internal Server Error`).

## Documentación del Endpoint

| Método | Ruta | Descripción |
| :--- | :--- | :--- |
| `POST` | `/api/contact` | Recibe y procesa los mensajes del formulario de contacto. |

### Ejemplo de solicitud (`Request Body`)

```json
{
  "name": "Alexis Mariano",
  "email": "visitante@example.com",
  "message": "Hola, me gustaría conocer más sobre tus proyectos."
}
```

### Respuestas posibles

**200 OK — Mensaje enviado correctamente**
```json
{
  "status": "success",
  "message": "Mensaje recibido de forma correcta"
}
```

**400 Bad Request — Error de validación en los campos**
```json
{
  "status": "validation_error",
  "message": "Error de validacion en los datos",
  "errors": {
    "name": "El nombre debe tener entre 2 y 85 caracteres.",
    "email": "El formato del correo electrónico no es válido."
  }
}
```

**500 Internal Server Error — Fallo inesperado en el servidor**
```json
{
  "status": "server_error",
  "message": "Ocurrió un error interno en el servidor. Por favor, inténtalo más tarde."
}
```

## Configuración de variables de entorno

Por seguridad, ninguna credencial está almacenada en el código fuente. Antes de ejecutar la aplicación, define las siguientes variables en tu sistema operativo o IDE:

| Variable | Descripción |
| :--- | :--- |
| `MAIL_USERNAME` | Cuenta de Gmail remitente |
| `MAIL_PASSWORD` | Contraseña de aplicación de 16 caracteres de Google |
| `MAIL_TO` | Correo electrónico donde se recibirán las notificaciones |

## Ejecución local

1. Clonar el repositorio:
   ```bash
   git clone https://github.com
   cd portfolio-api
   ```
2. Configurar las variables de entorno en tu terminal o IDE:
   ```bash
   export MAIL_USERNAME="tu_correo@gmail.com"
   export MAIL_PASSWORD="tu_contraseña_de_aplicacion"
   export MAIL_TO="destino@gmail.com"
   ```
3. Ejecutar la aplicación usando el wrapper de Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
4. La API estará disponible en:
   ```text
   http://localhost:8080
   ```

---
*Proyecto desarrollado por Wilfredo Alexis Mariano como parte de su portafolio profesional.*
