# ONLINE SPORTS BETTIN API

## 1. DESCRIPTION

Rest Api that allows to carry out sports betting and then execute them after introduction of event result.

## 2. TECHNOLOGIES
- Spring Boot
- Hibernate
- Spring Security

## 3. DEMO

- [Api demo](https://online-sports-betting.herokuapp.com/users)
- [Endpoints documentation](https://online-sports-betting.herokuapp.com/swagger-ui.html)
- [Demo with simple front-end](https://osb-front.herokuapp.com)

## 4. HOW TO RUN

To run this project in your IDE you need to have lombok plugin and enable annotation processing. If there will be problems with building change 
```
compileOnly('org.projectlombok:lombok')
annotationProcessor('org.projectlombok:lombok')
```
to
```
compile('org.projectlombok:lombok')
```
in build.gradle. Project is cotencted to online database then you need Internet connection.

## 5. FRONT-END

Simple front-end to this application - https://github.com/aleksanderkot00/Online-Sports-Betting-Front 

## 6. FUTURE PLANS

- Improvement security by JWT token
- Improvenet of front-end
