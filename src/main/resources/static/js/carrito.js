const API = "/api/carritos";

document.getElementById("botonActualizarCarrito").addEventListener("click", actualizarCarrito);
document.getElementById("botonCargarLineas").addEventListener("click", cargarLineas);
document.getElementById("botonAddLinea").addEventListener("click", addLineaCarrito);
document.getElementById("botonActualizarLinea").addEventListener("click", actualizarLineaCarrito);
document.getElementById("botonBorrarLinea").addEventListener("click", borrarLineaCarrito);
document.getElementById("botonBorrarCarrito").addEventListener("click", borrarCarrito);

//Ahora lo que quiero es hacer un get pero de un carrito en especifico
//Entonces, lo que quiero buscar es que desde la URL pueda acceder directamente
//por ejemplo, poner /caritos/5 (que sea su id) y me de ya la página recargada
//Me hace falta esto para ello:

window.addEventListener("load", cargarCarritoDesdeUrl);

//Una vez que lo tengo
//creo la funcion para obtener el carrito desde la URL

function getCarritoDesdeURL(){
    const parametros = new URLSearchParams(window.location.search);
    return parametros.get("id");
}

//Cargo el carrito automaticamente si viene en la URL, ----> GET
async function cargarCarritoDesdeURL(){
    const idCarrito = getCarritoDesdeURL()

    //Apunte chat: tengo que esperar con await si se cumple la condicion
    // a que se cargue un carrito y sus respectivas lineas
    //ambas funciones se crearan en seguida
    if (idCarrito !== null) {
        await cargarCarrito(idCarrito);
        await cargarLineasPorId(idCarrito);
    }
}

//Ahora me creo cargarCarrito
async function cargarCarrito(idCarrito){
    const respuesta = await fetch(API + "/" + idCarrito);

    if(!respuesta.ok){
        alert("No se ha podido cargar el carrito");
        return;
    }

    const carrito = await respuesta.json();

    document.getElementById("idCarritoMostrado").textContent = carrito.id;
    document.getElementById("idUsuarioMostrado").textContent = carrito.idUsuario;
    document.getElementById("emailUsuarioMostrado").textContent = carrito.emailUsuario;
    document.getElementById("totalPrecioMostrado").textContent = carrito.totalPrecio;

    //Apunte chat: claro, no tengo que usar solamente los del propio metodo
    //un carrito puede cambiar de muchas maneras
    //puedo actualizar su id, añadirlo, actualizar una linea
    //y tengo que tener todo eso en cuenta:
    document.getElementById("actualizarIdCarrito").value = carrito.id;
    document.getElementById("actualizarIdUsuario").value = carrito.idUsuario;
    document.getElementById("actualizarEmailUsuario").value = carrito.emailUsuario;
    document.getElementById("addIdCarrito").value = carrito.id;
    document.getElementById("actualizarLineaIdCarrito").value = carrito.id;
    document.getElementById("borrarLineaIdCarrito").value = carrito.id;
    document.getElementById("borrarIdCarrito").value = carrito.id;

}

//Actualizar carrito
async function actualizarCarrito(event){
    event.preventDefault();

    const idCarrito = document.getElementById("actualizarIdCarrito").value;

    const carrito = {
        idUsuario: Number(document.getElementById("actualizarIdUsuario").value),
        emailUsuario: document.getElementById("actualizarEmailUsuario").value
    };

    const respuesta = await fetch(API + "/" + idCarrito,{
        method: "PUT",
        headers: {
            "Content-Type":"application/json"
        },
        body: JSON.stringify(carrito)
    });

    if(respuesta.ok){
        alert("Carrito actualizado correctamente");
        cargarCarrito(idCarrito);
    }else{
        alert("Error al actualizuar");
    }
}

//Cargar líneas usando el formulario
async function cargarLineas(event){
    event.preventDefault();

    const idCarrito = document.getElementById("addIdCarrito").value;

    if(idCarrito === ""){
        alert("Introduce un idCarrito");
        return;
    }

    await cargarLineasPorId(idCarrito);
}

