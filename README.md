# Práctica 5 PAT Pablo de la Cal Priede
En esta práctica se muestra la unión de las tres partes de la asignatura:

    - Backend
    - Frontend
    - JavaScript para unirlas

Como en las prácticas anteriores no había estudiado tanto para el examen, y hacer todo desde 0 podía ser un buen ejercicio, he optado por rehacer absolutamente todo. 

Esto resulta en que el backend es nuevo, el frontend también. Son independientes de las prácticas anteriores. 

Entonces, como el backend es nuevo creo que estaría bien dejar por aqui que endpoints y funcionalidades he añadido a esta practica: 

## Nuevas funcionalidades Backend
### En carrito
Tengo: 

- Crear un carrito
- Obtener todos los carritos
- Obtener carrito por id
- Obtener carrito por idUsuario
- Actualziar carrito
- Borrar carrito

### En lineaCarrito
Tengo: 

- Obtener las lineas de carrito de un carrito
- Añadir linea a un carrito
- Actualizar lineaCarrito
- Borrar lineaCarrito

## Nuevas funcionalidades Frontend
Por otro lado, en la práctica de frontend anterior, donde no había estudidado para el examen y no sabía tanto de frontend, hice 
la página web del Atlético de Madrid. He optado por cambiar esto también, para poder adaptarlo al enunciado de la práctica y sobretodo a la 
posterior union con JavaScrip. 

Entonces, he dejado solamente dos archivos .html: 

### index.html (carritos)
Aquí he recogido lo principal. Se puede: 

- Crear un carrrito
- Buscar un carrito por su id
- Buscar un carrito por su id de usuario
- Listar todos los carritos

### carrito.html
Aquí he recogido un poco más la parte de linea de pedido. Se puede: 

- Ver los datos del carrito consultado
- Actualizar un carrito
- Mostrar las lineas de un carrito
- Añadir una linea de carrito
- Actualizar una linea de carrito
- Borrar una linea de carrito
- Borrar un carrito

## JavaScript
Después de rehacer todo esto, se ha unido todo con lógica de JavaScript implementando dos archivos .js

- index.js
- carrito.js
