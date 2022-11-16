import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProdAdminService } from './prod-admin.service';
import { Producto } from '../productos/producto';
import { Observable } from 'rxjs';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { debounceTime } from "rxjs/operators"

@Component({
  selector: 'app-prod-admin',
  templateUrl: './prod-admin.component.html',
  styleUrls: ['./prod-admin.component.css']
})
export class ProdAdminComponent implements OnInit {

  entrada: string = "";
  validos: string = "";
  inputValida: boolean;
  control = new FormControl();

  constructor(private route : Router, private prodAdminService: ProdAdminService) { }

  productos: Producto[];
  porBuscar: string = "";
  
  ngOnInit(): void {
    this.cambiosBusqueda()
    this.prodAdminService.getBuscado().subscribe(
      productos => this.productos = productos
    );
    for (let index = 0; index < this.productos.length; index++) {
      const element = this.productos[index];
      console.log(element.codigo);
      
    }
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
    const btn = document.getElementById("btn_busqueda_prod_admin") as HTMLButtonElement | null;  
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
    this.route.navigate(['/prod-admin'], {queryParams:{data:this.entrada}})
    //console.log(this.entrada);
    // this.productoService.busqueda(this.entrada);
  }
}
