#!/usr/bin/env bash

# Muestra todos los pedidos

curl -H "Content-Type: application/json" \
	-d '{
		"usuario": {
			"rol": "xerente"
		},
		"operacion": {
			"apartado": "x_pedidos",
			"tipo": "get"
		}
	}' \
	localhost:10097 | jq
