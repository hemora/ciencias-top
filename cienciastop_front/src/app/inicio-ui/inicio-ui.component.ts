import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { of } from 'rxjs';
import { SesionService } from './sesion.service';
import { Usuario } from './usuario';

@Component({
  selector: 'app-inicio-ui',
  templateUrl: './inicio-ui.component.html',
  styleUrls: ['./inicio-ui.component.css']
})
export class InicioUiComponent implements OnInit {

  type: string = "password";
  isText: boolean = false;
  eyeIcon: string = "fa-eye-slash";

  loginForm!: FormGroup;

  private usuario: Usuario;

  constructor(private fb: FormBuilder
      , private sesionService: SesionService) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    })
  }

  hideShowPass() {
    this.isText = !this.isText;
    this.isText ? this.eyeIcon = "fa-eye" : this.eyeIcon = "fa-eye-slash";
    this.isText ? this.type = "text" : this.type = "password";
  }

  onSubmit() {
    if(this.loginForm.valid) {
      console.log(this.loginForm.value);
      const noCta = (this.loginForm.value).username;
      const pw = (this.loginForm.value).password;
      var foo = this.sesionService.verifSesion(noCta, pw).subscribe(
        (u: Usuario) => { 
          console.log(u);
          if(u.contrasenya == pw) {
            alert('Autenticación exitosa');
          }
          this.usuario = u;
        }
      );
    } else {
      console.log("ERROR: Form is not valid");
    }
  }

  //TODO: Obtener usuario y contraseña del form y pasárselos a verifSesion
  //RESOURCE: https://www.youtube.com/watch?v=p9ScsROLjdI
  //iniciarSesion() {
  //  this.usuario = this.sesionService.verifSesion();
  //}

}
