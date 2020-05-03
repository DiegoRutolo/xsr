# FASE DE PLANIFICACIÓN DO PROXECTO

# Guía de planificación do proxecto

## Metodoloxía prevista


## Fases planificadas

Descríbense as fases en que se divide o proxecto.
Pódense indicar os recursos materiais e humanos asociados a cada tarefa ou, se son os mesmos, de maneira máis xeral.

> **Recursos**: Os recursos son exactamente os mesmos para todas as fases: 1 programador (eu), 1 PC (Ubuntu) e unha conexión a internet.

### Fase 1: Estudo de necesidades e modelo de negocio

#### Tarefa 1: xxxxxxxx

Descrición: 

Recursos hardware/software: 

Recursos humanos: Persoas que se encargarán de realizar esta tarefa

Duración: 

#### Tarefa 2
...

-------------------------------

### Fase 2: Proba de concepto

**Duración da fase**: 5 días

**Descrición**: Nesta fase realízase unha implementación sen funcionalidade real, pero que demostra que todas as tarefas son posibles (configuracion de contenedores, probas de conectividade, etc...).

**Obxetivo**: O servidor debería ser capaz de recibir peticións HTTP, ler o arquivo de configuración e conectarse coa base de datos.

#### Tarefa 1: Docker e build scripts

**Descrición**: Escribir os scripts *(Docker, Bash, SQL...)* que crean e configuran as imaxes.

#### Tarefa 2: Clases principais

**Descricion**: Escribir a funcadión das principais clases que forman o código. 
Algunhas destas clases son:
 * Main
 * Servidor
 * db.Operacions
 * data.Config
 * data.Log

-------------------------------------

### Fase 3: Xestión de Clientes

**Duración**: 4 días

**Descrición**: Nesta fase impleméntase a xestión de *Clientes*.

**Obxetivo**: Poder crear, consultar e actualizar clientes. Interactuar co cliente mediante JSON.

#### Tarefa 1: Clases

**Descrición**: Crear a Clase *Cliente* e os métodos de *Operacións*

#### Tarefa 2: Respostas

**Descrición**: Métodos no servidor para interpretar as peticións entrantes, compoñer a resposta adecuada e enviala.

----------------

### Fase 4: Pezas

**Duración**: 2 días

**Descrición**: Implementar xestión de *Pezas* de forma parecida á fase 3.

**Obxetivo**: Poder crear, consultar e actualizar pezas. Interactuar co cliente mediante JSON.

---------------------------------------

## Diagrama de Gantt

![Diagrama de Gantt](img/xsr_Gantt.png)

# Orzamento
Realizar o orzamento do proxecto é relativamente fácil se se elaboraron ben as etapas anteriores; é dicir, definir as actividades necesarias e os recursos propios de cada actividade. 
Neste caso dispoñerase dun custo por cada actividade e a suma do custo de todas as actividades será o custo total do proxecto. 
Para establecer os custos do proxecto téñense en conta diferentes conceptos: 
Materiais que se utilizan: material funxible, materias primas, materiais didácticos, roupa de traballo… 
Custo horario das persoas que participan directamente na actividade. 
Aluguer/compra de ferramentas, maquinaria, equipos informáticos e/ou audiovisuais… 
Aluguer/compra de locais 
Contratos de subministracións: auga, luz, electricidade, gas… Subcontrataciones 
Gastos de publicidade… 
Seguros ... 

O maior custo no proxecto case sempre corresponde ás persoas, polo que é importante controlar o número de horas que se invisten en cada actividade para que non se nos desequilibre o orzamento. Tamén hai que coidar as subcontratacións; convén que traballen cun orzamento establecido.
 A continuación preséntanse dúas opción de táboa para facilitar a creación do orzamento do proxecto:

## Orzamento por actividade

| ACTIVIDADE | DURACIÓN | CUSTO (EUROS) | | CUSTO TOTAL ACTIVIDADE |
|--|--|--|--|--|
|            |          | PERSOAS|RECURSOS MATERIAIS|
|||||
|||||
|||||
|||||

| TOTAL | PROXECTO | 
| -- | -- |

## Orzamento por partidas de inversión / gasto:

| CONCEPTO | IMPORTE|
|--|--|
|**A) INVERSIONS**
|Gastos de establecemento e gastos de constitución
|Total inmobilizacións inmateriais
|Terreos
|Construcións
|Instalacións técnicas
|Maquinaria
|Ferramentas
|Mobiliario e instalacións
|Equipos informáticos
|Elementos de transporte
|Outro inmobilizado material
|Total inmobilizacións materiais
|Outros gastos a distribuír en varios exercicios
|**TOTAL INVERSIÓNS:**
|**B) GASTOS**
|Compras de materiais
|Arrendamentos
|Publicidade, propaganda e relacións públicas
|Persoal
|Reparacións e conservación
|Servizos de profesionais independentes
|Outros gastos xerais
|Gastos financeiros
|Amortizacións
|Gastos de xestión e administración
|**TOTAL GASTOS:**

|TOTAL ORZAMENTO:
|--|

## WEBGRAFÍA
Guía para a elaboración de proyectos. Gobierno Vasco.
https://www.pluralismoyconvivencia.es/upload/19/71/guia_elaboracion_proyectos_c.pdf  (páxina 49 e seguintes)



