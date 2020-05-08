# IDEA DO PROXECTO

## O proxecto

O proxecto consiste nun sistema de xestión para un taller. Serve para gardar un rexistro dos clientes, as pezas que piden e os servizos prestados.

A aplicación utiliza a arquitectura Servidor-Cliente. O servidor é unha aplicación en Java dentro dun contenedor (probablemente Docker) e unha API para comunicarse cos clientes.

Os clientes poden ser APPs Android, webapps multiplataforma feitas con Electron ou simples scripts para CLI. O importante é que só interactúan con servidor mediate a API, de forma que é moi facil programarlas de forma independiente.

O proxecto céntrase na documentación e desenvolvemento do servidor e definir os protocolos de comunicación.


## Destinatarios

Está orientado específicamente a **pequenos talleres** de reparación de informática e telefonía, pero a idea é facelo o suficientemente flexible como para que poida ser utilizado por outros sectores, como automovilístico, electrodomésticos, etc...


## Necesidades e alternativas

Por experiencia propia no sector, a maioría de tendas e talleres son bastante caóticos e é habitual perder de vista algunha peza ou atopalas polo almacen e non saber para qué se pediron. 

A maioría do software actual é demasiado complexo ou caro para as pequenas empresas.

O que diferencia éste proxecto é a especialización. Garda o estrictamente necesario para coñecer os clientes e que necesitan.


## Comercialización

Dado que o proxecto leva asociada unha licencia libre, a mellor opción de comercialización sería ofrecer un servizo de implantación e soporte técnico.