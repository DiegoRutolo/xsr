# XSR - Xestor Simple de Reparacións

## Descripción

XSR é un software, orientado a pequenos talleres de reparación, que axuda a manter certo control sobre a organización do inventario, reparacións para clientes e pezas.

A aplicación completa está formada por un servidor e múltiples clientes.

Este repositorio contén a documentación do servidor e as especificacións da comunicación. Cada cliente que se desenvolva terá un repositorio propio.

## Instalación / Posta en marcha

XSR é unha aplicación basada en [Docker](https://www.docker.com/), polo que a instalación é bastante sinxela.

Proporcionase un script que utiliza [Maven](https://maven.apache.org/) para compilar, executar os tests e empaquetar. A continuación lanza 2 contenedores (base de datos e servidor) e conecta a rede e os arquivos de configuración. 

Pero non necesitas saber todo esto para instalala.

Para cambiar os valores por defecto podes editar o arquivo `build.sh` e `config/xsrd.conf`. Asegúrate de que teñan o mesmo valor en ambolos dous.

> **CAMBIA O CONTRASINAL POR DEFECTO**

### Linux

Para instalar XSR nun sistema Linux podes usar o script `build.sh`:


 ```
 git clone https://gitlab.iessanclemente.net/damo/a16diegoar.git
 cd a16diegoar
 chmod +x build.sh cleanup.sh && ./build.sh
 ```

 Podes usar o script `cleanup.sh` para eliminar todo rastro da aplicación: rede, contenedores e config.

### Outros

Aínda que outros sitemas non teñan soporte, deberías poder instalar XSR sen moitos problemas. Podes ver o script de instalación `build.sh` e adaptar os comandos en orden para o teu sistema.

Por exemplo, para Windows, o último comando que lanza o contenedor do servidor podería ser algo así:
```
docker run -d -p 10097:10097 --network $NETNAME --network-alias xsr-srv -v config\xsrd.conf:%APPDATA%\Local\xsrd.conf --name xsr-srv xsr
```


## Uso

Sendo unha aplicación docker toda a interación será a través desta ferramenta.

A continuación detallanse os comandos mais interesantes:
```
docker logs xsr-srv			# Consultar os logs

docker stop xsr-srv xsr-mysql		# Parar os contenedores
docker start xsr-mysql xsr-srv		# Iniciar

docker ps -a		# Ver todos os contenedores
docker exec -it xsr-mysql /bin/bash -c 'mysql -p xsr'		# Executar MySQL de forma interactiva.
```

### Configuración

A configuración do servidor realízase a través dun arquivo chamado `xsrd.conf`. Por defecto localízase en `~/.config/xsrd.conf`, pero pódese cambiar modificando `build.sh` antes de executalo.


## Sobre o autor

Eu son Diego Antelo Rútolo e son Técnico en Sistemas Microinformáticos e Redes e Técnico superior en Desenvolvemento de Aplicacións Multiplataforma.

Podes revisar os meus proxectos na web [rutolo.eu](http://rutolo.eu) ou en [GitHub](https://github.com/DiegoRutolo)

Para contacto: diegorutolo{arroba}gmail{punto}com


## Licencia

Este proxecto utiliza unha licencia **GPL-3** (GNU General Public License v3).

Podes atopar o texto completo no arquivo [LICENSE](./LICENSE).
Tamén hai traduccións [*non oficiais*](https://www.gnu.org/licenses/translations.html) e un [resumo](https://tldrlegal.com/license/gnu-general-public-license-v3-(gpl-3)).


## Índice

1. [Idea](doc/1_idea.md)
2. [Necesidades](doc/2_necesidades.md)
3. [Análise](doc/3_analise.md)
4. [Deseño](doc/4_deseno.md)
5. [Planificación](doc/5_planificacion.md)
6. [Implantación](doc/6_implantacion.md)


## Guía de contribución

> *TODO*: Tratándose de un proyecto de software libre, es muy importante que expongas cómo se puede contribuir con tu proyecto. Algunos ejemplos de esto son realizar nuevas funcionalidades, corrección y/u optimización del código, realización de tests automatizados, nuevas interfaces de integración, desarrollo de plugins, etc. etc. Sé lo más conciso que puedas.

## Links

> *TODO*: Enlaces externos y descipciones de estos enlaces que creas conveniente indicar aquí. Generalmente ya van a estar integrados con tu documentación, pero si requieres realizar un listado de ellos, este es el lugar.