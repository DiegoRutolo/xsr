#!/usr/bin/env bash

# Ejemplo de petición inválida

curl -v -H "Content-Type: application/json" \
	-d '{
		"usuario": {
			"rol": "xerente"
		},
		"operacion": {
			"apar
			et",
			"datos": {}
		}
	}' \
	localhost:10097
