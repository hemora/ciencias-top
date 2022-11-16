import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usuario } from './usuario';
import { USUARIOS } from './usuarios.json';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { }
  
  private urlEndPoint:string = 'http://localhost:8080/api/usuarios';

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})

  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.urlEndPoint);
  }

  eliminar(noCT: Number): Observable<Usuario>{
    return this.http.delete<Usuario>(`${this.urlEndPoint}/${noCT}`, {headers: this.httpHeaders});
  }

  crearUsuario(usuario: Usuario): Observable<Object>{
    return this.http.post(this.urlEndPoint, usuario);
  }
}
