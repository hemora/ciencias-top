import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError, catchError } from 'rxjs';
import { Producto } from '../productos/producto';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class SrchAdminProdService {

  private urlEndPoint:string = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  allProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.urlEndPoint + "/productos ");
  }

  getBuscado(entrada: string): Observable<Producto[]> {
    var newUrl: string = this.urlEndPoint;
    newUrl += "/busqueda?entrada=" + entrada;
    //return this.http.get<Producto[]>(newUrl);
    return this.http.get<Producto[]>(newUrl)
      .pipe(
      catchError(e => {
        // console.log("error");
        Swal.fire('Error al buscar', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }
}
