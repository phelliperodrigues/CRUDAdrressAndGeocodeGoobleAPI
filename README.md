# CRUD de Endereço
[![SourceLevel](https://app.sourcelevel.io/github/phelliperodrigues/CRUDAdrressAndGeocodeGoobleAPI.svg)](https://app.sourcelevel.io/github/phelliperodrigues/CRUDAdrressAndGeocodeGoobleAPI)

Esse projeto foi desenvolvido com proposito de avaliação pela empresa STOOM.

### Como Executar o Projeto

Para rodar o projeto clone o repositorio e siga as instruções abaixo


```bash
cd CRUDAdrressAndGeocodeGoobleAPI/
```

```bash
mvn clean install
```

```bash
./mvnw package && java -jar target/address-0.0.1-SNAPSHOT.jar    
```
Também é possivel executar o projeto via docker

```bash
docker build -t "addres-api/dockerfile" .    
```

```bash
docker run --publish 8080:8080 --detach --name api addres-api/dockerfile
```

Para rodar a swith de teste execute o comando maven abaixo:
```bash
mvn test
```
### Postman Collenction
[Aqui](https://github.com/phelliperodrigues/CRUDAdrressAndGeocodeGoobleAPI/blob/master/Address.postman_collection.json)

### Construido com:

* Java 11
* Maven
* Spring Boot
* Lombok
* Spring Data JPA
* Database H2
* Spring Test
* JUnit Jupter

Ps: Model contruido suportando DSL(Domain Specific Languages)
