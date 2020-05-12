#!/usr/bin/env bash

# Elimina una pieza

if [[ $# -eq 0 ]]; then
	echo "Uso: delete.sh N_PIEZA"
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
			"apartado": "x_pezas",
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
			"apartado": "x_pezas",
			"tipo": "delete",
			"selec": {
				"id": "'$ID'"
			}
		}
	}' \
	localhost:10097 | jq
