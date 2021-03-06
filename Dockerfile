FROM maven:3-jdk-11-slim AS build
COPY src /usr/app/src
COPY pom.xml /usr/app
RUN mvn -ntp -f /usr/app/pom.xml package

FROM openjdk:11-jre-slim
COPY --from=build /usr/app/target/xsr-1.0-SNAPSHOT.jar /opt/xsr.jar
COPY --from=build /usr/app/target/libs/* /opt/libs/
EXPOSE 10097
CMD ["java", "-jar", "/opt/xsr.jar"]
