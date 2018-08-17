FROM postgres:9.6

ENV POSTGRES_DB postgres

COPY contacts.sql /docker-entrypoint-initdb.d/