FROM openjdk:8-jre

COPY build/libs/track-debts-0.0.1-SNAPSHOT.jar /tmp

ENTRYPOINT ["java", "-jar", "/tmp/track-debts-0.0.1-SNAPSHOT.jar"]
