#!/usr/bin/env python3

import os
import tkinter as tk
import decimal as dc
from . import funs as f

class Cliente():
	def __init__(self, id=0, nome="", tlf="", email="", notas=""):
		self.id = id
		self.nome = nome
		self.tlf = tlf
		self.email = email
		self.notas = notas

	def getLabel(self):
		return self.nome
	
	def getDic(self):
		return {
			"id": self.id, "nome": self.nome, "tlf": self.tlf,
			"email": self.email, "notas": self.notas
		}

class Peza():
	def __init__(self, id=0, codigo="", prov="", nome="", foto="", precio=dc.Decimal(0), cantidade=0, notas=""):
		self.id = id
		self.codigo = codigo
		self.prov = prov
		self.nome = nome
		self.foto = foto
		self.precio = precio
		self.cantidade = cantidade
		self.notas = notas
	
	def getLabel(self):
		return self.nome + " (" + self.prov + ")"
	
	def getDic(self):
		return {
			"id": self.id, "codigo": self.codigo, "prov": self.prov,
			"nome": self.nome, "foto": self.foto,
			"precio": str(self.precio), "cantidade": self.cantidade,
			"notas": self.notas
		}

class Pedido():
	def __init__(self, client_id=0, peza_id=0, pvp=dc.Decimal(0), estado=""):
		self.client_id = client_id
		self.peza_id = peza_id
		self.pvp = pvp
		self.estado = estado
	
	def getLabel(self):
		label = ""
		try:
			label = str(f.getCliente(self.client_id).getLabel() + " - " + f.getPeza(self.peza_id).getLabel())
		except:
			label = str(self.client_id) + ", " + str(self.peza_id)

		print("Label es " + label)
		return label
	
	def getDic(self):
		return {
			"idCliente": self.client_id, "idPeza": self.peza_id,
			"pvp": str(self.pvp), "estado": self.estado
		}