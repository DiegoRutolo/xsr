#!/usr/bin/env python3

import os
import tkinter as tk
import json
import requests
import traceback
import decimal as dc
from . import objetos as o

CON_STR = "http://localhost:10097"

#region Comunes

def getReqObj_Get(apartado, rol="xerente"):
	return {
		"usuario": {
			"rol": rol
		},
		"operacion": {
			"apartado": apartado,
			"tipo": "get"
		}
	}

def getReqObj_Create(apartado, nome_obx, obx, rol="xerente"):
	return {
		"usuario": {
			"rol": rol
		},
		"operacion": {
			"apartado": apartado,
			"tipo": "create",
			"datos": {
				nome_obx: obx.getDic()
			}
		}
	}

def getReqObj_Update(apartado, obx, rol="xerente"):
	return {
		"usuario": {
			"rol": rol
		},
		"operacion": {
			"apartado": apartado,
			"tipo": "update",
			"selec": {
				"id": obx.id
			},
			"datos": obx.getDic()
		}
	}

def getReqObj_Delete(apartado, id, rol="xerente"):
	return {
		"usuario": {
			"rol": rol
		},
		"operacion": {
			"apartado": apartado,
			"tipo": "delete",
			"selec": {
				"id": id
			}
		}
	}
#endregion

#region Clientes
def listClientesInventados():
	listaClientes = []

	c1 = o.Cliente(4, "Carrero", "888888888", "blanco@espanha.up", "Primer astronauta español")
	c2 = o.Cliente(675, "Paquito", "55555555", "chocolate@espanha.up", "Mejor chocolatero desde 1936")

	listaClientes.append(c1)
	listaClientes.append(c2)

	return listaClientes

def listClientes():
	listaClientes = []

	try:
		r = requests.post(CON_STR, json=getReqObj_Get(apartado="x_clientes"))
		datos = json.loads(r.text)
		for c in datos["data"]:
			listaClientes.append(o.Cliente(
				c["id"], c["nome"], c["tlf"], c["email"], c["notas"]
			))
	except:
		print("Error procesando datos")
	
	return listaClientes

def getCliente(id):
	for c in listClientes():
		if c.id == id:
			return c

def addCliente(cliente):
	r = requests.post(CON_STR, json=getReqObj_Create("x_clientes", "cliente", cliente))
	return r.status_code

def updateCliente(cliente):
	r = requests.post(CON_STR, json=getReqObj_Update("x_clientes", cliente))
	return r.status_code

def deleteCliente(cliente):
	r = requests.post(CON_STR, json=getReqObj_Delete("x_clientes", cliente.id))
	return r.status_code
#endregion

#region Pezas
def listPezasInvent():
	lista = []

	lista.append(o.Peza(
		id="23432", codigo="REW-3", prov="Misko", nome="Paleta",
		precio=dc.Decimal("69"), cantidade=45
	))

	lista.append(o.Peza(
		id="208", codigo="pp51b-plb", prov="Plumbubo Prime 51b",
		nome="Plumbus", precio=dc.Decimal("6.5"), cantidade=21,
		notas="No necesita descripción, todo el mundo sabe lo que hace"
	))

	return lista

def getPeza(id):
	for p in listPezas():
		if p.id == id:
			return p

def listPezas():
	listaPezas = []

	try:
		r = requests.post(CON_STR, json=getReqObj_Get(apartado="x_pezas"))
		datos = json.loads(r.text)
		for c in datos["data"]:
			listaPezas.append(o.Peza(
				c["id"], c["codigo"], c["prov"], c["nome"], c["foto"],
				dc.Decimal(c["precio"]), int(c["cantidade"]), c["notas"]
			))
	except:
		print(traceback.format_exc())
		print("Error procesando datos")
	
	return listaPezas

def addPeza(peza):
	r = requests.post(CON_STR, json=getReqObj_Create("x_pezas", "peza", peza))
	return r.status_code

def updatePeza(peza):
	r = requests.post(CON_STR, json=getReqObj_Update("x_pezas", peza))
	return r.status_code

def deletePeza(peza):
	r = requests.post(CON_STR, json=getReqObj_Delete("x_pezas", peza.id))
	return r.status_code
#endregion

#region Pedidos
def getReqObj_UpdatePedido(pedido, rol="xerente"):
	return {
		"usuario": {
			"rol": rol
		},
		"operacion": {
			"apartado": "x_pedidos",
			"tipo": "update",
			"selec": {
				"idCliente": pedido.client_id,
				"idPeza": pedido.peza_id
			},
			"datos": pedido.getDic()
		}
	}

def getReqObj_DeletePedido(pedido, rol="xerente"):
	return {
		"usuario": {
			"rol": rol
		},
		"operacion": {
			"apartado": "x_pedidos",
			"tipo": "delete",
			"selec": {
				"idCliente": pedido.client_id,
				"idPeza": pedido.peza_id
			}
		}
	}

def getPedido(client_id, peza_id):
	for p in listPedidos():
		if p.client_id == client_id and p.peza_id == peza_id:
			return p

def listPedidos():
	listaPedidos = []

	try:
		r = requests.post(CON_STR, json=getReqObj_Get(apartado="x_pedidos"))
		datos = json.loads(r.text)
		for ped in datos["data"]:
			listaPedidos.append(o.Pedido(
				ped["idCliente"], ped["idPeza"],
				dc.Decimal(ped["pvp"]), ped["estado"]
			))
	except:
		print(traceback.format_exc())
		print("Error procesando datos")
	
	return listaPedidos

def addPedido(pedido):
	r = requests.post(CON_STR, json=getReqObj_Create("x_pedidos", "pedido", pedido))
	return r.status_code

def updatePedido(pedido):
	r = requests.post(CON_STR, json=getReqObj_UpdatePedido(pedido))
	return r.status_code

def deletePedido(pedido):
	r = requests.post(CON_STR, json=getReqObj_DeletePedido(pedido))
	return r.status_code
#endregion