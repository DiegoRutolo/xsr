#!/usr/bin/env python3

import os
import traceback
import decimal as dc
import tkinter as tk
import tkinter.messagebox as tkMsgBox
from tkinter import ttk
from PIL import ImageTk, Image
from . import funs as f
from . import objetos as o

class ItemLista(tk.Frame):
	def __init_(self, master=None, *args, **kwargs):
		print("soy un constructor de verdad")
		tk.Frame.__init__(self, master, bg="White", *args, **kwargs)
		self.master = master

	'''
	Constructor del Mercadona, porque el otro no funciona?
	'''	
	def build(self, obxeto):
		self.obxeto = obxeto

		#region Descr
		self.texto = tk.StringVar()
		self.texto.set(self.obxeto.getLabel())

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
		frmEditPedido = FrmEditPedido(self.obxeto, master=self.master, readonly=readonly)
		frmEditPedido.mainloop()

	def delete(self):
		#Pedir confirmacion
		respuesta = tkMsgBox.askyesno(
			"Eliminar",
			message="Seguro que queres eliminar o obxeto " + self.obxeto.getLabel() + "?",
			default="no")

		if respuesta:
			#Enviar peticion
			result = 0
			if type(self.obxeto == o.Cliente):
				result = f.deleteCliente(self.obxeto)
			elif type(self.obxeto == o.Peza):
				result = f.deletePeza(self.obxeto)
			elif type(self.obxeto == o.Pedido):
				result = f.deletePedido(self.obxeto)

			#Mostrar resultado
			if result == 201:
				tkMsgBox.showinfo("OK", message="Obxeto eliminado")
				self.destroy()
			else:
				print("Error " + str(result) + " actualizando datos ")
				tkMsgBox.showerror("ERROR " + str(result), message="Erro gardando os datos")


#region Cliente
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
		#Pedir confirmacion
		respuesta = tkMsgBox.askyesno(
			"Eliminar",
			message="Seguro que queres eliminar a " + self.cliente.getLabel() + "?",
			default="no")

		if respuesta:
			#Enviar peticion
			result = f.deleteCliete(self.cliente)

			#Mostrar resultado
			if result == 201:
				tkMsgBox.showinfo("OK", message="Cliente eliminado")
				self.destroy()
			else:
				print("Error " + str(result) + " actualizando datos ")
				tkMsgBox.showerror("ERROR " + str(result), message="Error al guardar los datos")

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
		tk.Button(barraIconos, text="Nuevo cliente", command=self.add).pack(side=tk.LEFT)
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

		for c in f.listClientes():
			item = ItemCliente(master=self.widLista, bg="white")
			item.build(c)
			item.pack(expand=True, side=tk.TOP, fill=tk.X)
		
	def add(self):
		frmNewCliente = FrmEditCliente(o.Cliente(), master=self, newclient=True)
		frmNewCliente.mainloop()

