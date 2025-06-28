FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY . .

RUN ./gradlew bootJar --no-daemon

CMD ["./gradlew", "bootRun", "--no-daemon"]
