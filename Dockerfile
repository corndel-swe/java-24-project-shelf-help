FROM maven:3.9.9-eclipse-temurin-21-alpine

WORKDIR /app

COPY . .

COPY shelfhelp.db ./data/shelfhelp.db

EXPOSE 8080

RUN mvn clean package

CMD ["java", "-jar", "target/shelfhelp-1.0-SNAPSHOT.jar"]
