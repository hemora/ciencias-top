import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MonederoService } from '../editar-puma-puntos/monedero.service';
import { Usuario } from '../usuarios/usuario';
import { UsuarioService } from '../usuarios/usuario.service';

@Component({
  selector: 'app-agregar-usr',
  templateUrl: './agregar-usr.component.html',
  styleUrls: ['./agregar-usr.component.css']
})
export class AgregarUsrComponent implements OnInit {

  //Cramos un nuevo usuario vacío
  usuario: Usuario = new Usuario();
  angForm: FormGroup;

  type: string = "password";
  isText: boolean = false;  
  eyeIcon: string = "fa-eye-slash";

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

  constructor(private usuarioService: UsuarioService, private router: Router, private fb: FormBuilder, private monederoService: MonederoService) { 
    this.createForm();
  }

  ngOnInit(): void {
    this.usuario.status = 1;
  }

  createForm() {
    this.angForm = new FormGroup({      
      nombreUsr: new FormControl({ nombre: '' }, Validators.compose([Validators.required])),
      apellidosUsr: new FormControl({ apellidos: '' }, Validators.compose([Validators.required])),
      noCT: new FormControl({ noCT: '' }, Validators.compose([Validators.required])),
      telefono: new FormControl({ telefono: '' }, Validators.compose([Validators.required])),
      correo: new FormControl({ correo: '' }, Validators.compose([Validators.required])),
      contrasenya: new FormControl({ contrasenya: '' }, Validators.compose([Validators.required])),
      carrera: new FormControl({ carrera: '' }, Validators.compose([Validators.required])),
      rol: new FormControl({ rol: '' }, Validators.compose([Validators.required])),
    });    
  }

  //Este método es llamado desde el formulario
  //Se encarga de disparar el método de guardado de usuarios
  onSubmitForm() {
    if(this.angForm.valid) {
      console.log(this.usuario);
      this.commitusuario();
    } else {
      Swal.fire('Error al agregar un usuario', 'El form está incompleto o es incorrecto, intenta de nuevo.', 'error');
    }
  }

  //Este método llama al createusuario de usuarioService.
  commitusuario() {
    this.usuarioService.crearUsuario(this.usuario).subscribe(
      usuarioData => {
        console.log(usuarioData);
        //Llamamos al método de redirección para volver a la lista de usuarios
        Swal.fire('Se ha creado un nuevo usuario', `El ${usuarioData.usuario.rol} se añadió con éxito`, 'success');
        //this.usuarioService.activarCrearMonedero(this.usuario.noCT).subscribe(
        //  monederoData => {
        //    console.log(monederoData);
        //  },
        //  error => console.log(error)
        //)
        this.redirectusuarioList();
      },
      error => console.log(error));
    
    this.monederoService.crearMonedero(this.usuario.noCT).subscribe(
      monederoData => {
        console.log(monederoData);
        Swal.fire('Se ha creado un monedero para el periodo actual: ', 'Se añaden 100 puma puntos de regalo', 'success');
      },
      error => console.log(error)
    );
  }

  //Redirección a lista de usuarios
  redirectusuarioList() {
    this.router.navigate(['/usuarios']);
  }

  hideShowPass() {
    this.isText = !this.isText;
    this.isText ? this.eyeIcon = "fa-eye" : this.eyeIcon = "fa-eye-slash";
    this.isText ? this.type = "text" : this.type = "password";
  }
}
