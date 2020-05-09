#!/usr/bin/env bash

# Muestra todas las piezas

curl -H "Content-Type: application/json" \
	-d '{
		"usuario": {
			"rol": "xerente"
		},
		"operacion": {
			"apartado": "x_pezas",
			"tipo": "get"
		}
	}' \
	localhost:10097 | jq
