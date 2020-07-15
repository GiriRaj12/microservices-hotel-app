#Users
FROM openjdk:8-jre-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
EXPOSE 8083
COPY ./target/lib /app/lib
COPY ./target/classes /app
COPY ./target/HotelUsers-1.0.jar app.jar
ENTRYPOINT ["java","-cp","app:app/lib/*","org.hotelapp.users.Controller.HotelUsersApplication"]