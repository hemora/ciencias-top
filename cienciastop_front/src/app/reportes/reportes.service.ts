import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError, catchError } from 'rxjs';
import Swal from 'sweetalert2';
import { Producto } from '../productos/producto';

@Injectable({
  providedIn: 'root'
})
export class ReportesService {

  private urlEndPoint:string = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  agrupamientoCarrera(): Observable<Object[]> {
    var newUrl: string = this.urlEndPoint;
    newUrl += "/usuarios/agrupado-carrera";
    return this.http.get<Object[]>(newUrl)
      .pipe(
      catchError(e => {
        // console.log("error");
        Swal.fire('Error al buscar', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }

  topFiveBaratos(): Observable<Producto[]> {
    var newUrl: string = this.urlEndPoint;
    newUrl += "/productos/top-5-baratos";
    return this.http.get<Producto[]>(newUrl)
      .pipe(
      catchError(e => {
        Swal.fire('Error al generar reporte de mas baratos', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }

  agrupamientoStatus(): Observable<Object[]> {
    var newUrl: string = this.urlEndPoint;
    newUrl += "/usuarios/agrupado-status";
    return this.http.get<Object[]>(newUrl)
      .pipe(
      catchError(e => {
        Swal.fire('Error al buscar', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }

  topFiveConMasRentas(): Observable<Object[]> {
    var newUrl: string = this.urlEndPoint;
    newUrl += "/rentas/con-mas-rentas";
    return this.http.get<Object[]>(newUrl)
      .pipe(
      catchError(e => {
        // console.log("error");
        Swal.fire('Error al buscar', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }

  topFiveMasRentados(): Observable<Object[]> {
    var newUrl: string = this.urlEndPoint;
    newUrl += "/rentas/prod-mas-rentados";
    return this.http.get<Object[]>(newUrl)
      .pipe(
      catchError(e => {
        // console.log("error");
        Swal.fire('Error al buscar', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }
}