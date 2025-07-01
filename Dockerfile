FROM maven:3.9.9-eclipse-temurin-21-alpine

WORKDIR /app

COPY . .

# Copy the SQLite DB file into the image (adjust the path as needed)
COPY shelfhelp.db ./data/shelfhelp.db

EXPOSE 8080

RUN mvn clean package

CMD ["java", "-jar", "target/shelfhelp-1.0-SNAPSHOT.jar"]