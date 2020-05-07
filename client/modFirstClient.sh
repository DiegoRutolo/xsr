#!/usr/bin/env bash

# Modifica el teléfono del primer cliente

ID=$(curl -H "Content-Type: application/json" \
	-d '{
		"usuario": {
			"rol": "xerente"
		},
		"operacion": {
			"apartado": "x_clientes",
			"tipo": "get",
			"datos": {
				"nome": "Pepito"
			}
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
			"tipo": "update",
			"selec": {
				"id": "'$ID'
			},
			"datos": {
				"tlf": "+27888888888"
			}
		}
	}' \
	localhost:10097 | jq
