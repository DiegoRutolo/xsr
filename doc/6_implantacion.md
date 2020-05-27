# IMPLANTACIÓN


## INSTALACIÓN

Esta sección describe cómo instalar XSR. Existen diferentes formas, cada unha delas explicada no seu apartado.

### Simple

> Requerimentos: Docker

Este método descarga o proxecto e utiliza o script incluído para compilar o programa e lanzar 2 Docker que conteñen a base de datos e a aplicación.

Para cambiar os valores por defecto podes editar o arquivo `build.sh` e `config/xsrd.conf`. Asegúrate de que teñan o mesmo valor en ambolos dous.

> **CAMBIA O CONTRASINAL POR DEFECTO**

```
git clone https://gitlab.iessanclemente.net/damo/a16diegoar.git
cd a16diegoar
bash ./build.sh
```

Este método está recomendado para situacións na que a eficiencia no é demasiado importante, como probas ou uso a moi pequena escala.

### Base de datos a parte

> Requerimentos: Docker e servidor MySQL configurado.

Este método pódese utilizar cando xa existe un servidor MySQL configurado.

1. Editar o arquivo de configuración (`config/xsrd.conf`) escribindo os datos de acceso a MySQL (IP, porto, usuario, contrasinal)

2. Crear a base de datos e executar o script en `config/xsrdb.sql` para crear as táboas.

3. Copiar o arquivo de configuración ó seu destino. Por defecto recoméndase `$HOME/.config/xsrd.conf`

4. Compilar e lanzar o contenedor
```
docker build -t xsr:latest .

docker run -d -p 10097:10097 -v $HOME/.config/xsrd.conf:/etc/xsrd.conf --name xsr xsr
```

### Sin Docker

> Requerimentos: Servidor MySQL, Maven, permiso para escribir en `/etc`.

Nos anteriores métodos a compilación do código fonte, a xestión de dependencias e outros detalles quedan encapsulados en Docker, de forma que o usuario non necesita preocuparse. Con éste método esa capa de abstracción desaparece, polo que é mais fácil que algo non funcione correctamente.

A única situación na que se reomenda é durante o desenvolvemento, para axilizar a compilación.

1. Copiamos o arquivo `config/xsrd.conf` a `/etc/xsrd.conf` e editamos os valores de acceso a MySQL

2. Compilamos con Maven
```
mvn clean package
```

3. Executamos o jar
```
java -jar target/xsr-1.0-SNAPSHOT.jar
```
--------------

## ADMINISTRACIÓN

Sendo un sistema tan sixelo apena é necesario mantemento.

Cando se reinicia o servidor hai que levantar os containers con `docker start`. 
Pódese consultar o log con `docker logs xsr`.

As copias de seguridade dependen do método de instalación. O primeiro utiliza [volumes de docker](https://docs.docker.com/storage/volumes/), os demais dependen de cada instalación particular de MySQL.

### Volume de datos

Os datos da base de datos están gardados nun volume chamado **xsr-mysql-data**.

Eliminar o contenedor non elimina os datos, de forma que poden ser restaurados mais adiante nun contenedor diferente, de ser necesario.

Para eliminalos permanente mente pódese utilizar o comando
`docker volume rm xsr-mysql-data` ou pasar o argumento `--db` ó script `cleanup.sh`.

### Actualizar

Neste momento a única forma de instalar unha nova versión de XSR é desinstalando a anterior.

### Desinstalación

Pódese utilizar o script `cleanup.sh` para eliminar todos os arquivos de XSR do sistema.

-------------------------

## USO

Toda a interación co sistema realízase a través de *clientes xsr* polo que haberá que consultar a documentación específica de cada un deles.

Este repositorio inclúe 2 clientes:

#### test-client

É un conxunto de scripts bash que utilizan os comandos **curl** e **jq** para realizar todas as posibles operacións.

Pódense atopar no [directiorio client](client/).

#### PCXSR

É un cliente escrito en Python utilizando a librería Tkinter.

A documentación completa deste cliente está [no seu directorio](pcxsr/).

------------------

## Xestión de incidencias

A continuación lístanse os problemas mais comúns e unha serie de puntos a comprobar.

En todos os casos unha das mellores formas de ver posibles causas de problemas é co comando `docker logs xsr`

### Problemas de conexión coa DB

+ Usuario e contrasinal en `xsrd.conf` e `build.sh`.
    + En caso de que a do build script sexa incorrecta a mellor opción en cambiar a variable de entorno *MYSQL_ROOT_PASSWORD* do contenedor *xsr-mysql*.
+ Resposta de pings entre hosts

### Problemas de conexión co cliente

+ Pings
+ Tamaño dos paquetes
    + Algúns clientes, entre eles *curl*, utilizan a cabeceira HTTP [Expect](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Expect) cando os datos a enviar son moi grandes. Esto pode causar fallos, por exemplo, cando se intenta enviar unha imaxe codificada en base64.

-----------------

## Protección de datos

Toda a información está gardada exclusivamente na base de datos, polo que, igual que coas copias de seguridade, se queremos encriptala ou securizala doutras formas, é aqui onde se debe facer.

No caso de utlizar a base de datos predeteminada a información está gardada nun volume de docker. Hai unha descrición detallada mais arriba, na sección de *Administración*

Actualmente, a mellor forma de controlar o acceso ó sistema é securizar a rede local. En versións futuras implementaráse cifrado na comunicación cliente-servidor.