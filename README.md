# 🎸 Sistema de Gestión para Estudio Musical

Proyecto **Full Stack** desarrollado con **Spring Boot**, **Thymeleaf**, **Java**, **HTML/CSS** y **MySQL**.  
Este sistema permite gestionar **bandas, salas, reservas y servicios** de un estudio musical profesional.

---

## 🌟 Funcionalidades Principales

- ✅ **Gestión completa de entidades:**
  - **Bandas**: nombre, género, cantidad de músicos, contacto.
  - **Salas**: nombre, capacidad, precio por hora.
  - **Servicios**: nombre, precio (micrófonos, iluminación, etc.).
  - **Reservas**: asociación entre banda, sala, fecha y horario.
  - **Reserva-Servicio**: permite asociar servicios adicionales a una reserva.

- ✅ **Operaciones CRUD** para todas las entidades mediante una **interfaz web intuitiva**.
- 🔍 **Búsqueda dinámica** por ID de reserva o servicio.
- 🧩 **Modales interactivos**: creación y edición sin recargar la página.
- ⚠️ **Validaciones de formulario** con campos obligatorios y mensajes claros.

---

## 🛠️ Tecnologías Utilizadas

| Tecnología       | Rol en el Proyecto                      |
|------------------|------------------------------------------|
| `Java 17+`       | Lenguaje principal                      |
| `Spring Boot`    | Backend, controladores, servicios       |
| `Thymeleaf`      | Plantillas HTML dinámicas               |
| `JPA / Hibernate`| Mapeo objeto-relacional                 |
| `MySQL`          | Base de datos                           |
| `HTML5 & CSS3`   | Interfaz de usuario                     |
| `JavaScript`     | Modales, interacciones dinámicas        |

---

## 📦 Cómo Ejecutar el Proyecto

### 1. Clonar el repositorio

```bash
1. git clone https://github.com/tu-usuario/trabajo-final-estudio-musical.git
cd trabajo-final-estudio-musical
```
2. Crear la base de datos
```bash
CREATE DATABASE estudio_musical;
```

3. Configurar credenciales de la base de datos
```bash
cp src/main/resources/application-template.properties src/main/resources/application.properties
```
Edita application.properties con tus credenciales:
spring.datasource.url=jdbc:mysql://localhost:3306/estudio_musical
spring.datasource.username=root
spring.datasource.password=tu_contraseña_secreta

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
🔒 El archivo application.properties está en el .gitignore y no se sube al repo.

4. Ejecutar la aplicación
```bash
./mvnw spring-boot:run
```

6. Acceder desde el navegador
```bash
http://localhost:8080

```

📁 Estructura del Proyecto

src/
├── main/
│   ├── java/com/tercer/trabajo/
│   │   ├── controllers/       # Controladores MVC
│   │   ├── services/          # Lógica de negocio
│   │   ├── repositories/      # Acceso a BD
│   │   ├── entidades/         # Modelos JPA
│   │   └── TrabajoFinalApplication.java
│   └── resources/
│       ├── templates/         # Archivos HTML (Thymeleaf)
│       ├── static/            # CSS, JS, imágenes
│       └── application.properties


📂 Archivos Importantes

    application-template.properties: ejemplo de configuración (sin credenciales).

    .gitignore: protege archivos sensibles como application.properties.

🙌 Autor
Lucas Bonggio | Portfolio | ✉️ adriano.abonggio@gmail.com

