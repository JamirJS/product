FROM eclipse-temurin:17-jre-alpine
ARG JAR_FILE=target/application-product-0.0.1.jar
COPY ${JAR_FILE} app_product.jar
EXPOSE 8096
ENTRYPOINT ["java", "-jar", "app_product.jar"]