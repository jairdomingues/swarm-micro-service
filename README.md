# swarm-micro-service

WildFly Swarm Modules

### Run App

Antes de executar é necessário criar um DB no postgres 9.x com o nome de "swarm". 
Caso queria trocar o nome do BD, favor alterar em "projects-stages.yml"
 

``` sh

$ ./mvnw clean package \
  && java -jar target/swarm-micro-service-thorntail

```

## Usage


Exemplo autenticação e criação de token JWT ( token retorna no header da resposta tag 'Authorization Bearer: ...)

``` sh

curl -d '{"username": "admin@email.com.br", "password":"admin"}' -H "Content-Type: application/json" \
-X POST http://localhost:8080/api/v1/usuarios/autenticar

```
Exemplo lista de usuários

``` sh

curl -H "Content-Type: application/json" -H "Authorization: Bearer <TOKEN GERADO SERVICO AUTENTICAR>" localhost:8080/api/v1/usuarios/

```




