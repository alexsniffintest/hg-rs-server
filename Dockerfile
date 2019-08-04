FROM openjdk:8
ONBUILD ADD data/* /app/
ONBUILD COPY target/scala-*/*-assembly*.jar /app/
ONBUILD RUN ln -sf /app/*-assembly*.jar /app/server.jar
ONBUILD WORKDIR /app
CMD ["java","-jar","/app/server.jar"]
ONBUILD EXPOSE 46900