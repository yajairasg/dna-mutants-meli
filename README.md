# API REST ADN MUTANTE 🦸 - CHALLENGE MERCADO LIBRE

## Descripción de la prueba

Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar contra los X-Men. Se requiere desarrollar un programa, Java / Golang / C-C++ / Javascript (node) / Python / Ruby), que detecte si un humano es mutante basándose en su secuencia de ADN.

Premisas:
- La secuencia del ADN esperado es un array de Strings que representan cada fila de una tabla de (NxN). 
- Las letras de los Strings solo pueden ser: (A,T,C,G), las cuales representa cada base nitrogenada del ADN.
- Un humano es mutante cuando se encuentran más de una secuencia de cuatro letras iguales, de forma oblicua, horizontal o vertical.

Ejemplo (caso mutante):
string[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};

## Desafíos 🚀

### Nivel 1 ⭐️
Desarrollar un programa (en cualquier lenguaje de programación) que cumpla con el método pedido por Magneto.

### Nivel 2 ⭐️⭐️
Crear una API REST, hostear esa API en un cloud computing libre (Google App Engine, Amazon AWS, etc), crear el servicio “/mutant/” en donde se pueda detectar si un humano es mutante enviando la secuencia de ADN mediante un HTTP POST con un Json el cual tenga el siguiente formato:
POST → /mutant/
{ “dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"] }
En caso de verificar un mutante, debería devolver un HTTP 200-OK, en caso contrario un 403-Forbidden.

### Nivel 3 ⭐️⭐️⭐️
Anexar una base de datos, la cual guarde los ADN’s verificados con la API.
Solo 1 registro por ADN.
Exponer un servicio extra “/stats” que devuelva un Json con las estadísticas de las verificaciones de ADN: 
{“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4}
Tener en cuenta que la API puede recibir fluctuaciones agresivas de tráfico (Entre 100 y 1 millón de peticiones por segundo).
Test-Automáticos, Code coverage > 80%.

## Solución API REST

Para la Api Rest, se desarrolló un proyecto Maven usando Java como lenguaje de programación, Spring Boot como framework y MySQL para la base de datos.
La estructura base del proyecto se generó a traves de la herramienta Spring Inicializer https://start.spring.io/


## Consumo de servicios 🔧

### Pre-requisitos 📋

Se recomienta tener instalado el aplicativo Postman, para el consumo de los servicios.
Puede descargar el aplicativo en este link: https://www.postman.com/downloads/

### probar los endpoints 📋

Para el consumo de los servicios de la Api Mutantes a traves de Postman:
1. Realice la importación de la colección: 
2. Ingrese a la carpeta ApiMutants
4. Seleccione la Petición POST /api/mutant
5. Clic en botón Send para ralizar el consumo del servicio
4. Seleccione la Petición GET /api/stats
5. Clic en botón Send para ralizar el consumo del servicio

## Construido con 🛠️

* [Spring Boot](https://spring.io/projects/spring-boot) - El framework usado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Spring Tools 4](https://spring.io/tools) - IDE

## Autor ✒️

* **Maria Yajaira Sanabria Guerrero** - *Development* - [yajairasg](https://github.com/yajairasg)


## Agradecimientos 🎁

* Gracias a mi familia por regalarme de su tiempo para desarrollar este proyecto 👨‍👩‍👧‍👦
* Gracias Mercado Libre por darme la oportunidad de participar en el Challenge 🤓.
