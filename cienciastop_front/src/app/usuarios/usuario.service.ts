import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Usuario } from './usuario';
import { USUARIOS } from './usuarios.json';
import { catchError, throwError } from 'rxjs';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { }
  
  private urlEndPoint:string = 'http://localhost:8080/api/usuarios';

  
  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.urlEndPoint).pipe(
      catchError(e => {
        Swal.fire('Error al cargar los usuarios', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    );
  }

  crearUsuario(usuario: Usuario): Observable<any>{
    return this.http.post<any>(this.urlEndPoint, usuario).pipe(
      catchError(e => {
        Swal.fire('Error al crear un usuario', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    );
  }
}
