#!/usr/bin/env python3

import os
import tkinter as tk

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
