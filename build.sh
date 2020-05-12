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
cp config/xsrd.conf $CONFIG_FILE && \
echo "Copiado archivo de configuración a $CONFIG_FILE"

# Compilar la imagen
docker build -t xsr:latest . && \
echo "Compilada imagen de XSR"

# Crear la red
docker network create $NETNAME && \
echo "Creada la red $NETNAME"

# Lanzar el contenedor de mysql
docker run -d \
	--network $NETNAME \
	--network-alias xsr-mysql \
	-v xsr-mysql-data:/var/lib/mysql \
	-e MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD \
	-e MYSQL_DATABASE=$MYSQL_DATABASE \
	-p 3306:3306 \
	--name xsr-mysql \
	mysql:5.7 && \
echo "Lanzado contenedor MySQL"

# Crear la base de datos
docker cp config/xsrdb.sql xsr-mysql:/tmp/xsrdb.sql
echo "Creando base de datos..."
sleep 10
if docker exec xsr-mysql /bin/bash -c "mysql -u root -p$MYSQL_ROOT_PASSWORD < /tmp/xsrdb.sql" 2> /dev/null; then
	echo "DB creada"
else
	sleep 10
	docker exec xsr-mysql /bin/bash -c "mysql -u root -p$MYSQL_ROOT_PASSWORD < /tmp/xsrdb.sql" && echo "DB creada"
	#echo "Erro al crear DB. Puedes crearla manualmente con el comando 'docker exec xsr-mysql /bin/bash -c \"mysql -u root -pLA_CONTRASEÑA < /tmp/xsrdb.sql'"
fi


# Lanzar el contenedor de XSR
docker run -d -p 10097:10097 \
	--network $NETNAME \
	--network-alias xsr-srv \
	-v $CONFIG_FILE:/etc/xsrd.conf \
	--name xsr-srv \
	xsr && \
echo "Lanzado contenedor XSR"
