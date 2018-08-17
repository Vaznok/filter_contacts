FROM postgres:9.6

COPY postgres.sql /docker-entrypoint-initdb.d