#!/usr/bin/env python3

import os
import tkinter as tk
import tkinter.messagebox as tkMsgBox
from PIL import ImageTk, Image
from . import funs

class ItemCliente(tk.Frame):
	def __init_(self, master=None, *args, **kwargs):
		print("soy un contructor de verdad")
		tk.Frame.__init__(self, master, bg="White", *args, **kwargs)
		self.master = master

	'''
	Constructor del Mercadona, porque el otro no funciona?
	'''	
	def build(self, cliente):
		self.cliente = cliente

		#region Descr
		self.texto = tk.StringVar()
		self.texto.set(self.cliente.getLabel())

		self.label = tk.Label(master=self, textvariable=self.texto, wraplength=450)
		self.label.pack(expand=True, side=tk.LEFT, fil=tk.X)
		#endregion

		#region Botones
		self.btnDel = tk.Button(self, text="Borrar", command=self.delete)
		self.btnDel.pack(side=tk.RIGHT)
		self.btnEdit = tk.Button(self, text="Editar", command=self.edit)
		self.btnEdit.pack(side=tk.RIGHT)
		self.btnVer = tk.Button(self, text="Ver", command=self.ver)
		self.btnVer.pack(side=tk.RIGHT)
		#endregion
		return self

	def ver(self):
		self.edit(readonly=True)

	def edit(self, readonly=False):
		frmEditCliente = FrmEditCliente(self.cliente, master=self.master, readonly=readonly)
		frmEditCliente.mainloop()

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
		tk.Label(master=self, text="Listado de clientes").pack(side=tk.TOP)

		self.widLista = tk.Frame(self)
		self.widLista.pack(expand=True, side=tk.TOP, fill=tk.X)

		self.refresh()
		#endregion

	def refresh(self):
		# Destruir todos los hijos de  widLista
		#map(lambda i: i.destroy(), self.widLista.winfo_children())
		for i in self.widLista.winfo_children():
			i.destroy()

		for c in funs.listClientes():
			item = ItemCliente(master=self.widLista, bg="white")
			item.build(c)
			item.pack(expand=True, side=tk.TOP, fill=tk.X)
		
	def add(self):
		pass

class FrmEditCliente(tk.Toplevel):
	def __init__(self, cliente, master=None, readonly=False):
		tk.Toplevel.__init__(self, master)
		self.master = master
		self.cliente = cliente
		self.title("Datos " + str(self.cliente.nome))

		#Variables de los datos
		self.nome = tk.StringVar()
		self.nome.set(self.cliente.nome)
		self.tlf = tk.StringVar()
		self.tlf.set(self.cliente.tlf)
		self.email = tk.StringVar()
		self.email.set(self.cliente.email)
	
		lblId = tk.Label(master=self, text="ID: ")
		lblId.grid(column=0, row=0)
		lblIdTxt = tk.Label(master=self, text=str(self.cliente.id))
		lblIdTxt.grid(column=1, row=0)

		lblNome = tk.Label(master=self, text="Nome: ")
		lblNome.grid(column=0, row=1)
		txtNome = tk.Entry(master=self, textvariable=self.nome)
		txtNome.grid(column=1, row=1)

		lblTlf = tk.Label(master=self, text="Tlf: ")
		lblTlf.grid(column=0, row=2)
		txtTlf = tk.Entry(master=self, textvariable=self.tlf)
		txtTlf.grid(column=1, row=2)

		lblEmail = tk.Label(master=self, text="eMail: ")
		lblEmail.grid(column=0, row=3)
		txtEmail = tk.Entry(master=self, textvariable=self.email)
		txtEmail.grid(column=1, row=3)

		lblNotas = tk.Label(master=self, text="Notas: ")
		lblNotas.grid(column=0, row=4)
		self._txtNotas = tk.Text(master=self)
		self._txtNotas.grid(column=1, row=4)
		#Insertar la nota real
		self._txtNotas.insert(tk.INSERT, self.cliente.notas)

		btnCancel = tk.Button(master=self, text="Cancelar", command=self.cancel)
		btnCancel.grid(column=0, row=5)

		btnSave = tk.Button(master=self, text="Gardar", command = self.save)
		btnSave.grid(column=1, row=5)

	def cancel(self):
		self.destroy()

	def save(self):
		self.cliente.nome = self.nome.get()
		self.cliente.tlf = self.tlf.get()
		self.cliente.email = self.email.get()
		self.cliente.notas = self._txtNotas.get('1.0', tk.END)

		try:
			result = funs.updateCliente(self.cliente)

			if result == 201:
				tkMsgBox.showinfo("OK", message="Datos actualizados")
				self.destroy()
			else:
				print("Error " + str(result) + " actualizando datos ")
				tkMsgBox.showerror("ERROR " + str(result), message="Error al guardar los datos")
		except:
			print("Error actualizando datos (2)")
			tkMsgBox.showerror("ERROR", message="Error al guardar los datos")