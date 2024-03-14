# Use a base image with Java 20 and Gradle
FROM openjdk:20-oracle AS build

COPY --chown=gradle:gradle . ./app
# Set the working directory inside the container

WORKDIR /app
# Copy the source code and build.gradle files to the container
RUN microdnf install findutils
RUN ./gradlew clean build

# Use a lightweight base image with Java 20
FROM openjdk:20-oracle

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from the build stage
COPY --from=build /app/build/libs/RinhadeBackend-*.jar app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "app.jar"]
