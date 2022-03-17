# Mutant DNA - _Challenge Mercado Libre_

## Descripción de la prueba 🦸 

Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar contra los X-Men.

Se requiere desarrollar un programa, Java / Golang / C-C++ / Javascript (node) / Python / Ruby), que detecte si un humano es mutante basándose en su secuencia de ADN.

Premisas:
- La secuencia del ADN esperado es un array de Strings que representan cada fila de una tabla de (NxN)._ 
- Las letras de los Strings solo pueden ser: (A,T,C,G), las cuales representa cada base nitrogenada del ADN._
- Un humano es mutante cuando se encuentran más de una secuencia de cuatro letras iguales, de forma oblicua, horizontal o vertical.

```
Ejemplo (caso mutante):
string[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
```

## Desafíos 🚀

### Nivel 1 
Desarrollar un programa (en cualquier lenguaje de programación) que cumpla con el método pedido por Magneto.

### Nivel 2 
Crear una API REST, hostear esa API en un cloud computing libre (Google App Engine, Amazon AWS, etc), crear el servicio “/mutant/” en donde se pueda detectar si un humano es mutante enviando la secuencia de ADN mediante un HTTP POST con un Json el cual tenga el siguiente formato:

```
POST → /mutant/
{ 
  “dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"] 
}
```

En caso de verificar un mutante, debería devolver un HTTP 200-OK, en caso contrario un 403-Forbidden.

### Nivel 3 
Anexar una base de datos, la cual guarde los ADN’s verificados con la API.
Solo 1 registro por ADN.
Exponer un servicio extra “/stats” que devuelva un Json con las estadísticas de las verificaciones de ADN: 

```
{“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4}
```

Tener en cuenta que la API puede recibir fluctuaciones agresivas de tráfico (Entre 100 y 1 millón de peticiones por segundo).
Test-Automáticos, Code coverage > 80%.

## Solución API REST 👩🏻‍💻

Para dar solución a la prueba, se desarrolló un proyecto Maven usando Java como lenguaje de programación, Spring Boot como framework y MySQL para la base de datos.

Se crearon los siguientes servicios:

### Endpoint para validar si una secuencia de ADN es mutante

Estrategia 1: Validación del request (estructura del ADN)

* Se obtiene la longitud del listado de strings y se recorre cada posición del listado para verificar que los strings contengan la misma cantidad de elementos que el listado y es decir que se trate de una matriz de NxN. 
* Se usaron expresiones regulares para verificar que los strings solo contengan las letras A,C,T,G.

```
Si todo esta OK con la estructura del ADN se continua con la Estrategia 2, 
de lo contrario el servicio retorna 400-Bad Request.
```

Estrategia 2: Validar el ADN para saber si es mutante
* Se realiza el calculo de las coincidencias de manera horizontal (Estrategia 3), vertical (Estrategia 4) y oblicua (Estrategia 5). 
* Se retorna booleano y continua con la Estrategia 6.

Estrategia 3: Calcular coincidencias horizontales en listado del ADN
* Se calculan las coincidencias horizontales (Se usaron expresiones regulares para encontrar secuencias de cuatro letras iguales). 
_Si las coincidencias encontradas (de manera horizontal) son mayores iguales a 2, se continua con la Estrategia 6, debido a que es ADN Mutante y no es necesario continuar con las validaciones en las demas formas (vertical u oblicua)._

Estrategia 4: Calcular coincidencias verticales en listado del ADN
* Se recorre el listado del ADN, se construye un listado de los strings verticales y se calculan las coincidencias (Se usaron expresiones regulares para encontrar secuencias de cuatro letras iguales). 
_Si las coincidencias encontradas (de manera vertical + horizontal) son mayores iguales a 2 se continua con la Estrategia 6, debido a que es ADN Mutante y no es necesario continuar con la validacion de forma oblicua._

Estrategia 5: Calcular coincidencias oblicuas en listado del ADN
* Se recorre el listado del ADN, se construye un listado de los strings oblicuas y se calculan las coincidencias (Se usaron expresiones regulares para encontrar secuencias de cuatro letras iguales). 
_Se continua con la Estrategia 6_

Estrategia 6: Guardar la validacion del ADN en la base de datos
* Se guarda en base de datos el listado con del ADN verificado en un campo de tipo TEXT y si es Mutante se guarda en un campo VARCHAR con el texto "Mutante" de lo contrario "Humano".

```
Si es Mutante el servicio retorna 200-OK,
de lo contrario el servicio retorna 403-Forbidden.
```

### Endpoint para consultar las estadísticas de las verificaciones de ADN

Estrategia 1: Consulta de validaciones del ADN en base de datos (estructura del ADN)

* Se consulta a la base de datos la cantidad de validaciones del ADN de tipo Mutante
* Se consulta a la base de datos la cantidad de validaciones del ADN de tipo Humano

Estrategia 2: Calculo de estadísticas

* Se calcula el total de validaciones realizadas del ADN (sumatoria entre la cantidad de validaciones tipo Mutante y Humano)
* Calculo del ratio (la cantidad de validaciones tipo Mutante dividido entre el total de validaciones)

```
El servicio retorna 200-OK con un Json de las estadísticas de las verificaciones de ADN:  
{
  “count_mutant_dna”:0, 
  “count_human_dna”:0: 
  “ratio”:0.0
}
```

## Consumo de servicios

### Pre-requisitos

Se recomienta tener instalado el aplicativo Postman, para el consumo de los servicios.
Puede descargar el aplicativo en este link: https://www.postman.com/downloads/

### Endpoints

Para el consumo de los servicios de la Api Mutantes a traves de Postman:
1. Realice la importación de la colección
2. Ingrese a la carpeta ApiMutants
4. Para realizar el consumo del servicio que realiza la verificación del ADN, seleccione la Petición POST /api/mutant.
5. Clic en botón Send
6. Para realizar el consumo del servicio que consulta las estadísticas de verificaciones del ADN, seleccione la Petición GET /api/stats.
7. Clic en botón Send

## Construido con 🛠️

* [Java 8](https://docs.oracle.com/javase/8/) - Lenguaje de programación
* [Spring Initializr](https://start.spring.io/) - Creación del proyecto base
* [Spring Boot](https://spring.io/projects/spring-boot) - El framework usado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [MySQL](https://dev.mysql.com/downloads/mysql/) - Entorno de desarrollo
* [Spring Tools 4](https://spring.io/tools) - Base de datos

## Autor ✒️

* **Maria Yajaira Sanabria Guerrero** - *Desarrollo* - [yajairasg](https://github.com/yajairasg)


## Agradecimientos

* Gracias a mi familia por regalarme de su tiempo para desarrollar este proyecto 👨‍👩‍👧‍👦
* Gracias Mercado Libre por darme la oportunidad de participar en el Challenge 🤓.
