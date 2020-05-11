#!/usr/bin/env bash

# Modifica el teléfono del primer cliente

if [[ $# -eq 0 ]]; then
	echo "Uso: mod.sh N_CLIENTE"
	echo
	echo "0 sería el primero, 1 el segundo, etc..."
	exit
fi

if ! [[ $1 =~ ^[0-9]+$ ]]; then
	echo "Indica un número"
	exit 1
fi

ID=$(curl -H "Content-Type: application/json" \
	-d '{
		"usuario": {
			"rol": "xerente"
		},
		"operacion": {
			"apartado": "x_clientes",
			"tipo": "get"
		}
	}' \
	localhost:10097 | \
jq -r '.data['$1'].id')

echo Cambiando $ID

curl -v -H "Content-Type: application/json" \
	-d '{
		"usuario": {
			"rol": "xerente"
		},
		"operacion": {
			"apartado": "x_clientes",
			"tipo": "update",
			"selec": {
				"id": "'$ID'"
			},
			"datos": {
				"tlf": "+27888888888"
			}
		}
	}' \
	localhost:10097 | jq