class FrmEditCliente(tk.Toplevel):
	def __init__(self, cliente, master=None, readonly=False, newclient=False):
		tk.Toplevel.__init__(self, master)
		self.master = master
		self.cliente = cliente
		self.newclient = newclient
		self.title("Datos " + str(self.cliente.nome))

		self.campos = {}

		#Variables de los datos
		self.nome = tk.StringVar()
		self.nome.set(self.cliente.nome)
		self.tlf = tk.StringVar()
		self.tlf.set(self.cliente.tlf)
		self.email = tk.StringVar()
		self.email.set(self.cliente.email)
	
		if not newclient:
			lblId = tk.Label(master=self, text="ID: ")
			lblId.grid(column=0, row=0)
			lblIdTxt = tk.Label(master=self, text=str(self.cliente.id))
			lblIdTxt.grid(column=1, row=0)
			self.campos["id"] = lblIdTxt

		lblNome = tk.Label(master=self, text="Nome: ")
		lblNome.grid(column=0, row=1)
		txtNome = tk.Entry(master=self, textvariable=self.nome)
		txtNome.grid(column=1, row=1)
		self.campos["nome"] = txtNome

		lblTlf = tk.Label(master=self, text="Tlf: ")
		lblTlf.grid(column=0, row=2)
		txtTlf = tk.Entry(master=self, textvariable=self.tlf)
		txtTlf.grid(column=1, row=2)
		self.campos["tlf"] = txtTlf

		lblEmail = tk.Label(master=self, text="eMail: ")
		lblEmail.grid(column=0, row=3)
		txtEmail = tk.Entry(master=self, textvariable=self.email)
		txtEmail.grid(column=1, row=3)
		self.campos["email"] = txtEmail

		lblNotas = tk.Label(master=self, text="Notas: ")
		lblNotas.grid(column=0, row=4)
		txtNotas = tk.Text(master=self)
		txtNotas.grid(column=1, row=4)
		#Insertar la nota real
		txtNotas.insert(tk.INSERT, self.cliente.notas)
		self.campos["notas"] = txtNotas

		if readonly:
			btnClose = tk.Button(master=self, text="Pechar", command=self.cancel)
			btnClose.grid(column=1, row=5)

			for k, v in self.campos.items():
				v.config(state=tk.DISABLED, fg="black")

		else:
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
		self.cliente.notas = self.campos["notas"].get('1.0', tk.END)

		try:
			if self.newclient:
				result = f.addCliente(self.cliente)
			else:
				result = f.updateCliente(self.cliente)

			if result == 201:
				tkMsgBox.showinfo("OK", message="Datos actualizados")
				self.destroy()
			else:
				print("Error " + str(result) + " actualizando datos ")
				tkMsgBox.showerror("ERROR " + str(result), message="Error al guardar los datos")
		except:
			print("Error actualizando datos (2)")
			tkMsgBox.showerror("ERROR", message="Error al guardar los datos")
		self.master.refresh()
#endregion

#region Pezas
class ItemPeza(tk.Frame):
	def __init_(self, master=None, *args, **kwargs):
		print("soy un constructor de verdad")
		tk.Frame.__init__(self, master, bg="White", *args, **kwargs)
		self.master = master

	'''
	Constructor del Mercadona, porque el otro no funciona?
	'''	
	def build(self, peza):
		self.peza = peza

		#region Descr
		self.texto = tk.StringVar()
		self.texto.set(self.peza.getLabel())

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
		frmEditPeza = FrmEditPeza(self.peza, master=self.master, readonly=readonly)
		frmEditPeza.mainloop()

	def delete(self):
		#Pedir confirmacion
		respuesta = tkMsgBox.askyesno(
			"Eliminar",
			message="Seguro que queres eliminar a peza " + self.peza.getLabel() + "?",
			default="no")

		if respuesta:
			#Enviar peticion
			result = f.deletePeza(self.peza)

			#Mostrar resultado
			if result == 201:
				tkMsgBox.showinfo("OK", message="Peza eliminada")
				self.destroy()
			else:
				print("Error " + str(result) + " actualizando datos ")
				tkMsgBox.showerror("ERROR " + str(result), message="Erro gardando os datos")

class Xpezas(tk.Toplevel):
	def __init__(self, master=None, resPath=".."):
		tk.Toplevel.__init__(self, master)
		self.master = master
		self.geometry("600x600")
		self.title("Xestión pezas")
	
		#region Barra acciones
		barraIconos = tk.Frame(self)
		barraIconos.pack(side=tk.TOP, fill=tk.X)

		tk.Button(barraIconos, text="Actualizar", command=self.refresh).pack(side=tk.LEFT)
		tk.Button(barraIconos, text="Novo", command=self.add).pack(side=tk.LEFT)
		#endregion

		#region Lista
		tk.Label(master=self, text="Listado de pezas").pack(side=tk.TOP)

		self.widLista = tk.Frame(self)
		self.widLista.pack(expand=True, side=tk.TOP, fill=tk.X)

		self.refresh()
		#endregion

	def refresh(self):
		# Destruir todos los hijos de  widLista
		#map(lambda i: i.destroy(), self.widLista.winfo_children())
		for i in self.widLista.winfo_children():
			i.destroy()

		for c in f.listPezas():
			item = ItemPeza(master=self.widLista, bg="white")
			item.build(c)
			item.pack(expand=True, side=tk.TOP, fill=tk.X)
		
	def add(self):
		frmNewPeza = FrmEditPeza(o.Peza(), master=self, newpeza=True)
		frmNewPeza.mainloop()

