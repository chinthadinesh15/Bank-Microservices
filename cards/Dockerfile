# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jre

# Information around who maintains the image
LABEL "org.opencontainers.image.authors"="dinesh babu"

# Set working directory
WORKDIR /app

# Copy the JAR file from the build context to the container
COPY target/cards-0.0.1-SNAPSHOT.jar app.jar

# Expose port (documentation only)
EXPOSE 9000

# Run the application with JVM optimizations
ENTRYPOINT ["java", "-jar", "app.jar"]
