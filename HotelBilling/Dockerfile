#Billing
FROM openjdk:8-jre-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
EXPOSE 8081
COPY ./target/lib /app/lib
COPY ./target/classes /app
COPY ./target/HotelBilling-1.0.jar app.jar
ENTRYPOINT ["java","-cp","app:app/lib/*","org.hotelapp.billing.Controllers.BillingApplication"]