//Cargar líneas por id
async function cargarLineasPorId(idCarrito){
    const respuesta = await fetch(API + "/" + idCarrito + "/lineas");

    if(!respuesta.ok){
        alert("No se han podido cargar las líneas");
        return;
    }

    const lineas = await respuesta.json();
    pintarLineas(lineas);
}

//Pintar líneas en la tabla
function pintarLineas(lineas){
    const cuerpoTabla = document.getElementById("cuerpoTablaLineas");
    cuerpoTabla.innerHTML = "";

    for(const linea of lineas){
        const fila = document.createElement("tr");

        fila.innerHTML = `
            <td>${linea.id}</td>
            <td>${linea.idArticulo}</td>
            <td>${linea.precioUnitario}</td>
            <td>${linea.numeroUnidades}</td>
            <td>${linea.costeLinea}</td>
            
       
        `;

        cuerpoTabla.appendChild(fila);
    }
}

//Rellenar formulario de actualizar línea
function rellenarLinea(idLinea, idArticulo, precioUnitario, numeroUnidades){
    document.getElementById("actualizarIdLinea").value = idLinea;
    document.getElementById("actualizarIdArticulo").value = idArticulo;
    document.getElementById("actualizarPrecioUnitario").value = precioUnitario;
    document.getElementById("actualizarNumeroUnidades").value = numeroUnidades;
}

//Añadir línea a carrito
async function addLineaCarrito(event){
    event.preventDefault();

    const idCarrito = document.getElementById("addIdCarrito").value;

    const linea = {
        idArticulo: Number(document.getElementById("addIdArticulo").value),
        precioUnitario: Number(document.getElementById("addPrecioUnitario").value),
        numeroUnidades: Number(document.getElementById("addNumeroUnidades").value)
    };

    const respuesta = await fetch(API + "/" + idCarrito + "/lineas", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(linea)
    });

    if(respuesta.ok){
        alert("Línea añadida correctamente");
        await cargarCarrito(idCarrito);
        await cargarLineasPorId(idCarrito);
    }else{
        alert("Error al añadir la línea");
    }
}

//Actualizar línea carrito
async function actualizarLineaCarrito(event){
    event.preventDefault();

    const idCarrito = document.getElementById("actualizarLineaIdCarrito").value;
    const idLinea = document.getElementById("actualizarIdLinea").value;

    const linea = {
        idArticulo: Number(document.getElementById("actualizarIdArticulo").value),
        precioUnitario: Number(document.getElementById("actualizarPrecioUnitario").value),
        numeroUnidades: Number(document.getElementById("actualizarNumeroUnidades").value)
    };

    const respuesta = await fetch(API + "/" + idCarrito + "/lineas/" + idLinea, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(linea)
    });

    if(respuesta.ok){
        alert("Línea actualizada correctamente");
        await cargarCarrito(idCarrito);
        await cargarLineasPorId(idCarrito);
    }else{
        alert("Error al actualizar la línea");
    }
}

//Borrar línea carrito desde formulario
async function borrarLineaCarrito(event){
    event.preventDefault();

    const idCarrito = document.getElementById("borrarLineaIdCarrito").value;
    const idLinea = document.getElementById("borrarIdLinea").value;

    await borrarLinea(idCarrito, idLinea);
}

//Borrar línea carrito desde tabla
async function borrarLineaDesdeTabla(idLinea){
    const idCarrito = document.getElementById("borrarLineaIdCarrito").value;

    await borrarLinea(idCarrito, idLinea);
}

//Borrar línea
async function borrarLinea(idCarrito, idLinea){
    const respuesta = await fetch(API + "/" + idCarrito + "/lineas/" + idLinea, {
        method: "DELETE"
    });

    if(respuesta.ok){
        alert("Línea borrada correctamente");
        await cargarCarrito(idCarrito);
        await cargarLineasPorId(idCarrito);
    }else{
        alert("Error al borrar la línea");
    }
}

//Borrar carrito
async function borrarCarrito(event){
    event.preventDefault();

    const idCarrito = document.getElementById("borrarIdCarrito").value;

    const respuesta = await fetch(API + "/" + idCarrito, {
        method: "DELETE"
    });

    if(respuesta.ok){
        alert("Carrito borrado correctamente");
        window.location.href = "./index.html";
    }else{
        alert("Error al borrar el carrito");
    }
}

