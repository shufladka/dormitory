FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8180

ENV SPRING_DATASOURCE_URL=jdbc:mariadb://host.docker.internal:3306/dormitory

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
