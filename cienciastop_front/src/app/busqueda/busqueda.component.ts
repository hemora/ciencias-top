import { query } from '@angular/animations';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { debounceTime } from "rxjs/operators"
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-busqueda',
  templateUrl: './busqueda.component.html',
  styleUrls: ['./busqueda.component.css']
})
export class BusquedaComponent implements OnInit {

  entrada: string = "";
  validos: string = "";
  inputValida: boolean;
  control = new FormControl();

  constructor(private route : Router) { }

  ngOnInit(): void {
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
    const btn = document.getElementById("btn_busqueda") as HTMLButtonElement | null;  
    if (this.entrada.length < 3  ||
        this.entrada.length > 30 ||
        this.caracteresInvalidos() == true) {
      // console.log("invalido");
      btn?.setAttribute('disabled', '');
      return true;
    } else {
      // console.log("valido")
      btn?.removeAttribute('disabled');
      return false;
    }
  }

  /**
   * Regresa True si tiene caracteres invalidos, False en otro caso.
   * @returns True si tiene caracteres invalidos, False en otro caso. 
   */
  caracteresInvalidos(): boolean {
    this.validos = "qwertyuiop" + "asdfghjklñ" + "zxcvbnm" + "1234567890";
		this.validos += "QWERTYUIOP" + "ASDFGHJKLÑ" + "ZXCVBNM";
		this.validos += "áéíóú" + "ÁÉÍÓÚ" + "üÜ" + " ,.*$¿?¡!";
		this.inputValida = false;
		let caracteres: Array<string> = this.entrada.split(""); 
    var i: number;
		for (i = 0; i < caracteres.length; i++) {
			if (!this.validos.includes("" + caracteres[i])) {
				this.inputValida = true;
			}
		}
		return this.inputValida;
  }

  public iniciarBusqueda():void {
    this.route.navigate(['/prod-usuario'], {queryParams:{data:this.entrada}})
    //console.log(this.entrada);
    // this.productoService.busqueda(this.entrada);
  }
}
