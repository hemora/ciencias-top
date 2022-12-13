import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { of } from 'rxjs';
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { Renta } from './renta';
import { catchError } from 'rxjs';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { UserAuthService } from '../util/user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class RentaService {

  constructor(private http: HttpClient, 
    private userAuthService: UserAuthService) { }
    
    private urlEndPoint:string = 'http://localhost:8080/api/rentas';

    private authHeader = {
      headers: new HttpHeaders()
        .set('Authorization',  `Bearer ${this.userAuthService.getToken()}`)
    }
    
    private httpHeaders = new HttpHeaders()
        .set('Authorization',  `Bearer ${this.userAuthService.getToken()}`)
        .set('Content-Type',  'application/json')

 
    rentarProducto(codigo: String, noCT: number): Observable<RentaJSON>{
      return this.http.post<RentaJSON>(this.urlEndPoint + '/' + codigo + '/' + noCT, null,{headers: this.httpHeaders}).pipe(
        catchError( e => {
         Swal.fire('Error al rentar el producto', e.error.mensaje, 'error');
          return throwError( () => e);

        })
      );
    }

    verRenta(id: number): Observable<Renta>{
      return this.http.get<Renta>(this.urlEndPoint + '/' + id, this.authHeader);
    }
  
  }
  
  export class RentaJSON{
    mensaje: string;
    renta: Renta;
  }

