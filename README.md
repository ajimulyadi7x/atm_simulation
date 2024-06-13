# ATM SIMULATION
CLI study case project to simulate various ATM features, such as Withdrawal and Fund Transfer. 

## Overview
this study case project is created to make knownledge about SOLID Principle and OOP better

## atm_simulation_01
it is implementing SOLID Principle using CLI as input channel.
to run this project there are have some requirements stack that should be prepared :
- apache-maven-3.0.5 or other highest version
- Java 1.8
- Intelij IDEA or other IDEA

after all requirements are ready, follow below steps :
1. build this project using maven command, mvn install, it will generate jar file
2. go to folder target in this project and run this the jar file, here is the command :
   java -jar atm_simulation-1.0-SNAPSHOT.jar
3. you will see account number field on the console.
## DATA
Authenticate by using account and pin number below

| Account Number | PIN    | Name        | Balance |
| -------------- | ------ | ----------- | ------- |
| 112233         | 012108 | John Doe    | $100    |
| 112244         | 932012 | Jane Doe    | $30     |
