#!/usr/bin/env python3

import os
import tkinter as tk
from PIL import ImageTk, Image
from . import funs

class ItemCliente(tk.Frame):
	def __init_(self, master=None, *args, **kwargs):
		print("soy un contructor de verdad")
		tk.Frame.__init__(self, master, *args, **kwargs)
		self.master = master

	'''
	Constructor del Mercadona, porque el otro no funciona?
	'''	
	def build(self, cliente):
		self.cliente = cliente

		#region Descr
		self.texto = tk.StringVar()
		self.texto.set(self.cliente.getLabel())

		self.label = tk.Label(master=self, textvariable=self.texto, wraplength=25)
		self.label.pack(side=tk.LEFT, fil=tk.X)
		#endregion

		#region Botones
		self.btnEdit = tk.Button(self, text="Editar", command=self.edit)
		self.btnEdit.pack(side=tk.RIGHT)
		self.btnDel = tk.Button(self, text="Borrar", command=self.delete)
		self.btnDel.pack(side=tk.RIGHT)
		#endregion
		return self

	def edit(self):
		pass

	def delete(self):
		pass

class Xclientes(tk.Toplevel):
	def __init__(self, master=None, resPath=".."):
		tk.Toplevel.__init__(self, master)
		self.master = master
		self.geometry("600x600")
		self.title("Xestión clientes")
	
		#region Barra acciones
		barraIconos = tk.Frame(self)
		barraIconos.pack(side=tk.TOP, fill=tk.X)

		imgRefresh = ImageTk.PhotoImage(
			Image.open(os.path.join(resPath, "refresh-icon.png")).resize((60, 60), Image.ANTIALIAS)
		)
		tk.Button(barraIconos, text="Actualizar", command=self.refresh).pack(side=tk.LEFT)

		imgAdd = ImageTk.PhotoImage(
			Image.open(os.path.join(resPath, "refresh-icon.png")).resize((60, 60), Image.ANTIALIAS)
		)
		tk.Button(barraIconos, text="Nuevo", command=self.add).pack(side=tk.LEFT)
		#endregion

		#region Lista
		self.widLista = tk.Frame(self)
		self.widLista.pack(side=tk.TOP)

		tk.Label(master=self, text="Listado de clientes").pack(side=tk.TOP)

		ItemCliente(master=self.widLista).pack(side=tk.TOP)

		self.refresh()
		#endregion

	def refresh(self):
		# Destruir todos los hijos de  widLista
		#map(lambda i: i.destroy(), self.widLista.winfo_children())
		for i in self.widLista.winfo_children():
			i.destroy()

		for c in funs.listClientes():
			item = ItemCliente(master=self.widLista)
			item.build(c)
			item.pack(expand=True, side=tk.TOP, fill=tk.X)
		
	def add(self):
		pass