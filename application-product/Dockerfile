FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

#Copiamos el POM primero para las dependencias
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

#Copiamos el codigo y construimos
COPY src ./src
RUN mvn -B -DskipTests=false clean package

#Etapa 2: runtine (JRE 17)
FROM eclipse-temurin:17-jre
WORKDIR /app

#Render asigna el puerto en la variable de entorno PORT
ENV PORT=8096
EXPOSE 8096

#Copiamos el jar build
COPY --from=build /app/target/*.jar app_product.jar

#Iniciar la app usando el puerto de Render
CMD ["sh", "-c", "java -Dserver.port=${PORT} -jar app_product.jar"]