-- Created by Andryev Lemes
CREATE USER beblue SUPERUSER PASSWORD 'bebluepass';
CREATE DATABASE "beblue"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.utf8'
       LC_CTYPE = 'en_US.utf8'
       CONNECTION LIMIT = -1;
GRANT ALL PRIVILEGES ON DATABASE beblue TO beblue;