class FrmEditPeza(tk.Toplevel):
	def __init__(self, peza, master=None, readonly=False, newpeza=False):
		tk.Toplevel.__init__(self, master)
		self.master = master
		self.peza = peza
		self.newpeza = newpeza
		self.title("Datos " + str(self.peza.nome))

		self.campos = {}

		if not newpeza:
			lblId = tk.Label(master=self, text="ID: ")
			lblId.grid(column=0, row=0)
			lblIdTxt = tk.Label(master=self, text=str(self.peza.id))
			lblIdTxt.grid(column=1, row=0)
			self.campos["id"] = lblIdTxt

		tk.Label(master=self, text="Codigo: ").grid(column=0, row=1)
		self.campos["codigo"] = tk.Entry(master=self)
		self.campos["codigo"].insert(tk.INSERT, self.peza.codigo)
		self.campos["codigo"].grid(column=1, row=1)

		tk.Label(master=self, text="Prov: ").grid(column=0, row=2)
		self.campos["prov"] = tk.Entry(master=self)
		self.campos["prov"].insert(tk.INSERT, self.peza.prov)
		self.campos["prov"].grid(column=1, row=2)

		tk.Label(master=self, text="Nome: ").grid(column=0, row=3)
		self.campos["nome"] = tk.Entry(master=self)
		self.campos["nome"].insert(tk.INSERT, self.peza.nome)
		self.campos["nome"].grid(column=1, row=3)

		#Foto

		tk.Label(master=self, text="Precio: ").grid(column=0, row=5)
		self.campos["precio"] = tk.Entry(master=self)
		self.campos["precio"].insert(tk.INSERT, str(self.peza.precio))
		self.campos["precio"].grid(column=1, row=5)

		tk.Label(master=self, text="Cantidade: ").grid(column=0, row=6)
		self.campos["cantidade"] = tk.Entry(master=self)
		self.campos["cantidade"].insert(tk.INSERT, str(self.peza.cantidade))
		self.campos["cantidade"].grid(column=1, row=6)

		tk.Label(master=self, text="Notas: ").grid(column=0, row=7)
		self.campos["notas"] = tk.Text(master=self)
		self.campos["notas"].insert(tk.INSERT, self.peza.notas)
		self.campos["notas"].grid(column=1, row=7)

		if readonly:
			btnClose = tk.Button(master=self, text="Pechar", command=self.cancel)
			btnClose.grid(column=1, row=8)

			for k, v in self.campos.items():
				v.config(state=tk.DISABLED, fg="white")

		else:
			btnCancel = tk.Button(master=self, text="Cancelar", command=self.cancel)
			btnCancel.grid(column=0, row=8)

			btnSave = tk.Button(master=self, text="Gardar", command = self.save)
			btnSave.grid(column=1, row=8)

	def cancel(self):
		self.destroy()

	def save(self):
		#Cambiar coma por punto
		tmp = self.campos["precio"].get()
		self.campos["precio"].delete(0, tk.END)
		self.campos["precio"].insert(tk.INSERT, tmp.replace(",", "."))

		#Validar datos
		try:
			i = dc.Decimal(self.campos["precio"].get())
			i += 1	
		except:	
			tkMsgBox.showerror("ERROR", message="O precio debe ser un número")
			return

		try:
			i = int(self.campos["cantidade"].get())
		except:
			tkMsgBox.showerror("ERROR", message="A cantidade debe ser un número enteiro")
			return


		#Asignar al Obj
		self.peza.codigo = self.campos["codigo"].get()
		self.peza.prov = self.campos["prov"].get()
		self.peza.nome = self.campos["nome"].get()
		#Foto
		self.peza.precio = dc.Decimal(self.campos["precio"].get())
		self.peza.cantidade = int(self.campos["cantidade"].get())
		self.peza.notas = self.campos["notas"].get('1.0', tk.END)

		try:
			if self.newpeza:
				result = f.addPeza(self.peza)
			else:
				result = f.updatePeza(self.peza)

			if result == 201:
				tkMsgBox.showinfo("OK", message="Datos actualizados")
				self.destroy()
			else:
				print("Error " + str(result) + " actualizando datos ")
				tkMsgBox.showerror("ERROR " + str(result), message="Erro gardando datos")
		except:
			print("Error actualizando datos (2)")
			tkMsgBox.showerror("ERROR", message="Erro gardando datos")
		self.master.refresh()
