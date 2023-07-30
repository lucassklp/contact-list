FROM node:18.16.1-alpine3.18 as frontend-build
WORKDIR /frontend
COPY ./frontend ./
RUN npm ci
RUN npm run build

FROM maven:3.9.3-amazoncorretto-17 as backend-build
WORKDIR /backend
COPY ./backend ./
COPY --from=frontend-build /frontend/dist/contact-list ./src/main/resources/static
RUN mvn package -DskipTests

FROM amazoncorretto:17.0.8-al2023-headless
WORKDIR /app
COPY --from=backend-build /backend/target/contact-list-0.0.1-SNAPSHOT.jar ./
ENTRYPOINT [ "java", "-jar", "contact-list-0.0.1-SNAPSHOT.jar" ]