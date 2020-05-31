#!/usr/bin/env python3

import os
import tkinter as tk
import json
import requests
from . import objetos

CON_STR = "http://localhost:10097"

def listClientesInventados():
	listaClientes = []

	c1 = objetos.Cliente(4, "Carrero", "888888888", "blanco@espanha.up", "Primer astronauta español")
	c2 = objetos.Cliente(675, "Paquito", "55555555", "chocolate@espanha.up", "Mejor chocolatero desde 1936")

	listaClientes.append(c1)
	listaClientes.append(c2)

	return listaClientes

def getReqObj_Get(apartado="x_clientes", rol="xerente"):
	return {
		"usuario": {
			"rol": rol
		},
		"operacion": {
			"apartado": apartado,
			"tipo": "get"
		}
	}

def getReqObj_UpdateCliente(cliente, rol="xerente"):
	return {
		"usuario": {
			"rol": rol
		},
		"operacion": {
			"apartado": "x_clientes",
			"tipo": "update",
			"selec": {
				"id": cliente.id
			},
			"datos": cliente.getDic()
		}
	}

def listClientes():
	listaClientes = []

	try:
		r = requests.post(CON_STR, json=getReqObj_Get())
		datos = json.loads(r.text)
		for c in datos["data"]:
			listaClientes.append(objetos.Cliente(
				c["id"], c["nome"], c["tlf"], c["email"], c["notas"]
			))
	except:
		print("Error procesando datos")
	
	return listaClientes

def updateCliente(cliente):
	r = requests.post(CON_STR, json=getReqObj_UpdateCliente(cliente))
	return r.status_code