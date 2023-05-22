FROM gradle:7.4.2-jdk17 as builder

WORKDIR /app
COPY --chown=gradle:gradle . .

RUN gradle build --no-daemon --refresh-dependencies

FROM openjdk:17

WORKDIR /app

# Copy the JAR file to the root directory
COPY --from=builder /app/build/libs/task-0.0.1-SNAPSHOT.jar /app/task-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "task-0.0.1-SNAPSHOT.jar"]
