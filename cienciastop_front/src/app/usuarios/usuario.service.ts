import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Usuario } from './usuario';
import { USUARIOS } from './usuarios.json';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { }
  
  private urlEndPoint:string = 'http://localhost:8080/api/usuarios';

  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.urlEndPoint);
  }
  crearUsuario(usuario: Usuario): Observable<Object>{
    return this.http.post(this.urlEndPoint, usuario);
  }
}
