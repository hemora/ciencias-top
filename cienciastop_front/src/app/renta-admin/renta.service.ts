import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { of } from 'rxjs';
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { Renta } from './renta';
import { catchError } from 'rxjs';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { UserAuthService } from '../util/user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class RentaService {

  constructor(private http: HttpClient, private router: Router, private userAuthService: UserAuthService) { }

  private urlEndPoint:string = 'http://localhost:8080/api/rentas';  

  private authHeader = {
    headers: new HttpHeaders()
      .set('Authorization',  `Bearer ${this.userAuthService.getToken()}`)
  }
  
  private httpHeaders = new HttpHeaders()
      .set('Authorization',  `Bearer ${this.userAuthService.getToken()}`)
      .set('Content-Type',  'application/json')
  
  getRentas(): Observable<Renta[]> {
    return this.http.get<Renta[]>(this.urlEndPoint, this.authHeader);
  }
  update(id: number): Observable<Renta>{
    return this.http.put<any>( this.urlEndPoint+ '/' + id, null ,{headers: this.httpHeaders}).pipe(
      catchError( e => {
        Swal.fire('Error al rentar el producto', e.error.mensaje, 'error');
         return throwError( () => e);

       })
    );
  }
    
}