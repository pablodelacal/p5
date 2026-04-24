const API = "/api/carritos";

//Tengo que poner todos mis metodos para poder encontrar y que funcione mi js
document.getElementById("botonCrearCarrito").addEventListener("click", crearCarrito);
document.getElementById("botonBuscarCarrito").addEventListener("click", buscarCarritoPorId);
document.getElementById("botonBuscarCarritoUsuario").addEventListener("click", buscarCarritoPorUsuario);
document.getElementById("botonCargarCarritos").addEventListener("click", cargarCarritos);


//Creo carrito
async function crearCarrito(event) {
    event.preventDefault();

    //Lo que me pasan es un carrito
    const carrito = {
        idUsuario : Number(document.getElementById("crearIdUsuario").value),
        emailUsuario : document.getElementById("crearEmailUsuario").value
    };

    const respuesta = await fetch(API, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(carrito)
    });

    if(respuesta.ok){
        alert("Carrito creado correctamente");
        cargarCarritos();
    }else{
        alert("Error al crear el carrito");
    }
}

//Obtener todos los carritos --> es un get
async function cargarCarritos(event){
    if(event !== undefined){
        event.preventDefault();
    }

    const respuesta = await fetch(API);

    if(!respuesta.ok){
        alert("Error al cargar los datos");
        return;
    }

    const carritos = await respuesta.json();
    pintarCarritos(carritos);
}

//Obtener carrito por idCarrito
async function buscarCarritoPorId(event){
    event.preventDefault();

    const idCarrito = document.getElementById("buscarIdCarrito").value;
    const respuesta = await fetch(API + "/" + idCarrito);

    if(!respuesta.ok){
        alert("No se ha encontrado el carrito");
        return;
    }

    const carrito = await respuesta.json();
    pintarCarritos([carrito]);
}

//Buscar carritos por idUsuario
async function buscarCarritoPorUsuario(event){
    event.preventDefault();

    const idUsuario = document.getElementById("buscarIdUsuario").value;
    const respuesta = await fetch(API + "/usuario/" + idUsuario);

    if(!respuesta.ok){
        alert("No se ha encontrado el carrito del usuario");
        return;
    }

    const carrito = await respuesta.json();
    pintarCarritos([carrito]);
}

//Hago ya la de pintar carritos --> Apunte chat: no hay que usar async, en este caso no nos importa
function pintarCarritos(carritos){
    const cuerpoTabla = document.getElementById("cuerpoTablaCarritos");
    cuerpoTabla.innerHTML = "";

    for(const carrito of carritos){
        const fila = document.createElement("tr");

        fila.innerHTML = `
            <td>${carrito.id}</td>
            <td>${carrito.idUsuario}</td>
            <td>${carrito.emailUsuario}</td>
            <td>${carrito.totalPrecio}</td>
            <td>
                <a href="./carrito.html?id=${carrito.id}">Ver carrito</a>
            </td>
        `;

        cuerpoTabla.appendChild(fila);
    }
}