#!/usr/bin/env bash

# Crea una reparacion para un cliente con una pieza

if [[ $# -lt 2 ]]; then
	echo "Uso: $0 ID_CLIENTE ID_PEZA"
	echo
	exit
fi

if ! [[ $1 =~ ^-*[0-9]+$ ]]; then
	echo "Indica un número"
	exit 1
fi

if ! [[ $2 =~ ^-*[0-9]+$ ]]; then
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
			"tipo": "create",
			"datos": {
				"reparacion": {
					"ini": "",
					"fin": "",
					"n_horas": "2",
					"completa": "false",
					"causa": "Peza rota",
					"solucion": "Cambiar peza",
					"notas": "",
					"id_cliente": "'$1'",
					"ids_pezas": [
						"'$2'"
					]
				}
			}
		}
	}' \
	localhost:10097
