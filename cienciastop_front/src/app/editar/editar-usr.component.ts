import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Usuario } from '../usuarios/usuario';
import { UsuarioService } from '../usuarios/usuario.service';

@Component({
  selector: 'app-editar',
  templateUrl: './editar-usr.component.html',
  styleUrls: ['./editar-usr.component.css']
})
export class EditarUsrComponent implements OnInit {

  //Cramos un nuevo usuario vacío
  usuario: Usuario = new Usuario(); 
  
  radio_button_value = null;

  box_options = [
    {
      "name": "Administrador",
      "value": "Administrador",
    },
    {
      "name": "Alumno",
      "value": "Alumno"
    },
    {
      "name": "Proveedor",
      "value": "Proveedor"
    }
  ]


  constructor(
    private usuarioService: UsuarioService,
    private router: Router) { }

  ngOnInit(): void {
    this.usuario = history.state;
  }

  //Este método es llamado desde el formulario
  //Se encarga de disparar el método de guardado de usuarios
  onSubmitForm() {
    console.log(this.usuario);    
    this.commitusuario();
  }

  //Este método llama al createusuario de usuarioService.
  commitusuario() {
    this.usuarioService.crearUsuario(this.usuario).subscribe(
      usuarioData => {
        console.log(usuarioData);
        //Llamamos al método de redirección para volver a la lista de usuarios
        this.redirectusuarioList();
      },
      error => console.log(error));
  }

  //Redirección a lista de usuarios
  redirectusuarioList() {
    this.router.navigate(['/usuarios']);
  }
  goToPumapuntos() {
    this.router.navigate(['/usuarios/pumapuntos']);
  }
}
