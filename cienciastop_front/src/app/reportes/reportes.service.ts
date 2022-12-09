import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError, catchError } from 'rxjs';
import Swal from 'sweetalert2';
import { Producto } from '../productos/producto';
import { ProductoService } from '../productos/producto.service';

@Injectable({
  providedIn: 'root'
})
export class ReportesService {

  constructor(private http: HttpClient, private productoService: ProductoService) { }
  private urlEndPoint:string = 'http://localhost:8080/api';
  public httpHeaders = this.productoService.httpHeaders;
  public authHeader = this.productoService.authHeader;

  agrupamientoCarrera(): Observable<Object[]> {
    var newUrl: string = this.urlEndPoint;
    newUrl += "/usuarios/agrupado-carrera";
    return this.http.get<Object[]>(newUrl, this.authHeader)
      .pipe(
      catchError(e => {
        Swal.fire('Error al buscar', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }

  topFiveBaratos(): Observable<Producto[]> {
    var newUrl: string = this.urlEndPoint;
    newUrl += "/productos/top-5-baratos";
    return this.http.get<Producto[]>(newUrl, this.authHeader)
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
    return this.http.get<Object[]>(newUrl, this.authHeader)
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
    return this.http.get<Object[]>(newUrl, this.authHeader)
      .pipe(
      catchError(e => {
        Swal.fire('Error al buscar', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }

  topFiveMasRentados(): Observable<Object[]> {
    var newUrl: string = this.urlEndPoint;
    newUrl += "/rentas/prod-mas-rentados";
    return this.http.get<Object[]>(newUrl, this.authHeader)
      .pipe(
      catchError(e => {
        Swal.fire('Error al buscar', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }

  topTenConMasRetardos(): Observable<Object[]> {
    var newUrl: string = this.urlEndPoint;
    newUrl += "/rentas/usr-mas-retardos";
    return this.http.get<Object[]>(newUrl, this.authHeader)
      .pipe(
      catchError(e => {
        Swal.fire('Error al buscar', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }
}