# Read Me

#### INFORMATION:
* PROJECT'S NAME: DRONES
* PRODUCED BY: YAMIL MARTINEZ MENGUAL
* Spring boot version 3.1.0
* Required JDK 17

#### DATABASE INFORMATION:
H2 database is an open source, embedded and in memory relational database management system. 
It is written in Java and provides a client/server application. It stores data in system memory instead of disk.

Open: http://localhost:8080/h2-console/
user: drone
password: drone

# Getting Started

#### How to compile the program
* In the root folder of the application, raise a console and run “mvn package”,
  requirement to have installed Maven or with a programming IDE run the program.


####  Run Program
* java -jar drone.jar --server.port=8080.

####  Create Drone:
  URL endpoint local server: http://localhost:8080/api/drone/create
* Method: POST
* Request example: 
{
  "serialNumber":"GAJi3",
  "droneModel":"2",
  "weightLimit":500,
  "droneStatus":"2",
  "battery":45,
  "medications":[
  {
  "idMedication": 1,
  "name":"ETR_S8",
  "weight":"200",
  "code":"WBRI_26",
  "image":"2f885077341cb05943c2aeddc1288ad72934de45442732cff695572f35b04c9340fe6f7a6f95488c9446943da0a54576ffa6a9a87d1f2b48e6292b259d562b2851cb0d1310985b52fc44b6d33a19cc87a496947dcddb8feff366a8e72fe68c7bcdf313b379ad22ab7f94b338f3c8df8e9adffb7bb4650eb278a1c1ef89a86dd44f28924e713fba5eac54ad62eba1a2c14bdd3edc040455156b7930c558b8e66f2124471675968d61d65eae5550a4b1e64d01de849acccba6c16405148174af6ae30c84705dcbb9496ce36530fdd92f866ac0bbcf790f7a22821f1fcf04210ab7b9a971af9ee175aecec502c3ca752f66f09f3dd54f7d4a7c75f763d2150f10ee"
  },
  {
  "idMedication": 2,
  "name":"TBwH34",
  "weight":"50",
  "code":"TB_69",
  "image":""
  }
  ]
} 
* Response example: {
  "idDrone": 1,
  "battery": 45,
  "serialNumber": "GAJi3",
  "droneModel": "CRUISERWEIGHT",
  "weightLimit": 500.0,
  "droneStatus": "LOADED",
  "medications": [
  {
  "idMedication": 1,
  "name": "ETR_S8",
  "weight": 200.0,
  "code": "WBRI_26",
  "image": "2f885077341cb05943c2aeddc1288ad72934de45442732cff695572f35b04c9340fe6f7a6f95488c9446943da0a54576ffa6a9a87d1f2b48e6292b259d562b2851cb0d1310985b52fc44b6d33a19cc87a496947dcddb8feff366a8e72fe68c7bcdf313b379ad22ab7f94b338f3c8df8e9adffb7bb4650eb278a1c1ef89a86dd44f28924e713fba5eac54ad62eba1a2c14bdd3edc040455156b7930c558b8e66f2124471675968d61d65eae5550a4b1e64d01de849acccba6c16405148174af6ae30c84705dcbb9496ce36530fdd92f866ac0bbcf790f7a22821f1fcf04210ab7b9a971af9ee175aecec502c3ca752f66f09f3dd54f7d4a7c75f763d2150f10ee"
  },
  {
  "idMedication": 2,
  "name": "TBwH34",
  "weight": 50.0,
  "code": "TB_69",
  "image": ""
  }
  ]
  }
#### List Drones:
  URL endpoint local server: http://localhost:8080/api/drone/
* Method: GET 
* Request example: 
* Response example: [
  {
  "idDrone": 1,
  "battery": 45,
  "serialNumber": "GAJi3",
  "droneModel": "CRUISERWEIGHT",
  "weightLimit": 500.0,
  "droneStatus": "LOADED",
  "medications": [
  {
  "idMedication": 1,
  "name": "ETR_S8",
  "weight": 200.0,
  "code": "WBRI_26",
  "image": "2f885077341cb05943c2aeddc1288ad72934de45442732cff695572f35b04c9340fe6f7a6f95488c9446943da0a54576ffa6a9a87d1f2b48e6292b259d562b2851cb0d1310985b52fc44b6d33a19cc87a496947dcddb8feff366a8e72fe68c7bcdf313b379ad22ab7f94b338f3c8df8e9adffb7bb4650eb278a1c1ef89a86dd44f28924e713fba5eac54ad62eba1a2c14bdd3edc040455156b7930c558b8e66f2124471675968d61d65eae5550a4b1e64d01de849acccba6c16405148174af6ae30c84705dcbb9496ce36530fdd92f866ac0bbcf790f7a22821f1fcf04210ab7b9a971af9ee175aecec502c3ca752f66f09f3dd54f7d4a7c75f763d2150f10ee"
  },
  {
  "idMedication": 2,
  "name": "TBwH34",
  "weight": 50.0,
  "code": "TB_69",
  "image": ""
  }
  ]
  }
  ]
