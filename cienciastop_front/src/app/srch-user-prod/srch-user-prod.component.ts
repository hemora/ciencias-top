import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { debounceTime } from 'rxjs/operators';
import { Producto } from '../productos/producto';
import { SrchUserProdService } from './srch-user-prod.service';

@Component({
  selector: 'app-srch-user-prod',
  templateUrl: './srch-user-prod.component.html',
  styleUrls: ['./srch-user-prod.component.css']
})
export class SrchUserProdComponent implements OnInit {

  entrada: string = "";
  control = new FormControl();
  productos: Producto[];

  constructor(
    private route : Router,
    private srchUserProdService: SrchUserProdService) { }

  ngOnInit(): void {
    this.busquedaVacia();
    this.cambiosBusqueda()
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

  public busquedaVacia(): void {
    this.srchUserProdService.allProductos().subscribe(
      productos => this.productos = productos
    );
  }

  public iniciarBusqueda(): void {
    this.srchUserProdService.getBuscado(this.entrada).subscribe(
      productos => this.productos = productos
    );
  }
}
