import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Producto } from '../productos/producto';
import Swal from 'sweetalert2';
import { ProductoService } from '../productos/producto.service';

@Injectable({
  providedIn: 'root'
})
export class SrchUserProdService {

  constructor(private http: HttpClient, private productoService: ProductoService) { }
  
  private urlEndPoint:string = 'http://localhost:8080/api';

  public httpHeaders = this.productoService.httpHeaders;
  public authHeader = this.productoService.authHeader;

  allProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.urlEndPoint + "/productos-filtro ", this.authHeader);
  }

  getBuscado(entrada: string): Observable<Producto[]> {
    var newUrl: string = this.urlEndPoint;
    newUrl += "/busqueda?entrada=" + entrada;
    newUrl += "&filtro=" + true;
    return this.http.get<Producto[]>(newUrl, this.authHeader)
      .pipe(
      catchError(e => {
        Swal.fire('Error al buscar', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }
}
