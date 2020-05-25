#!/usr/bin/env bash

# Modifica las notas de una reparacion

if [[ $# -eq 0 ]]; then
	echo "Uso: $0 ID_REPARACION [NOTA]"
	echo
	exit
fi

if ! [[ $1 =~ ^-*[0-9]+$ ]]; then
	echo "Indica un número"
	exit 1
fi

curl -v -H "Content-Type: application/json" \
	-d '{
		"usuario": {
			"rol": "xerente"
		},
		"operacion": {
			"apartado": "x_reparacions",
			"tipo": "update",
			"selec": {
				"id": "'$ID'"
			},
			"datos": {
				"notas": "'"$2"'"
			}
		}
	}' \
	localhost:10097 | jq
