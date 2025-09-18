FROM postgres:latest
ENV POSTGRES_DB=ihaq
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD={POSTGRES_PASSWORD}
COPY ./startup.sql /docker-entrypoint-initdb.d/
EXPOSE 5432