#!/usr/bin/env bash

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
					"nome": "Maria del Carmen",
					"tlf": "678910254",
					"email": "maricarmen@yahoo.es",
					"notas": "Prefiere que la llamen Maricarmen"
				}
			}
		}
	}' \
	localhost:10097