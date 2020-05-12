#!/usr/bin/env bash

# Crea un cliente de nombre Pepito

curl -v -H "Content-Type: application/json" \
	-d '{
		"usuario": {
			"rol": "xerente"
		},
		"operacion": {
			"apartado": "x_clientes",
			"tipo": "create",
			"datos": {
				"cliente": {
					"nome": "Pepito",
					"tlf": "666666666",
					"email": "pepito@pepitoweb.net",
					"notas": ""
				}
			}
		}
	}' \
	localhost:10097