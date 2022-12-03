import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError, catchError } from 'rxjs';
// import { Renta } from '../re';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class HistorialService {

  private urlEndPoint:string = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  /*
  getHistorial(entrada: string): Observable<Renta[]> {
    var newUrl: string = this.urlEndPoint;
    newUrl += "/busqueda?entrada=" + entrada;
    //return this.http.get<Producto[]>(newUrl);
    return this.http.get<Renta[]>(newUrl)
      .pipe(
      catchError(e => {
        // console.log("error");
        Swal.fire('Error al buscar', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }
  */
}
