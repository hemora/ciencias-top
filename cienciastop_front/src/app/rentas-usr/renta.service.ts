import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { of } from 'rxjs';
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { Renta } from './renta';
import { catchError } from 'rxjs';
import { Router } from '@angular/router';
import swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class RentaService {

  constructor(private http: HttpClient, private router: Router) { }

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})

  private urlEndPoint:string = 'http://localhost:8080/api/rentas';

  rentarProducto(codigo: String, noCT: number): Observable<Object>{
    return this.http.post<Renta>(this.urlEndPoint + '/' + codigo + '/' + noCT, {headers: this.httpHeaders}).pipe(
      catchError( e => {
        swal.fire('Error al rentar el producto', e.error.mensaje, 'error');
        return throwError( () => e);

      })
    );
  }

}
