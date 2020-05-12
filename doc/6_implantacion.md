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

-------------------------

## USO

Toda a interación co sistema realízase a través de *clientes xsr* polo que haberá que consultar a documentación específica de cada un deles.

Neste repositorio inclúese un conxunto de scripts que poden funcionar como cliente de proba para demostrar o funcionamento do servidor.
