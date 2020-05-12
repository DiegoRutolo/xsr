#!/usr/bin/env bash

# Elimina un pedido

if [[ $# -lt 2 ]]; then
	echo "Uso: crea.sh ID_CLIENTE ID_PEZA"
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

curl -v -H "Content-Type: application/json" \
	-d '{
		"usuario": {
			"rol": "xerente"
		},
		"operacion": {
			"apartado": "x_pedidos",
			"tipo": "delete",
			"selec": {
				"idCliente": "'$1'",
				"idPeza": "'$2'"
			}
		}
	}' \
	localhost:10097 | jq
