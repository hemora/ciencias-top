import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { Monedero } from '../editar-puma-puntos/monedero';
import { MonederoService } from '../editar-puma-puntos/monedero.service';
import { Usuario } from '../usuarios/usuario';
import { UsuarioService } from '../usuarios/usuario.service';
import { UserAuthService } from '../util/user-auth.service';
import { HeaderService } from './header.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  private subscription: Subscription; //important to create a subscription 
  
  // usuario provicional - reemplazar con datos del usuario logeado  
  monedero: Monedero = new Monedero();
  entrada: string = "";
  control = new FormControl();  

  constructor(private route : Router, 
    public authService: UserAuthService,     
    public headerService: HeaderService) { }

  ngOnInit(): void {       

    this.cambiosBusqueda();       

    this.headerService.setPumaPts();    
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