#endregion

#region Pedidos
class ItemPedido(tk.Frame):
	def __init_(self, master=None, *args, **kwargs):
		print("soy un constructor de verdad")
		tk.Frame.__init__(self, master, bg="White", *args, **kwargs)
		self.master = master

	'''
	Constructor del Mercadona, porque el otro no funciona?
	'''	
	def build(self, pedido):
		self.pedido = pedido
		
		#region Descr
		self.texto = tk.StringVar()
		self.texto.set(self.pedido.getLabel())

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
		frmEditPedido = FrmEditPedido(self.pedido, master=self.master, readonly=readonly)
		frmEditPedido.mainloop()

	def delete(self):
		#Pedir confirmacion
		respuesta = tkMsgBox.askyesno(
			"Eliminar",
			message="Seguro que queres eliminar o pedido " + self.pedido.getLabel() + "?",
			default="no")

		if respuesta:
			#Enviar peticion
			result = f.deletePedido(self.pedido)

			#Mostrar resultado
			if result == 201:
				tkMsgBox.showinfo("OK", message="Pedido eliminado")
				self.destroy()
			else:
				print("Error " + str(result) + " actualizando datos ")
				tkMsgBox.showerror("ERROR " + str(result), message="Erro gardando os datos")

class Xpedidos(tk.Toplevel):
	def __init__(self, master=None, resPath=".."):
		tk.Toplevel.__init__(self, master)
		self.master = master
		self.geometry("600x600")
		self.title("Xestión pedidos")
	
		#region Barra acciones
		barraIconos = tk.Frame(self)
		barraIconos.pack(side=tk.TOP, fill=tk.X)

		tk.Button(barraIconos, text="Actualizar", command=self.refresh).pack(side=tk.LEFT)
		tk.Button(barraIconos, text="Novo", command=self.add).pack(side=tk.LEFT)
		#endregion

		#region Lista
		tk.Label(master=self, text="Listado de pedidos").pack(side=tk.TOP)

		self.widLista = tk.Frame(self)
		self.widLista.pack(expand=True, side=tk.TOP, fill=tk.X)

		self.refresh()
		#endregion

	def refresh(self):
		# Destruir todos los hijos de  widLista
		#map(lambda i: i.destroy(), self.widLista.winfo_children())
		for i in self.widLista.winfo_children():
			i.destroy()

		for ped in f.listPedidos():
			item = ItemPedido(master=self.widLista, bg="white")
			item.build(ped)
			item.pack(expand=True, side=tk.TOP, fill=tk.X)
		
	def add(self):
		frmNewPedido = FrmEditPedido(o.Pedido(), master=self, newpedido=True)
		frmNewPedido.mainloop()

