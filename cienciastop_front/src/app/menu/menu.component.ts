import { Component, OnInit } from '@angular/core';
import { UserAuthService } from '../util/user-auth.service';
import { UsuarioService } from '../usuarios/usuario.service';
import { Usuario } from '../usuarios/usuario';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  noCT : number;
  nombre : string;
  map_data = new Map();
  
  constructor(public authService: UserAuthService, private usuarioService: UsuarioService, private router: Router) {
  }
  
  
  ngOnInit(): void {        
    this.noCT = this.authService.getNoCta();
    this.usuarioService.buscarUsuario(this.noCT).subscribe(
      response => {
        this.map_data = new Map(Object.entries(response));                         
        console.log(this.map_data);
        this.nombre = this.map_data.get("nombre") + " " + this.map_data.get("apellidos");
  });            
    console.log(this.noCT);
  }

  logOut() {
    window.localStorage.clear();
    console.log(localStorage);
    this.authService.clear();        
    console.log(this.authService.getToken());                    
    this.router.navigate(['login']);           
  }

}
