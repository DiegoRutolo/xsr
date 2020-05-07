#!/usr/bin/env bash

curl -v -H "Content-Type: application/json" \
	-d '{
		"usuario": {
			"rol": "xerente"
		},
		"operacion": {
			"apartado": "x_clientes",
			"tipo": "get",
			"datos": {}
		}
	}' \
	localhost:10097
