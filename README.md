# Beblue API - Desafio técnico - Engenheiro back-end

[![Build Status](https://travis-ci.org/Andryev/Desafio-Beblue_Engenheiro-Back-End_Andryev_Lemes.svg?branch=master)](https://travis-ci.org/Andryev/Desafio-Beblue_Engenheiro-Back-End_Andryev_Lemes)
#### Technology

* [Spring Boot] - Spring Boot - Java Back End
* [Fly Way] - Flyway is an open-source database migration tool. It strongly favors simplicity and convention over configuration.
* [Maven] - Dependency and build management.
* [Undertow] - Undertow is a flexible performant web server written in java.
* [Project Lombok] - Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.

#### Database

* [Postgres] 

#### Clone
Run: `git clone https://github.com/Andryev/Desafio-Beblue_Engenheiro-Back-End_Andryev_Lemes.git`.

#### Build

Run: `mvn clean install`.

#### Start

Run: `mvn clean install`

Run: `docker-compose -f docker-compose.yml up --build`

#### Or
Run on your postgres

```sql
CREATE USER beblue SUPERUSER PASSWORD 'bebluepass';
CREATE DATABASE "beblue"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.utf8'
       LC_CTYPE = 'en_US.utf8'
       CONNECTION LIMIT = -1;
GRANT ALL PRIVILEGES ON DATABASE beblue TO beblue;
```
And 
Run: `mvn spring-boot:run`
#### Resources
#### Albums

* Find Albums
    * Headers: Content-Type application/json
    * GET:
http://localhost:8080/ws/album?genre=Pop&sort=name,Asc&page=1

* Find Album By Id
    * Headers: Content-Type application/json
    * GET:
http://localhost:8080/ws/album/1

#### Sales
* Find Sales
    * Headers: Content-Type application/json
    * GET:
http://localhost:8080/ws/sale?startDate=01/02/2019&endDate=21/04/2019&sort=saleDate,Desc&page=

* Find Sale By Id
    * Headers: Content-Type application/json
    * GET:
http://localhost:8080/ws/sale/1
* Save Sale
    * Headers: Content-Type application/json
    * POST:
http://localhost:8080/ws/sale
    * Example Body:<br>
    ```json
    {
     	"itemsSale":[
     		{
     			"album":{
                     "id": 1,
                     "name": "POP/STARS",
                     "idSpotify": "0UnBZ8laFgLUq5Ty5vbikQ",
                     "artistName": "K/DA",
                     "genre": "POP",
                     "value": 16.68
     			},
     			"amount":3
     		},
     		{
     			"album":{
                     "id": 187,
                 	"name": "Rock or Bust",
                 	"idSpotify": "6OwvO40ahugJE5PH4TjqTg",
                 	"artistName": "AC/DC",
                 	"genre": "ROCK",
                 	"value": 27.66
     			},
     			"amount":2
     		}
     	]
     }
    ```

[Spring Boot]: <https://spring.io/projects/spring-boot>
[Fly Way]: <https://flywaydb.org>
[Maven]: <https://maven.apache.org>
[Undertow]: <http://undertow.io>
[Project Lombok]: <https://projectlombok.org>
[Postgres]: <https://www.postgresql.org/>