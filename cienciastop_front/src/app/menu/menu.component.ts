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
  
  constructor(public authService: UserAuthService, private usuarioService: UsuarioService, private router: Router) {
  }
  
  
  ngOnInit(): void {        
    this.noCT = this.authService.getNoCta();    
    console.log(this.noCT);
  }

  logOut() {
    this.authService.clear();
    //this.router.navigate(['login'])
    console.log(this.authService.getToken())
  }

}
