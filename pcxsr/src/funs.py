#!/usr/bin/env python3

import os
import tkinter as tk
from . import objetos

def listClientes():
    listaClientes = []

    c1 = objetos.Cliente(4, "Carrero", "888888888", "blanco@espanha.up", "Primer astronauta español")
    c2 = objetos.Cliente(675, "Paquito", "55555555", "chocolate@espanha.up", "Mejor chocolatero desde 1936")

    listaClientes.append(c1)
    listaClientes.append(c2)

    return listaClientes
    