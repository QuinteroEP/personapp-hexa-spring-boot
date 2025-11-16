# personapp-hexa-spring-boot
Plantilla Laboratorio Arquitectura Limpia

Instalar MariaDB en puerto 3307
Instalar MongoDB en puerto 27017

Ejecutar los scripts en las dbs

el adaptador rest corre en el puerto 3000
el swagger en http://localhost:3000/swagger-ui.html

Son dos adaptadores de entrada, 2 SpringApplication diferentes

Deben configurar el lombok en sus IDEs

Pueden hacer Fork a este repo, no editar este repositorio

## Docker
Levantar cada compose con:
<ul>
    <li>docker compose -f docker-compose-maria.yml up -d</li>
    <li>docker compose -f docker-compose-mongo.yml up -d</li>
</ul>

## Ejecutar
<ol>
    <li>Navegar a la carpeta Root del proyecto con CD</li>
    <li>Levantar los Compose</li>
    <li>Ejecutar la aplicacion <b>con /usr/bin/env /Library/Java/JavaVirtualMachines/amazon-corretto-11.jdk/Contents/Home/bin/java @/var/folders/h9/p5vdxgp90y3755thc80tspqh0000gn/T/cp_js8sr5rcwlcpwjkb54awnxmb.argfile co.edu.javeriana.as.personapp.PersonAppCli</b></li>
</ol>