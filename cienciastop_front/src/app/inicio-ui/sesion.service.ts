import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import  { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usuario } from './usuario';

@Injectable({
  providedIn: 'root'
})
export class SesionService {

  constructor(private http: HttpClient) { }

  private urlEndpoint: string = 'http://localhost:8080/api/usuarios';
  private authEndpoint: string = 'http://localhost:8080/authenticate';
  private reestablecerEndpoint: string = 'http://localhost:8080/api/usuarios/restablecer-contrasenia';

  requestHeader = new HttpHeaders(
    { "No-Auth" : "True"}
  );

  verifSesion(noCt: number, passwordHash: string): Observable<Usuario> {
    var usuarioAlmacenado = this.http.get<Usuario>(this.urlEndpoint + '/' + noCt)
    return usuarioAlmacenado;
    //if verifEquals(usuarioAlmacenado.getPassword(), passwordHash)
    //  then 
  }

  authentication(loginData: any) {
    return this.http.post(this.authEndpoint, loginData, {headers : this.requestHeader})
  }

  reestablecerContrasenia(noCta: number, pw: string) {
    return this.http.post(`${this.reestablecerEndpoint}/${noCta}/${pw}`, {headers : this.requestHeader})
  }

}