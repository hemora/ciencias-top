export class Usuario {
    /* Una cadena con un nombre de longitud  igual o menor a xxx */
	nombre: string;
	/* Una cadena con unos apellidos de longitud  igual o menor a xxx */
	apellidos: string;
	/* Un '?entero? que tiene 9 di'gitos */	
	noCT: number;
	/* Un '?Long? que tiene 10 d'igitos */
	telefono: number;
	/* Una cadena con un correo electr'onico de longitud xxx o menor */
	correo: string;
	/* Una cadena con una carrera de longitud xxx o menor */	
	carrera: string;
	/* Una cadena con un rol de longitud xxx o menor */	
	rol: string;
	/* Una cadena con una contrasenya de longitud xxx o menor */	
	contrasenya: string;
	/* Un valor entre 0 y 1 que indica si el usuario est'a desactivado o no */
	status: number;
}
