import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Usuario } from '../usuarios/usuario';
import { UsuarioService } from '../usuarios/usuario.service';
import { UserAuthService } from '../util/user-auth.service';

@Component({
  selector: 'app-editar',
  templateUrl: './editar-usr.component.html',
  styleUrls: ['./editar-usr.component.css']
})
export class EditarUsrComponent implements OnInit {

  //Cramos un nuevo usuario vacío
  usuario: Usuario = new Usuario();
  angForm: FormGroup;

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
    private router: Router,
    private fb: FormBuilder,
    private userAuthService: UserAuthService) {
    this.createForm();
  }

  ngOnInit(): void {
    this.usuario = history.state;
    console.log(this.usuario);
  }

  createForm() {
    this.angForm = new FormGroup({
      nombreUsr: new FormControl({ nombre: this.usuario.nombre }, Validators.compose([Validators.required])),
      apellidosUsr: new FormControl({ apellidos: this.usuario.apellidos }, Validators.compose([Validators.required])),
      telefono: new FormControl({ telefono: this.usuario.telefono }, Validators.compose([Validators.required])),
      rol: new FormControl({ rol: this.usuario.rol }, Validators.compose([Validators.required])),
      correo: new FormControl({ correo: this.usuario.correo}, Validators.compose([Validators.required])),
    });
  }

  //Este método es llamado desde el formulario
  //Se encarga de disparar el método de guardado de usuarios
  onSubmitForm() {
    if (this.angForm.valid) {
      console.log(this.usuario);
      if(this.usuario.noCT == this.userAuthService.getNoCta()) {
        this.userAuthService.setRole(this.usuario.rol);
      }
      this.commitusuario();      
    } else {
      Swal.fire('Error al editar un usuario', 'El form está incompleto o es incorrecto, intenta de nuevo.', 'error');
    }
  }

  //Este método llama al createusuario de usuarioService.
  commitusuario() {
    this.usuarioService.editarUsuario(this.usuario.noCT, this.usuario).subscribe(
      usuarioData => {
        console.log(usuarioData);
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Se ha editado el usuario correctamente',
          showConfirmButton: false,
          timer: 3500
        })
        //Llamamos al método de redirección para volver a la lista de usuarios
        this.redirectusuarioList();
        this.usuarioService.setNombre();        
      },
      error => console.log(error));
  }

  //Redirección a lista de usuarios
  redirectusuarioList() {
    this.router.navigate(['/usuarios']);
  }
  goToPumapuntos() {
    //this.router.navigate(['/usuarios/pumapuntos']);
    this.router.navigateByUrl('/usuarios/pumapuntos', { state: this.usuario })
  }
}
