FROM adoptopenjdk/openjdk13:alpine-jre

WORKDIR /usr/src/app

COPY ./build/libs/*.jar /usr/src/app/app.jar
ENTRYPOINT java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -Dspring.profiles.active=citronium -jar /usr/src/app/app.jar
