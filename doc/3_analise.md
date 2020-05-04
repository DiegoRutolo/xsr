# REQUIRIMENTOS DO SISTEMA
Este documento describe os requirimentos para *XSR* especificando que funcionalidade ofrecerá e de que xeito.

## Descrición Xeral

XSR é un software, orientado a pequenos talleres de reparación, que axuda a manter certo control sobre a organización do inventario, reparacións para clientes e pezas.

A parte central da aplicación será o servidor, que leva asociada unha base de datos e garda toda a información necesaria.

O servidor cominícase cos clientes mediante una API REST, e os usuarios só interactúan cos clientes.

Por defecto, mensaxe que o cliente envíe o servidor debe ir firmado mediante clave asimétrica, de forma que só os clientes autorizados poidan operar.

-----------------------------------------

## Funcionalidades

### Operacións

Estas son as operacións que poden realizar os usuarios dende o cliente.

 * Rexistrar, editar e eliminar **clientes**:
	* nome, tlf, email, notas
 * Rexistrar, editar e eliminar **pezas**:
	* Codigo, proveedor, nome, foto, numero, precio, notas
 * Rexistrar, editar e eliminar **reparacións**:
	* Data ini, data fin, horas, completada(VF), causa, solución, cliente, pezas, notas
 * Asociar **pedidos** de clientes e pezas

### Configuración

Estos son parámetros de configuración xeral. Modifícanse mediante un arquivo de configuración no servidor. É necesario reiniciar para aplicar os cambios.

 * Servidor, nome, porto, usuairo e contrasinal da base de datos
 * Nivel de log
 * Activar ou desactivar a autentificación de clientes
 * Engadir ou eliminar claves públicas dos clientes autorizados
 * Engadir e eliminar roles.

## Roles

Por defecto existen 2 roles con ester permisos, pero é posible crear outros mediante a configuración.

> {\+, %, -} => {engadir, modificar, eliminar}

| Rol		| clientes	| pezas	| reparacións	| pedidos |
| --- 		| :---:		| :---:	| :---:			| :---:	|
| xerente	| +%-		| +%-	| +%-			| +%-	|
| currante	| +			| +%-	| +%			| +-	|


-------------------------------

## Requerimentos non funcionais

A continuación detállanse os requerimentos non funcionais máis importantes.

### Usabilidade

### Eficiencia

### Dependibilidade

### Seguridade

 * O servidor non realizará ningunha operación que non proveña dun cliente autentificado e autorizado
 * O servidor escribirá un *log* con todas as operación realizadas, incluídas data e cliente.
 * Os datos de clientes **nunca** serán accesibles de forma pública.
 * Toda comunicación cos clientes debe ir sobre SSL.
 * A base de datos debe estár cifrada.

### Entorno

### Organizacionales

### Desenvolvemento

### Regulatorios

### Éticos

### Lexislativos

 * O software debe cumplir todos os requisitos marcados pola [LOPD](https://www.boe.es/boe/dias/2018/12/06/pdfs/BOE-A-2018-16673.pdf) e o [RXPG](https://www.rgpd.es/)

---------------------------------

## Entorno operacional

A continuación están especificados os requerimentos de hardware se software para o servidor.

### Software

O servidor está implementado sobre Docker, polo que calquera plataforma soportada por éste será adecuada. Recoméndase Linux, xa que é a plataforma na que se leva o desenvolvemento.

### Hardware requerido

O software do servidor estará aloxado nun servidor con conexión de rede. Os requisitos mínimos de hardware son os mínimos para executar [Docker Engine](https://docs.docker.com/engine/install/). Na práctica son moi baixos, sendo posible instalalo incluso nunha [Raspberry Pi](https://www.raspberrypi.org/blog/docker-comes-to-raspberry-pi/).

----------------------------------------

## Interfaces externos

 * O servidor comunícase mediante os clientes a través de peticións HTTP.
 * Os clientes envían toda a información necesaria para a operación con cada petición.
 * O servidor envía as respostas en formato JSON.

-----------------------------------------

## Melloras futuras

A continuación detállanse algunhas posibles ideas para mellorar XSR.

 * Mellorar a seguridade
 * Aumentar o número de operacions
 * Implementación de clientes
 * Soporte para Docker Swarm para mellorar a escalabilidade
