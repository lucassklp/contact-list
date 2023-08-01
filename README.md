# Contact list

Uma aplicação simples de lista de contato

## Executar

Há duas formas de executar a aplicação: Por docker, ou rodando o front e o back isoladamente.

### Utilizando o docker
Para executar com o docker, basta executar os seguintes comandos:

```
docker compose build
docker compose up -d
```

Executando dessa forma, basta acessar [http://localhost:8080/](http://localhost:8080/) e o sistema irá funcionar normalmente.

### Executando isoladamente
Para executar o frontend basta ir até a pasta /frontend e executar o seguinte comando:

```
npm ci
npm start
```

Para executar o backend, basta abrir o projeto localizado na pasta /backend com sua IDE e executar a função main. Ou compilar os projetos utilizando o maven, e executando com o java, utilizando os seguintes comandos: 

```
mvn package
cd /target/
java -jar contact-list-0.0.1-SNAPSHOT.jar
```

Executando dessa forma, o frontend estará disponível em [http://localhost:4200/](http://localhost:4200/) e o backend em [http://localhost:8080/](http://localhost:8080/)

## Observação importante
Ao executar isoladamente, é necessário configurar o banco de dados local (application.yml). Por isso, recomenda-se fortemente utilizar a versão do docker, pois já está com tudo configurado. 

## Swagger

Com o projeto executando, é possível ver a documentação do swagger acessando [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)