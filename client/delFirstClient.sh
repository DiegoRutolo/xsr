#!/usr/bin/env bash

# Elimina el primer cliente

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
jq -r '.data[0].id')

echo Cambiando $ID

curl -v -H "Content-Type: application/json" \
	-d '{
		"usuario": {
			"rol": "xerente"
		},
		"operacion": {
			"apartado": "x_clientes",
			"tipo": "delete",
			"selec": {
				"id": "'$ID'"
			}
		}
	}' \
	localhost:10097
