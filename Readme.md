#Deploy the app in a Docker Container
Make a docker image from this project:

`docker build -t spring-rest-infomatix:latest .  `

and run:

`docker run --name spring-rest-infomatix -d -p 8080:8080 spring-rest-infomatix:latest`

---
See the `BookRest.postman_collection` file for more information on endpoints and request format.