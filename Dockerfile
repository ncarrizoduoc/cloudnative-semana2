FROM eclipse-temurin:17-jdk AS buildstage 

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src

RUN --mount=type=secret,id=REGION,env=REGION \
    --mount=type=secret,id=AWS_ACCESS_KEY_ID,env=AWS_ACCESS_KEY_ID \
    --mount=type=secret,id=AWS_SECRET_ACCESS_KEY,env=AWS_SECRET_ACCESS_KEY \
    --mount=type=secret,id=AWS_SESSION_TOKEN,env=AWS_SESSION_TOKEN

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk

COPY --from=buildstage /app/target/inscripciones-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]