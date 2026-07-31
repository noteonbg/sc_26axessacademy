FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY InterestServer.java .
RUN javac InterestServer.java
EXPOSE 5000
CMD ["java", "InterestServer"]
