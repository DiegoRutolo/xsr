#!/usr/bin/env bash

# Crea un pedido usando los argumentos. El primero es el cliente y el segundo es la pieza

if [[ $# -lt 2 ]]; then
	echo "Uso: crea.sh N_CLIENTE N_PIEZA [PRECIO] [ESTADO]"
	echo
	echo "0 sería el primero, 1 el segundo, etc..."
	exit
fi

if ! [[ $1 =~ ^[0-9]+$ ]]; then
	echo "Indica un número"
	exit 1
fi

if ! [[ $2 =~ ^[0-9]+$ ]]; then
	echo "Indica un número"
	exit 1
fi

if ! [[ $3 =~ ^[0-9]+\.*[0-9]*$ ]]; then
	echo "El precio debe ser un número"
	exit 1
fi

ID_CLIENTE=$(../clientes/get.sh | jq -r ".data[$1].id")

ID_PEZA=$(../pezas/get.sh | jq -r ".data[$2].id")

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
					"idCliente": "'$ID_CLIENTE'",
					"idPeza": "'$ID_PEZA'",
					"pvp": "'$3'",
					"estado": "'$4'"
				}
			}
		}
	}' \
	localhost:10097