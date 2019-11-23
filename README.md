# Microservicio Consulta - Técnicas Avanzadas de Programación
Este repositorio contiene el código del microservicio de consultas del sistema de validación crediticia realizado como trabajo práctico para materia Técnicas Avanzadas de Programación. Se encarga de la autenticación del sistema, generación de los JWT y administración de cuota máxima de solicitudes de estado que un usuario puede realizar en un periodo de una hora.
La autorización de los usuarios para los endpoints definidos es realizada por un API Gateway, que delega las peticiones a este microservicio.

## Instalación
### Requerimientos
- JDK 8
- Mongo DB
  - Base de datos: **tap-consultas-db**
- Variables de entorno  
  - ```JAVA_HOME```
  - ```MONGODB_HOST```: Host donde se encuentra la instancia corriendo
  - ```AUTH_DB_USER```: Usuario de conexión a la base de datos
  - ```AUTH_DB_PWD```: Contraseña del usuario para conexión con base de datos

```bash
git clone https://github.com/nickforla/tap-microservicio-autenticacion.git
cd tap-microservicio-autenticacion/
./mvnw install
java -jar target/tap-microservicio-consultas-1.0.0.jar
```

Una vez que el jar fue ejecutado, el servicio corre en el puerto 8084

## Endpoints
