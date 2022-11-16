import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usuario } from './usuario';
import { USUARIOS } from './usuarios.json';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})

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

  buscarUsuario(noCT: number) {
    return this.http.get<any>(this.urlEndPoint + '/' + noCT).pipe(
      catchError( e => {
        Swal.fire('Error al obtener el usuario', e.error.mensaje, 'error');
        return throwError( () => e );
      })
    )
  }

  editarUsuario(noCT: number, usuario: Usuario) {
    return this.http.put<any>(this.urlEndPoint + '/' + noCT, usuario).pipe(
      catchError( e => {
        Swal.fire('Error al editar el usuario', e.error.mensaje, 'error');
        return throwError( () => e );
      })
    )
  }
}