class FrmEditPedido(tk.Toplevel):
	def __init__(self, pedido, master=None, readonly=False, newpedido=False):
		tk.Toplevel.__init__(self, master)
		self.master = master
		self.pedido = pedido
		self.newpedido = newpedido
		self.title("Datos " + self.pedido.getLabel())

		self.listClientes = f.listClientes()[:]
		self.listPezas = f.listPezas()[:]

		posCliente = 0
		for i in range(len(self.listClientes)):
			if self.listClientes[i].id == self.pedido.client_id:
				posCliente = i
		posPeza = 0
		for i in range(len(self.listPezas)):
			if self.listPezas[i].id == self.pedido.peza_id:
				posPeza = i

		self.campos = {}

		tk.Label(master=self, text="Cliente: ").grid(column=0, row=0)
		self.campos["cliente"] = ttk.Combobox(self, values=[c.getLabel() for c in self.listClientes], state='readonly')
		self.campos["cliente"].current(posCliente)
		self.campos["cliente"].grid(column=1, row=0)

		tk.Label(master=self, text="Peza: ").grid(column=0, row=1)
		self.campos["peza"] = ttk.Combobox(self, values=[c.getLabel() for c in self.listPezas], state='readonly')
		self.campos["peza"].current(posPeza)
		self.campos["peza"].grid(column=1, row=1)

		tk.Label(master=self, text="PVP: ").grid(column=0, row=2)
		self.campos["pvp"] = tk.Entry(master=self)
		self.campos["pvp"].insert(tk.INSERT, str(self.pedido.pvp))
		self.campos["pvp"].grid(column=1, row=2)

		tk.Label(master=self, text="Estado: ").grid(column=0, row=3)
		self.campos["estado"] = tk.Entry(master=self)
		self.campos["estado"].insert(tk.INSERT, self.pedido.estado)
		self.campos["estado"].grid(column=1, row=3)

		if readonly:
			btnClose = tk.Button(master=self, text="Pechar", command=self.cancel)
			btnClose.grid(column=1, row=8)

			for k, v in self.campos.items():
				v.config(state=tk.DISABLED)
			
			# self.campos["cliente"].config(state='disabled')
			# self.campos["peza"].config(state='disabled')

		else:
			btnCancel = tk.Button(master=self, text="Cancelar", command=self.cancel)
			btnCancel.grid(column=0, row=8)

			btnSave = tk.Button(master=self, text="Gardar", command = self.save)
			btnSave.grid(column=1, row=8)

	def cancel(self):
		self.destroy()

	def save(self):
		#Cambiar coma por punto
		tmp = self.campos["pvp"].get()
		self.campos["pvp"].delete(0, tk.END)
		self.campos["pvp"].insert(tk.INSERT, tmp.replace(",", "."))

		#Validar datos
		try:
			i = dc.Decimal(self.campos["pvp"].get())
			i += 1
		except:	
			tkMsgBox.showerror("ERROR", message="O PVP debe ser un número")
			return

		#Asignar al Obj
		self.pedido.client_id = int(self.listClientes[self.campos["cliente"].current()].id)
		self.pedido.peza_id = int(self.listPezas[self.campos["peza"].current()].id)
		self.pedido.pvp = dc.Decimal(self.campos["pvp"].get())
		self.pedido.estado = self.campos["estado"].get()

		try:
			if self.newpedido:
				result = f.addPedido(self.pedido)
			else:
				result = f.updatePedido(self.pedido)

			if result == 201:
				tkMsgBox.showinfo("OK", message="Datos actualizados")
				self.destroy()
			else:
				print("Error " + str(result) + " actualizando datos ")
				tkMsgBox.showerror("ERROR " + str(result), message="Erro gardando datos")
		except:
			traceback.print_exc()
			print("Error actualizando datos (2)")
			tkMsgBox.showerror("ERROR", message="Erro gardando datos")
		self.master.refresh()
#endregion