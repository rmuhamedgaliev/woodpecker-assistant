FROM adoptopenjdk/openjdk13:alpine-jre

WORKDIR /usr/src/app

COPY *.jar /usr/src/app/app.jar
ENTRYPOINT java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar /usr/src/app/app.jar
