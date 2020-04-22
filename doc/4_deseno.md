# FASE DE DESEÑO

## Modelo conceptual do dominio da aplicación e/ou Diagrama de clases [usando UML, ConML, ou linguaxe semellante].

## Casos de uso [descritos en fichas e/ou mediante esquemas; deben incluír o(s) tipo(s) de usuario implicados en cada caso de uso].

![Operacións do xerente](img/CasoDeUso1.png "Caso de uso 1")

![Operacións do currante](img/CasoDeUso2.png "Caso de uso 2")

## Mensaxes

### Peticións

O corpo das peticións (o JSON) envíase encriptado cunha clave privada que identifica o cliente.
O servidor utiliza a clave pública para descifralo e identificar o cliente autorizado.

O copro das peticións ten sempre esta estructura:

> Os `[]` indican datos opcionais

```
{
	"usuairo": {

	},
	"operacion": {
		"apartado": <APARTADO>,
		"tipo": <get|crete|update|delete>,
		["selec": {}],
		["datos": {}]
	}
}
```


Exemplo de petición para consultar crear, editar e eliminar un cliente
```
GET / HTTP/1.1
HOST: servidor
Content-Type: applicaiton/json
Accept: application/json

{
	"usuario": {
		"rol": "xerente"
	},
	"operacion": {
		"apartado": "x_clientes",
		"tipo": "get",
		"selec": {}
	}
}
```

```
POST / HTTP/1.1
HOST: servidor
Content-Type: applicaiton/json
Accept: application/json

{
	"usuario": {
		"rol": "xerente"
	},
	"operacion": {
		"apartado": "x_clientes",
		"tipo": "create",
		"datos": {
			"cliente": {
				"nome": "Pepito",
				"tlf": "666666666",
				"email": "pepito@pepitoweb.net",
				"notas": ""
			}
		}
	}
}
```

```
PUT / HTTP/1.1
HOST: servidor
Content-Type: applicaiton/json
Accept: application/json

{
	"usuario": {
		"rol": "xerente"
	},
	"operacion": {
		"apartado": "x_clientes",
		"tipo": "update",
		"selec": {
			"nome": "Pepito"
		},
		"datos": {
			"cliente": {
				"tlf": "666777777",
				"notas": "Nova dirección: Rosalia 7, 3º A"
			}
		}
	}
}
```

```
DELETE / HTTP/1.1
HOST: servidor
Content-Type: applicaiton/json
Accept: application/json

{
	"usuario": {
		"rol": "xerente"
	},
	"operacion": {
		"apartado": "x_clientes",
		"tipo": "delete",
		"selec": {
			"nome": "Pepito"
		}
	}
}
```

Para operar con pezas ou reparacións sería igual pero cambiando *x_clientes* por *x_pezas* ou *x_repar* respectivamente.

Exemplo de peticións para crear ou eliminar pedidos
```
POST / HTTP/1.1
HOST: servidor
Content-Type: applicaiton/json
Accept: application/json

{
	"usuario": {
		"rol": "currante"
	},
	"operacion": {
		"apartado": "x_pedido",
		"tipo": "create",
		"datos": {
			"cliente": {
				"id": 7
			},
			"peza": {
				"id": 18
			}
		}
	}
}
```

```
DELETE / HTTP/1.1
HOST: servidor
Content-Type: applicaiton/json
Accept: application/json

{
	"usuario": {
		"rol": "currante"
	},
	"operacion": {
		"apartado": "x_pedido",
		"tipo": "delete",
		"datos": {
			"cliente": {
				"id": 7
			},
			"peza": {
				"id": 18
			}
		}
	}
}
```


### Respostas

## Deseño de interface de usuarios [mockups ou diagramas...].

## Diagrama de Base de Datos.

![Diagrama entidade-relación da Base de datos](img/ER.png "ER da base de datos")

## Diagrama de compoñentes software que constitúen o produto e de despregue.

![Diagrama de despregue](img/Despregue1.png)