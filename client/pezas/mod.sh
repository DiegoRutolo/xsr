#!/usr/bin/env bash

# Modifica el precio de una pieza

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
			"tipo": "update",
			"selec": {
				"id": "'$ID'"
			},
			"datos": {
				"precio": "99.99"
			}
		}
	}' \
	localhost:10097 | jq
