# Microservicio Consulta - Técnicas Avanzadas de Programación
Este repositorio contiene el código del microservicio de consultas del sistema de validación crediticia realizado como trabajo práctico para materia Técnicas Avanzadas de Programación. Se encarga de recibir las peticiones de los usuarios, busca la infomación en la BD, solicitar los estados al microservicio de riesgo y preparar la respuesta.
La autorización de los usuarios para los endpoints definidos es realizada por un API Gateway, que delega las peticiones a este microservicio.

## Instalación
### Requerimientos
- JDK 8
- Mongo DB
  - Base de datos: **tap-consultas-db**
- Variables de entorno  
  - ```JAVA_HOME```
  - ```MONGODB_HOST```: Host donde se encuentra la instancia corriendo
  - ```CONSULTAS_DB_USER```: Usuario de conexión a la base de datos
  - ```CONSULTAS_DB_PWD```: Contraseña del usuario para conexión con base de datos

```bash
git clone https://github.com/nickforla/tap-microservicio-consulta.git
cd tap-microservicio-consulta/
./mvnw install
java -jar target/microservicio-consulta-1.0.0.jar
```

Una vez que el jar fue ejecutado, el servicio corre en el puerto 8084

## Endpoints
- Analizar Estado Persona
  - GET
  - /analizarEstado/persona/{cuil}
  - Permite obtener el estado crediticio de una persona en base al CUIL enviado.
- Analizar Estado Personas
  - POST
  - /analizarEstado/personas
  - Permite obtener el estado crediticio de varias personas en base a un array de CUILs enviados en el cuerpo de la petición en formato JSON.
