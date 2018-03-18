#Exercise

Design and implement a RESTful API (including data model and the backing implementation) for money
transfers between accounts.

##Explicit requirements:
1. keep it simple and to the point (e.g. no need to implement any authentication, assume the APi is
invoked by another internal system/service)
2.  use whatever frameworks/libraries you like (except Spring, sorry!) but don't forget about the
requirement #1
3.  the datastore should run in-memory for the sake of this test
4.  the final result should be executable as a standalone program (should not require a pre-installed
container/server)
5.  demonstrate with tests that the API works as expected
Implicit requirements:
6.  the code produced by you is expected to be of high quality.
7.  there are no detailed requirements, use common sense.

#Build and run
<pre>
cd acme-transfer<br />
mvn install<br />
java -jar sample/target/sample-1.0-SNAPSHOT.jar<br />
</pre>

# acme-transfer
Parent/root pom

##Modules
### rest-server
Factory and Server interfaces to decouple jaxws server

### jersey-server
Basic jersey server used by sample.

### sample
Sample project to delivering the exercise
####Assumptions
1. Account number is assumed to be unique ideally something like an iban
2. That a accounts only hold single currencies and transfers must be of that currency

#### Notes

<pre>
If during start up there's a port conflict  
com.acme.sample.rest.Main.SERVER_URI 

com.acme.sample.rest.Main is also the class to run in case you're running from an IDE. 
</pre>


