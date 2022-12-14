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
  
  constructor(public authService: UserAuthService, public usuarioService: UsuarioService, private router: Router) {
  }
  
  
  ngOnInit(): void {      
    if (localStorage.getItem('update') === null) {
      localStorage['update'] = true;
      window.location.reload();
    }
    this.noCT = this.authService.getNoCta();          
    this.usuarioService.setNombre();        
  }

  logOut() {
    window.localStorage.clear();
    console.log(localStorage);
    this.authService.clear();        
    console.log(this.authService.getToken());                    
    this.router.navigate(['login']);           
  }

}
