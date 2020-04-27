FROM maven:3-jdk-8-onbuild
FROM java:8
COPY --from=0 /usr/src/app/target/xsr-1.0-SNAPSHOT.jar /opt/xsr.jar
CMD ["java", "-jar", "/opt/xsr.jar"]
