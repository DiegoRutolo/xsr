#!/usr/bin/env bash

# Elimina una reparación

if [[ $# -eq 0 ]]; then
	echo "Uso: $0 ID_REPARACION"
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
			"tipo": "delete",
			"selec": {
				"id": "'$1'"
			}
		}
	}' \
	localhost:10097 | jq
