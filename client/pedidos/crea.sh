#!/usr/bin/env bash

# Crea un pedido usando los argumentos. El primero es el cliente y el segundo es la pieza

if [[ $# -lt 2 ]]; then
	echo "Uso: crea.sh ID_CLIENTE ID_PIEZA [PRECIO] [ESTADO]"
	echo
	exit
fi

if ! [[ $1 =~ ^-*[0-9]+$ ]]; then
	echo "Indica un número"
	exit 1
fi

if ! [[ $2 =~ ^-*[0-9]+$ ]]; then
	echo "Indica un número"
	exit 1
fi

if ! [[ $3 =~ ^[0-9]+\.*[0-9]*$ ]]; then
	echo "El precio debe ser un número"
	exit 1
fi

curl -v -H "Content-Type: application/json" \
	-d '{
		"usuario": {
			"rol": "xerente"
		},
		"operacion": {
			"apartado": "x_pedidos",
			"tipo": "create",
			"datos": {
				"pedido": {
					"idCliente": "'$1'",
					"idPeza": "'$2'",
					"pvp": "'$3'",
					"estado": "'"$4"'"
				}
			}
		}
	}' \
	localhost:10097