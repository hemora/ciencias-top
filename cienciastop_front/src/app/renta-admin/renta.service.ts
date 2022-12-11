import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { of } from 'rxjs';
import { HttpClient, HttpHeaders  } from '@angular/common/http';
import { Renta } from './renta';
import { catchError } from 'rxjs';
import swal from 'sweetalert2';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RentaService {

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})

  constructor(private http: HttpClient, private router: Router) { }

  private urlEndPoint:string = 'http://localhost:8080/api/rentas';
  private urlEndPoint_perfil: string = 'http://localhost:8080/api/ver-perfil';
  
  getRentas(): Observable<Renta[]> {
    return this.http.get<Renta[]>(this.urlEndPoint);
  }
  update(id: number): Observable<Renta>{
    return this.http.put<Renta>( this.urlEndPoint+ '/' + id, {headers: this.httpHeaders}).pipe(
      catchError(e =>{
        this.router.navigate(['/renta-admin'])
        swal.fire('Error al actualizar producto', e.error.mensaje, "error");
        return throwError( () => e);
        
      })
    );
  }
  
  getRentasUsr(noCT: number): Observable<Object> {
    return this.http.get(this.urlEndPoint_perfil + '/' + noCT, { headers: this.httpHeaders }).pipe(
      catchError(e => {
        swal.fire(
          { 
            title: 'No se encontro informacion de rentas del usuario',  
            text: e.error.mensaje,  
            icon: 'warning'
          }
        );
        return throwError(() => e);

      })
    );
  }
}