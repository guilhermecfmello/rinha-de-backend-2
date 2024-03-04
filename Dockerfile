# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Define environment variable
ENV JAVA_OPTS=""

# Run the spring boot application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar your-api.jar"]
