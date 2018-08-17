FROM postgres:9.6

ENV POSTGRES_DB postgres

COPY postgres.sql /docker-entrypoint-initdb.d/