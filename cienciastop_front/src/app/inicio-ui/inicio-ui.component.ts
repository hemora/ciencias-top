import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { of } from 'rxjs';
import { UserAuthService } from '../util/user-auth.service';
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
  restablecer: boolean =true;
  eyeIcon: string = "fa-eye-slash";

  loginForm!: FormGroup;
  restablecerForm!: FormGroup;

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

    this.restablecerForm= this.fb.group({
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
      var foo = this.sesionService.authentication(payload).subscribe(
        (response: any) => { 
          console.log("Response:")
          console.log(response.jwtToken);
          console.log(response.usuario.rol);
          this.userAuthService.setRole(response.usuario.rol);
          this.userAuthService.setToken(response.jwtToken);
          // Set NoCta.
          this.userAuthService.setNoCta(response.usuario.noCT);

          console.log("Local Storage:")
          console.log(this.userAuthService.getRol());
          console.log(this.userAuthService.getToken());
          console.log(this.userAuthService.getNoCta());

          this.router.navigate(['/productos'])
        },
        (error) => {
          console.log(error);
        }
      );
    } else {
      console.log("ERROR: Form is not valid");
    }
  }

  isLoggedIn() {
    return this.userAuthService.isLoggedIn();
  }

  enviar() {
    this.unpop();
    if(this.restablecerForm.valid) {
      console.log(this.restablecerForm.value);
      const noCta = (this.restablecerForm.value).username;
      const pw = (this.restablecerForm.value).password;
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

  pop(){
    var popup = document.getElementById("myPopup");
    /*popup.classList.toggle("show");*/
    popup.classList.add("show");
  }


  unpop(){
    var popup = document.getElementById("myPopup");
    popup.style.display = "none";
  }

}
