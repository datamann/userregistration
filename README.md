# User Registration

A Spring Boot REST API and Vaadin Hilla frontend for user registration.

## Description

The User Registration feature has no practical use, this project is created to demonstrate the difference between "IN APP" authorizations and "EXTERNAL" or policy-based authorizations.

Versions:
* The branch "in_app_authorization" will use "internal" authorizations only, based on roles. These roles are injected into the authentication JWT token from the external authentication service, Keycloak.
* The branch "external_authorization" will use OpenPoliyAgent https://www.openpolicyagent.org/ for authorizations. The application will receive roles and attributes from Keycloak, and forward the data to OPA for authorization approval. OPA as a PDP will only make an "allow/not allow" decision based on data it receives from Keycloak via the application. The enforcement of the policy is done by the application.


## Getting Started

### Dependencies

* The front end is based on Vaadin Hilla/React.
* Backend is based on Spring Boot.
* Database is docker-based PostGresSQL. For database administration, PGAdmin4 is available.
* For authentication, Docker-based Keycloak is used, and configured with users and roles.
* For external authorization, a Docker-based Open Policy Agent (OPA) is used.
* Docker needs to be installed.

### Installing

* Look in file docker-compose.yml for Docker services and ports!
* To prevent conflict with "localhost" names and services, you might want to edit your localhost file and give e.g. keycloak a hostname e.g. "127.0.0.1 mykeycloak".
* After starting docker containers the first time, log into keycloak "http://mykeycloak:8080/admin/master/console/"
* You don't have to but I recommend that you create a new Keycloak realm.
* In the new realm, start by creating a Keycloak client. You will need to change clientID and clientSecret in the Spring Boot application properties file.
* Create Keycloak users and realm roles.
* Goto (In keycloak): Clients -> "client id" (Newly created client from the step above) -> Click Tab "Client scopes" -> Click on "<client id>-dedicated" -> Click "Add Mapper" -> From predefined mappers -> Select "realm roles", Click "ADD" -> Then click "realm roles" -> In "Token claim name" add: "realm_access\.roles" -> Disable "Add to ID token", Enable "Add to access token" and "Add to userinfo".

### Executing program

* From a terminal, run docker-compose up -d
* Run Spring Boot app from e.g. VSCode

## Authors

Idea by and coding is done by:
Stig Børje Sivertsen
ex. [@StigSivertsen](https://twitter.com/stigsivertsen)

## Version History

* 1.0

## License

This project is licensed under the GNU GPLv3 License - see the LICENSE.md file for details

## Acknowledgments

Inspiration, code snippets, etc.
* [Spring.io](https://spring.io/)
* [Vaadin](https://vaadin.com/)
* [Hilla](https://hilla.dev/)
* [OpenPoliyAgent](https://www.openpolicyagent.org/)
