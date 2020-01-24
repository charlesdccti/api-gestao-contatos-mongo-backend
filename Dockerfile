FROM maven:3.5-jdk-8-alpine
WORKDIR /app/contato-api
COPY . .
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]