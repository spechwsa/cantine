# Application WEB de test JAVA/JEE
Cette simple application permet la gestion de l'inscription d'élèves à une cantine.
Une simple interface WEB intégrée permet d'inscrire ou de désinscrire un éléve.
Une interface API REST est également fournie. (TODO) 

## Concept mise en oeuvre:
- DDD, BDD, TDD, Clean Architecture 

## Technologie / framework utilisé:
- Git
- JAVA/JEE
- Spring boot
- Micro Service REST (TODO)
- JWT (TODO)- Thymeleaf
- Cucumber Gherkin
- Mokito
- Docker

## Contrainte du projet:
Pas d'utilisation de framework dans la partie domaine

Utilisation du français dans la description Gherkin des tests.

La gestion de la sécurité grace à JWT et à un microservice dédié.(TODO)

La liste des élèves est fournie par un autre microservice.(TODO)

## Docker:
Déploiement docker.
docker run -d -p 5000:8080 spechwsa/test-java:0.0.1-SNAPSHOT

## Demo sur un serveur ikoula:
### Application déployé dans un serveur WildFly:
http://109.238.3.93:8080/cantine-0.0.1-SNAPSHOT/Cantine/InscrireEleves

### Application déployé dans un simple jar SPRING boot:
http://109.238.3.93:5000/Cantine/InscrireEleves
