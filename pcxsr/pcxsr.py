#!/usr/bin/env python3

import os
import tkinter as tk
from PIL import ImageTk, Image
from src import componentes

basePath = os.path.dirname(__file__)
resPath = os.path.join(basePath, "res")

root = tk.Tk()
root.title("PC-XSR")
root.geometry("600x600")

def abrirXclientes():
	xc = componentes.Xclientes(root, resPath)
	xc.mainloop()

img = ImageTk.PhotoImage(
		Image.open(os.path.join(resPath, "customer.png")).resize((80, 80), Image.ANTIALIAS)
	)

tk.Button(root, text="Xestión clientes", image=img, command=abrirXclientes).pack()

tk.mainloop()