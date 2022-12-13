import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { debounceTime } from 'rxjs/operators';
import { Monedero } from '../editar-puma-puntos/monedero';
import { MonederoService } from '../editar-puma-puntos/monedero.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  
  // usuario provicional - reemplazar con datos del usuario logeado
  usuario = {nombre: 'Daniel', apellidos:'Ruelas Milanés', noCT:317804520, telefono:5455111666, correo:'sango265@ciencias.unam.mx', carrera:'Ciencias de la Computación', rol:' Administrador', contrasenya:'12345678', status:1}
  monedero: Monedero = new Monedero();
  entrada: string = "";
  control = new FormControl();

  constructor(private route : Router, private monederoService: MonederoService) { }

  ngOnInit(): void {
    this.cambiosBusqueda();

    let periodoAux = new Intl.DateTimeFormat('es-MX').format(new Date()).split('/');    
    let periodo = periodoAux[2] + '-' + periodoAux[1];

    this.monederoService.getMonedero(this.usuario.noCT, periodo).subscribe(
      response => {
        this.monedero = response.monedero;
      }
    );
  }

  /**
   * Verifica el texto ingresado por el ususario cada 300 ms.
   */
   cambiosBusqueda() {
    this.control.valueChanges
    .pipe(
      debounceTime(300)
    )
    .subscribe(query => {
      this.validarEntrada(query);
    })
  }

  /**
   * Regresa True si la lognitud de lo ingresado es mayor a 3, menor a 30 y si no
   * posee ningun caracter invalido, False en otro caso.
   * @param query la entrada del usuario.
   * @returns True si la lognitud de lo ingresado es mayor a 3, menor a 30 y si no
   * posee ningun caracter invalido, False en otro caso.
   */
   validarEntrada(query): boolean {
    this.entrada = (query as string);
    const btn = document.getElementById("btn-srch-user-prod") as HTMLButtonElement | null;  
    if (this.entrada.length < 3  ||
        this.entrada.length > 30 ||
        this.caracteresInvalidos() == true) {
      btn?.setAttribute('disabled', '');
      return true;
    } else {
      btn?.removeAttribute('disabled');
      return false;
    }
  }

  /**
   * Regresa True si tiene caracteres invalidos, False en otro caso.
   * @returns True si tiene caracteres invalidos, False en otro caso. 
   */
  caracteresInvalidos(): boolean {
    var validos: string;
    validos = "qwertyuiop" + "asdfghjklñ" + "zxcvbnm" + "1234567890";
		validos += "QWERTYUIOP" + "ASDFGHJKLÑ" + "ZXCVBNM";
		validos += "áéíóú" + "ÁÉÍÓÚ" + "üÜ" + " ,.*$¿?¡!";
		var inputValida: boolean = false;
		let caracteres: Array<string> = this.entrada.split(""); 
    var i: number;
		for (i = 0; i < caracteres.length; i++) {
			if (!validos.includes("" + caracteres[i])) {
				inputValida = true;
			}
		}
		return inputValida;
  }

  public iniciarBusqueda(): void {
    // console.log("envie: " + this.entrada);
    this.route.navigate(['productos/srch-user-prod'], {queryParams:{data:this.entrada}})
  }

}
