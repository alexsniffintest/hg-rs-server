FROM openjdk:8

COPY ./data/ /app/data/
COPY ./target/scala-*/*-assembly*.jar /app/
RUN ln -sf /app/*-assembly*.jar /app/server.jar

WORKDIR /app
CMD ["java","-jar","/app/server.jar"]

EXPOSE 46900