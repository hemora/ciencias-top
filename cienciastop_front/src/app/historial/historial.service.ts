import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError, catchError } from 'rxjs';
// import { Renta } from '../re';
import Swal from 'sweetalert2';
import { ProductoService } from '../productos/producto.service';
import { UserAuthService } from '../util/user-auth.service';
import { Renta } from '../rentas-usr/renta';

@Injectable({
  providedIn: 'root'
})
export class HistorialService {

  constructor(private http: HttpClient,
    private productoService: ProductoService,
    private userAuthService: UserAuthService) { }

  private urlEndPoint:string = 'http://localhost:8080/api';
  public noCT: number = this.userAuthService.getNoCta();
  public authHeader = this.productoService.authHeader;

  getHistorial(): Observable<Renta[]> {
    var newUrl: string = this.urlEndPoint;
    newUrl += "/rentas/historial?entrada=" + this.noCT;
    console.log("por buscar "+ this.noCT);
    return this.http.get<Renta[]>(newUrl, this.authHeader)
      .pipe(
      catchError(e => {
        // console.log("error");
        Swal.fire('Error al buscar', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }
}
