import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usuario } from './usuario';
import { USUARIOS } from './usuarios.json';
import Swal from 'sweetalert2';
import { UserAuthService } from '../util/user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient
    , private userAuthService: UserAuthService) { }
  
  private urlEndPoint:string = 'http://localhost:8080/api/usuarios';
  private perfilEndPoint:string = 'http://localhost:8080/api/ver-perfil';
  private monederoEndPoint:string = 'http://localhost:8080/api/monederos';

  private authHeader = {
    headers: new HttpHeaders()
      .set('Authorization',  `Bearer ${this.userAuthService.getToken()}`)
  }
  
  private httpHeaders = new HttpHeaders()
      .set('Authorization',  `Bearer ${this.userAuthService.getToken()}`)
      .set('Content-Type',  'application/json')

  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.urlEndPoint, this.authHeader).pipe(
      catchError(e => {
        Swal.fire('Error al cargar los usuarios', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    );
  }

  eliminar(noCT: Number): Observable<Usuario>{
    return this.http.delete<Usuario>(`${this.urlEndPoint}/${noCT}`, {headers: this.httpHeaders}).pipe(
      catchError( e => {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'No se ha podido eliminar al usuario',
          showConfirmButton: false,
          timer: 3500});
        return throwError( () => e);
      })
    );
  }

  crearUsuario(usuario: Usuario): Observable<any>{
    return this.http.post<any>(this.urlEndPoint, usuario, this.authHeader).pipe(
      catchError(e => {
        Swal.fire('Error al crear un usuario', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    );
  }

  activarCrearMonedero(noCT: number): Observable<any> {
    return this.http.post<any>(this.monederoEndPoint + '/' + noCT, {headers: this.httpHeaders}).pipe(
      catchError( e => {
        Swal.fire('Error al reactivar/crear monedero', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    );
  }

  desactivarMonedero(noCT: number): Observable<any> {
    return this.http.delete<any>(this.monederoEndPoint + '/' + noCT, {headers: this.httpHeaders}).pipe(
      catchError( e => {
        Swal.fire('Error al reactivar/crear monedero', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    );
  }

  buscarUsuario(noCT: number) {
    return this.http.get<any>(this.urlEndPoint + '/' + noCT, this.authHeader).pipe(
      catchError( e => {
        Swal.fire('Error al obtener el usuario', e.error.mensaje, 'error');
        return throwError( () => e );
      })
    )
  }

  buscarUsuarioC(correo: String) {
    return this.http.get<any>(this.urlEndPoint + '/correo/' + correo, this.authHeader).pipe(
      catchError( e => {
        Swal.fire('Error al obtener el usuario', e.error.mensaje, 'error');
        return throwError( () => e );
      })
    )
  }

  buscarUsuarioN(nombre: String) {
    return this.http.get<any>(this.urlEndPoint + '/nombre/' + nombre, this.authHeader).pipe(
      catchError( e => {
        Swal.fire('Error al obtener el usuario', e.error.mensaje, 'error');
        return throwError( () => e );
      })
    )
  }

  busquedaAuxiliar(cadena: String){
    const patronN = '[0-9]{9}';
    const patronNumero = cadena.match(patronN);
    const patron = /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    const patronCorreo = cadena.match(patron);
    
    if(patronNumero!=null && patronNumero.length>0 ){
      console.log(patronNumero);
      var number = Number(cadena);
      return  this.buscarUsuario(number);
    }
    if(patronCorreo!=null && patronCorreo.length>0){
      console.log(patronCorreo);
      return this.buscarUsuarioC(cadena);
    }else{
      return this.buscarUsuarioN(cadena);
    }

  }

  
  editarUsuario(noCT: number, usuario: Usuario) {
    return this.http.put<any>(this.urlEndPoint + '/' + noCT, usuario, this.authHeader).pipe(
      catchError( e => {
        Swal.fire('Error al editar el usuario', e.error.mensaje, 'error');
        return throwError( () => e );
      })
    )
  }

  getPerfilAdmin(noCT: number): Observable<Object> {
    return this.http.get(this.urlEndPoint + '/ver-perfil/' + noCT, { headers: this.httpHeaders }).pipe(
      catchError(e => {
        Swal.fire(
          { 
            title: 'No hay rentas activas que mostrar',  
            text: e.error.mensaje,  
            icon: 'warning'
          }
        );
        return throwError(() => e);

      })
    );
  }

  getPerfilUsr(noCT: number): Observable<Object> {
    return this.http.get(this.perfilEndPoint + '/' + noCT, { headers: this.httpHeaders }).pipe(
      catchError(e => {
        Swal.fire(
          { 
            title: 'No hay rentas activas que mostrar',  
            text: e.error.mensaje,  
            icon: 'warning'
          }
        );
        return throwError(() => e);

      })
    );
  }
}
