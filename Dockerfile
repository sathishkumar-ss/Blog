# Use official Java 21 image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy Maven files first for better layer caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make the Maven wrapper executable
RUN chmod +x ./mvnw

# Download dependencies (will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the app
RUN ./mvnw package -DskipTests

# Expose port (Railway automatically assigns this)
EXPOSE ${PORT:-8080}

# Run the JAR file
CMD ["sh", "-c", "java -jar target/*.jar"]
