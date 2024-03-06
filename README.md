# Estate

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.1.0.

## Start the project

Git clone:

> git clone https://github.com/OpenClassrooms-Student-Center/P3-Full-Stack-portail-locataire


### Front
Go inside folder:

> cd P3-Full-Stack-portail-locataire

Install dependencies:

> npm install

Launch Front-end:

> npm run start;
> 
### Back

Make sure you have a local instance of mysql80 running on port 3306

Setup your database username and password in the application.yml file with your MySql username and password strings.

For testing purposes, the database is emptied every time you run the app you dont have to run any sql script to build all tables, all is done by hibernate

    hibernate:
      ddl-auto: create-drop



Go to backend folder

> cd backend

Build project
> mvn package

Run project
> mvn spring-boot:run


Project runs at 
```localhost:8080```

Swagger ui available at ```http://localhost:8080/api/swagger-ui/index.html```

## Ressources

### Mockoon env

Download Mockoon here: https://mockoon.com/download/

After installing you could load the environement

> ressources/mockoon/rental-oc.json

directly inside Mockoon 

> File > Open environmement

For launching the Mockoon server click on play bouton

Mockoon documentation: https://mockoon.com/docs/latest/about/

### Postman collection

For Postman import the collection

> ressources/postman/rental.postman_collection.json 

by following the documentation: 

https://learning.postman.com/docs/getting-started/importing-and-exporting-data/#importing-data-into-postman


### MySQL

SQL script for creating the schema is available `ressources/sql/script.sql`
