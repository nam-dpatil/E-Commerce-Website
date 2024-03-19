FROM openjdk:21
WORKDIR /opt
ENV PORT 8080
EXPOSE 8080
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar

#ENTRYPOINT ["java", "-jar", "Ecommerce-Website.jar"]