import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import  { HttpClient, HttpHeaders } from '@angular/common/http';
import { Monedero } from './monedero';
import Swal from 'sweetalert2';
import { UserAuthService } from '../util/user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class MonederoService {

  constructor(private http: HttpClient, private userAuthService: UserAuthService) { }

  private monederoApi: string = 'http://localhost:8080/api/monederos'

  private authHeader = {
    headers: new HttpHeaders()
      .set('Authorization',  `Bearer ${this.userAuthService.getToken()}`)
  }
  
  private httpHeaders = new HttpHeaders()
      .set('Authorization',  `Bearer ${this.userAuthService.getToken()}`)
      .set('Content-Type',  'application/json')

  getMonedero(id: number, periodo: string) {
    //var monedero = new Monedero();
    //monedero.id = 0;
    //monedero.ownerId = 0;
    //monedero.status = "";
    //monedero.pumaPuntos = 0;
    return this.http.get<any>(this.monederoApi + '/' + id + '/' + periodo, this.authHeader).pipe(
      catchError(e => {
        Swal.fire('Error al obtener el monedero', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
    //return this.http.get<Monedero>(this.monederoApi + '/' + id, monedero);
  }

  sumarRestarPumaPuntos(id: number, pp: number) {
    return this.http.put<any>(this.monederoApi + '/' + id + '/' + pp, null, {headers: this.httpHeaders}).pipe(
      catchError( e => {
        Swal.fire('Error al sumar/restar puma puntos', e.error, 'error');
        return throwError( () => e);
      })
    )
  }

}
