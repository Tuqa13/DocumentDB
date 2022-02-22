FROM openjdk:8
WORKDIR /
ADD target/document-db.jar document-db.jar
EXPOSE 8080
CMD java -jar document-db.jar