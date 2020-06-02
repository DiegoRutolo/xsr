#!/usr/bin/env python3

import os
import tkinter as tk
import decimal as dc

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
