import { Component, OnInit } from '@angular/core';
import { UserAuthService } from '../util/user-auth.service';
import { UsuarioService } from '../usuarios/usuario.service';
import { Usuario } from '../usuarios/usuario';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  usuario : Usuario = new Usuario();
  
  constructor(public authService: UserAuthService, private usuarioService: UsuarioService) {
  }
  
  
  ngOnInit(): void {        
    let cta = this.authService.getNoCta();
    this.usuarioService.buscarUsuario(cta).subscribe(
      response => {
        this.usuario = response;
      }
    );
    console.log(this.usuario);
  }

}
