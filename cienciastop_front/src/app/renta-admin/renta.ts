import { Producto } from "../productos/producto";
import { Usuario } from "../usuarios/usuario";

export class Renta {
    id: number;
    usuario: Usuario;
    producto: Producto;
    fecha_renta: Date;
    fecha_entrega: Date;
    status_entrega: boolean;

}
