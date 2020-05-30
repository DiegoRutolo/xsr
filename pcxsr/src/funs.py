#!/usr/bin/env python3

import os
import tkinter as tk
import json
import requests
from . import objetos

def listClientesInventados():
	listaClientes = []

	c1 = objetos.Cliente(4, "Carrero", "888888888", "blanco@espanha.up", "Primer astronauta español")
	c2 = objetos.Cliente(675, "Paquito", "55555555", "chocolate@espanha.up", "Mejor chocolatero desde 1936")

	listaClientes.append(c1)
	listaClientes.append(c2)

	return listaClientes

def getReqObjGet(apartado="x_clientes", rol="xerente"):
	return {
		"usuario": {
			"rol": rol
		},
		"operacion": {
			"apartado": apartado,
			"tipo": "get"
		}
	}

def listClientes():
	r = requests.post("http://localhost:10097", json=getReqObjGet())
	datos = json.loads(r.text)

	listaClientes = []
	try:
		for c in datos["data"]:
			listaClientes.append(objetos.Cliente(
				c["id"], c["nome"], c["tlf"], c["email"], c["notas"]
			))
	except:
		print("Error procesando datos recibidos")
	
	return listaClientes
