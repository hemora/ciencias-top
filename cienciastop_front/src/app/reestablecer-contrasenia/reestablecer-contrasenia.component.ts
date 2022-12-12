import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { SesionService } from '../inicio-ui/sesion.service';
import { Usuario } from '../usuarios/usuario';
import { UserAuthService } from '../util/user-auth.service';

@Component({
  selector: 'app-reestablecer-contrasenia',
  templateUrl: './reestablecer-contrasenia.component.html',
  styleUrls: ['./reestablecer-contrasenia.component.css']
})
export class ReestablecerContraseniaComponent implements OnInit {

  type: string = "password";
  isText: boolean = false;
  eyeIcon: string = "fa-eye-slash";

  loginForm!: FormGroup;

  private usuario: Usuario;

  constructor(private fb: FormBuilder
      , private sesionService: SesionService
      , private userAuthService: UserAuthService
      , private router: Router) { }

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
      const payload = { "userName" : noCta , "userPassword" : pw } 
      var foo = this.sesionService.reestablecerContrasenia(noCta, pw).subscribe(
        (response: any) => { 
          console.log("Contraseña actualizada")
          console.log(response)

          Swal.fire('Contraseña reestablecida'
          , 'Aquí va el mensaje de que todo salió bien'
          , 'success');

          this.router.navigate([''])
        },
        (error) => {
          console.log(error);
        }
      );
    } else {
      console.log("ERROR: Form is not valid");
    }
  }

  reestablecerContrasenia() {
    this.router.navigate(['/usuarios/reestablecer-contrasenia'])
  }

  isLoggedIn() {
    return this.userAuthService.isLoggedIn();
  }

}
