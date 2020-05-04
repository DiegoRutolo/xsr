FROM maven:3-jdk-11
FROM java:11
EXPOSE 10097
COPY --from=0 /usr/src/app/target/xsr-1.0-SNAPSHOT.jar /opt/xsr.jar
CMD ["java", "-jar", "/opt/xsr.jar"]
