import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError, catchError } from 'rxjs';
import { Producto } from '../productos/producto';
import Swal from 'sweetalert2';
import { HttpHeaders } from '@angular/common/http';
import { UserAuthService } from '../util/user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class SrchAdminProdService {

  constructor(private http: HttpClient, 
    private userAuthService: UserAuthService) { }
  
    private urlEndPoint:string = 'http://localhost:8080/api';

  private authHeader = {
    headers: new HttpHeaders()
      .set('Authorization',  `Bearer ${this.userAuthService.getToken()}`)
  }

  allProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.urlEndPoint + "/productos ", this.authHeader);
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
