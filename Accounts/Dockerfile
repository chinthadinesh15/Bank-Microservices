# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jre

#Information around who maintains the image
LABEL "org.opencontainers.image.authors"="dinesh babu"

# Copy the JAR file from the build context to the container
COPY target/Accounts-0.0.1-SNAPSHOT.jar /app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]