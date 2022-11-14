import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import  { HttpClient } from '@angular/common/http';
import { Monedero } from './monedero';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class MonederoService {

  constructor(private http: HttpClient) { }

  private monederoApi: string = 'http://localhost:8080/api/monederos'

  getMonedero(id: number): Observable<Monedero> {
    //return this.http.get<any>(this.monederoApi + '/' + id).pipe(
    //  catchError(e => {
    //    Swal.fire('Error al obtener el monedero', e.error.mensaje, 'error');
    //    return throwError( () => e);
    //  })
    //)
    return this.http.get<Monedero>(this.monederoApi + '/' + id)
  }

}
