#!/usr/bin/env bash

# Ruta absoluta del HOST donde se copia el archivo de configuración
CONFIG_FILE=$HOME/.config/xsrd.conf
# Contraseña de root del contenedor MySQL
MYSQL_ROOT_PASSWORD=abc123.
# Nombre de la base de datos
MYSQL_DATABASE=xsr
# Nombre de la red de docker
NETNAME=xsr-net



# Copiar el conf
cp config/xsrd.conf $CONFIG_FILE
echo "Copiado archivo de configuración a $CONFIG_FILE"

# Compilar la imagen
docker build -t xsr:latest .
echo "Compilada imagen de XSR"

# Crear la red si no existe
if docker network ls | grep $NETNAME; then
	echo "La red $NETNAME ya existe"
else
	docker network create $NETNAME
	echo "Creada la red $NETNAME"
fi

# Lanzar el contenedor de mysql
docker run -d \
	--network $NETNAME \
	--network-alias xsr-mysql \
	-v xsr-mysql-data:/var/lib/mysql \
	-e MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD \
	-e MYSQL_DATABASE=$MYSQL_DATABASE \
	--name xsr-mysql \
	mysql:5.7
echo "Lanzado contenedor MySQL"

# Lanzar el contenedor de XSR
docker run -d -p 10097:10097 \
	--network $NETNAME \
	--network-alias xsr-srv \
	-v $CONFIG_FILE:/etc/xsrd.conf \
	--name xsr-srv \
	xsr
echo "Lanzado contenedor XSR"