Sistema de Gestión para Estudio Musical 🎸
Proyecto Full Stack desarrollado con Spring Boot, Thymeleaf, Java, HTML/CSS y MySQL.
Este sistema permite gestionar bandas, salas, reservas y servicios en un estudio musical profesional.

🌟 Funcionalidades principales
✅ Gestión completa de entidades:

Bandas: Nombre, género, cantidad de músicos, contacto.
Salas: Nombre, capacidad, precio por hora.
Servicios: Nombre, precio (micrófonos, iluminación, etc.).
Reservas: Asociación entre banda, sala, fecha y horario.
Relaciones Reserva-Servicio: Agregar servicios adicionales a una reserva (ej: alquiler de micrófonos).

✅ Operaciones CRUD (Crear, Leer, Actualizar, Eliminar)Para todas las entidades mediante una interfaz web intuitiva.
✅ Búsqueda dinámicaFiltrar reservas por ID de reserva o servicio.
✅ Modales interactivosCreación y edición de registros sin recargar la página.
✅ ValidacionesCampos obligatorios y mensajes de error claros.

🛠️ Tecnologías utilizadas



Tecnología
Uso



Java 17+
Lenguaje principal


Spring Boot
Backend, controladores, servicios


Thymeleaf
Plantillas HTML dinámicas


JPA / Hibernate
Mapeo objeto-relacional


MySQL
Base de datos


HTML5 & CSS3
Interfaz de usuario


JavaScript
Manejo de modales y eventos



🖼️ Capturas del sistema

Nota: Puedes reemplazar estos enlaces con tus imágenes reales en la carpeta screenshots/.

Interfaz principal del sistema
Formulario modal para crear nuevas salas
Listado con opciones de edición y eliminación

📦 Cómo ejecutar el proyecto
1. Clona el repositorio:
git clone https://github.com/tu-usuario/trabajo-final-estudio-musical.git

2. Configura la base de datos:

Asegúrate de tener MySQL instalado y en ejecución.
Abre tu cliente de MySQL (como MySQL Workbench o consola).
Ejecuta:CREATE DATABASE estudio_musical;

3. Configuración de credenciales:
⚠️ Nunca subas tus credenciales a GitHub.

Copia el archivo de ejemplo:
cp src/main/resources/application-template.properties src/main/resources/application.properties

Edita application.properties y pon tus credenciales reales:
spring.datasource.url=jdbc:mysql://localhost:3306/estudio_musical
spring.datasource.username=root
spring.datasource.password=tu_contraseña_secreta
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


🔐 El archivo application.properties está en .gitignore, así que no se subirá al repositorio.
4. Ejecuta el proyecto:
./mvnw spring-boot:run

5. Abre el navegador:
Accede a http://localhost:8080

📁 Estructura del proyecto
src/
├── main/
│   ├── java/com/tercer/trabajo/
│   │   ├── controllers/    → Controladores MVC
│   │   ├── services/       → Lógica de negocio
│   │   ├── repositories/   → Acceso a BD
│   │   ├── entidades/      → Modelos JPA
│   │   └── TrabajoFinalApplication.java
│   └── resources/
│       ├── templates/      → Archivos HTML (Thymeleaf)
│       ├── static/         → CSS, JS, imágenes
│       └── application.properties

📂 Archivos importantes

application-template.properties: Ejemplo de configuración (sin credenciales).
.gitignore: Protege archivos sensibles como application.properties.
screenshots/: Carpeta con capturas para el portafolio.


🙌 Autor
Lucas Bonggio | Portfolio | ✉️ lucas.abonggio@email.com
