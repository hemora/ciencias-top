import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Producto } from '../productos/producto';
import { Observable, catchError, throwError } from 'rxjs';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class ProdUsuarioService {

  private urlEndPoint:string = 'http://localhost:8080/api/productos';

  constructor(private http: HttpClient) { }

  getBuscado(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.urlEndPoint);
    /*
    return this.http.get<Producto[]>(this.urlEndpoint)
      .pipe(
      catchError(e => {
        console.log("error");
        Swal.fire('Error al buscar', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
    */
  }
}