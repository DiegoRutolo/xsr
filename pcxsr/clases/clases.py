#!/usr/bin/env python3

import os
import tkinter as tk
from PIL import ImageTk, Image

class Xclientes(tk.Toplevel):
	def __init__(self, master=None, resPath="."):
		tk.Toplevel.__init__(self, master)
		self.master = master
		self.geometry("600x600")
	
		#region Barra acciones
		barraIconos = tk.Frame(self)
		barraIconos.pack(side=tk.TOP, fill=tk.X)

		img = ImageTk.PhotoImage(
			Image.open(os.path.join(resPath, "refresh-icon.png")).resize((60, 60), Image.ANTIALIAS)
		)
		tk.Button(barraIconos, text="Actualizar", command=self.refresh).pack(side=tk.LEFT)
		tk.Button(barraIconos, text="Nuevo", command=self.add).pack(side=tk.LEFT)
		#endregion

		#region Lista
		lista = []
		#endregion

	def refresh(self):
		pass

	def add(self):
		pass