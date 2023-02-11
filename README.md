# CRUDSampleApp
CRUD Sample App for Ant Media Server

In this sample application we created a custom REST API that makes CRUD operations on CRUDUser structure over Mongo DB. We wanted to show how you can make some server side modifications in your custom application running on top of Ant Media Server. 

# What we did?
Here we added the followings different than default [StreamApp](https://github.com/ant-media/StreamApp) provided by Ant Media Server: 
- `io.antmedia.rest` package. Naming is important because Spring checks that directory to deploy components. In this package we created `CRUDService.java` class which provides REST API.
- `io.antmedia.plugin` package. Naming is important because Spring checks that directory to deploy components. In this package we created `CRUDMain.java` class which is can be considered as the entry point for our application. We can initialize everything necessary for our application.
- `io.antmedia.db` package. In it we have `CRUDMongoStore` class which modifies the Mongo DB. Also we have `CRUDUser` class that is the structure on which we make CRUD operations.

# How to build?
- clone the project

`git clone https://github.com/burak-58/CRUDSampleApp.git`

- in the project folder build it:

`mvn clean install -DskipTests -Dgpg.skip=true`

# How to run?
- Install the war file created in `target` directory as a new application.
- Call the REST methos for:
  
  CREATE:
  
  ```curl -i -X POST -H "Accept: Application/json" -H "Content-Type: application/json" "http://localhost:5080/MyCRUDApp/rest/v1/sample/user" -d '{"name":"burak", "email":"burak@antmedia.io"}'```
  
  READ:
  
  ```curl -i -X GET -H "Accept: Application/json" -H "Content-Type: application/json" "http://localhost:5080/MyCRUDApp/rest/v1/sample/user/burak"'```
  
  UPDATE:
  
  ```curl -i -X PUT -H "Accept: Application/json" -H "Content-Type: application/json" "http://localhost:5080/MyCRUDApp/rest/v1/sample/user/burak" -d '{"email":"burak2@antmedia.io"}''```
  
  DELETE:
  
  ```curl -i -X DELETE -H "Accept: Application/json" -H "Content-Type: application/json" "http://localhost:5080/MyCRUDApp/rest/v1/sample/user/burak"```
