#!/usr/bin/env bash

# Ruta absoluta del HOST donde se copia el archivo de configuración
CONFIG_FILE=$HOME/.config/xsrd.conf
# Nombre de la red de docker
NETNAME=xsr-net

rm $CONFIG_FILE
echo "Eliminado config"
docker rm -f xsr-srv xsr-mysql 
echo "Eliminados contenedores"
docker network rm $NETNAME
echo "Eliminada red"
