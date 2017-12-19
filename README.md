# Comparador de Planilhas

Projeto desenvolvido para comparar duas planilhas e apontar diferenças nas células, suporte a extensões .xls e .xlsx

## Download e Instalação

### Pré Requisitos

Necessário ter instalado em sua máquina o [Maven](https://maven.apache.org/download.cgi) e [Java 8](http://www.oracle.com/technetwork/pt/java/javase/downloads/jre8-downloads-2133155.html)

Após clonar o projeto para sua máquina, acessar o diretório:
```
/comparador-planilhas/src/main/resources
```
No arquivo **application.properties**, alterar a chave **upload.path=** com o seu caminho para o upload dos arquivos.

### Instalando
```
mvn clean install
```

### Executando projeto
```
mvn spring-boot:run
```

## Linguagens e Frameworks

* Java 8
* Spring Boot
* Apache POI
* Maven
* Thymeleaf