#### UPDATE DRONE MEDICATIONLIST:
  URL endpoint local server: http://localhost:8080/api/drone/2/updatemedicationitems
* Method: POST
* Request example: [
  {
  "idMedication": 2,
  "name": "TBwH34",
  "weight": 50.0,
  "code": "TB_69",
  "image": ""
  }
  ]
* Response example: {
  "idDrone": 2,
  "battery": 45,
  "serialNumber": "GAJi3",
  "droneModel": "CRUISERWEIGHT",
  "weightLimit": 500.0,
  "droneStatus": "LOADED",
  "medications": [
  {
  "idMedication": 3,
  "name": "EllR_S8",
  "weight": 10.0,
  "code": "LKI_26",
  "image": ""
  }
  ]
  }
#### LIST MEDICATION ITEMS BY idDRONE:
  URL endpoint local server: http://localhost:8080/api/drone/1/medications 
* Method: GET
* Request example:
* Response example: [
  {
  "idMedication": 1,
  "name": "ETR_S8",
  "weight": 200.0,
  "code": "WBRI_26",
  "image": "2f885077341cb05943c2aeddc1288ad72934de45442732cff695572f35b04c9340fe6f7a6f95488c9446943da0a54576ffa6a9a87d1f2b48e6292b259d562b2851cb0d1310985b52fc44b6d33a19cc87a496947dcddb8feff366a8e72fe68c7bcdf313b379ad22ab7f94b338f3c8df8e9adffb7bb4650eb278a1c1ef89a86dd44f28924e713fba5eac54ad62eba1a2c14bdd3edc040455156b7930c558b8e66f2124471675968d61d65eae5550a4b1e64d01de849acccba6c16405148174af6ae30c84705dcbb9496ce36530fdd92f866ac0bbcf790f7a22821f1fcf04210ab7b9a971af9ee175aecec502c3ca752f66f09f3dd54f7d4a7c75f763d2150f10ee"
  },
  {
  "idMedication": 2,
  "name": "TBwH34",
  "weight": 50.0,
  "code": "TB_69",
  "image": ""
  }
  ]
#### DRONES AVAILABLE:
 URL endpoint local server: http://localhost:8080/api/drone/available
* Method: GET
* Request example:
* Response example: [
  {
  "idDrone": 3,
  "battery": 45,
  "serialNumber": "Ji3",
  "droneModel": "MIDDLEWEIGHT",
  "weightLimit": 500.0,
  "droneStatus": "IDLE",
  "medications": [
  {
  "idMedication": 1,
  "name": "ETR_S8",
  "weight": 200.0,
  "code": "WBRI_26",
  "image": "2f885077341cb05943c2aeddc1288ad72934de45442732cff695572f35b04c9340fe6f7a6f95488c9446943da0a54576ffa6a9a87d1f2b48e6292b259d562b2851cb0d1310985b52fc44b6d33a19cc87a496947dcddb8feff366a8e72fe68c7bcdf313b379ad22ab7f94b338f3c8df8e9adffb7bb4650eb278a1c1ef89a86dd44f28924e713fba5eac54ad62eba1a2c14bdd3edc040455156b7930c558b8e66f2124471675968d61d65eae5550a4b1e64d01de849acccba6c16405148174af6ae30c84705dcbb9496ce36530fdd92f866ac0bbcf790f7a22821f1fcf04210ab7b9a971af9ee175aecec502c3ca752f66f09f3dd54f7d4a7c75f763d2150f10ee"
  },
  {
  "idMedication": 2,
  "name": "TBwH34",
  "weight": 50.0,
  "code": "TB_69",
  "image": ""
  }
  ]
  }
  ]
#### DRONE GET BATTERY LEVEL:
  URL endpoint local server: http://localhost:8080/api/drone/1/batterylevel
* Method: GET
* Request example:
* Response example: {
  "batteryLevel": 45
  }

##### Periodic task every 10 seconds
* Save history log on Audit table.
 
### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#web)
* [Validation](https://docs.spring.io/spring-boot/docs/3.1.0/reference/htmlsingle/#io.validation)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
