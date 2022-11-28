import { Injectable } from '@angular/core';
import { Producto } from './producto';
//import { PRODUCTOS } from './productos.json';
import { of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import Swal from 'sweetalert2';
import { Router } from '@angular/router'; // Para realizar el redireccionamiento


@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  miStorage = window.localStorage;

  constructor(private http: HttpClient, private router:Router) { }

  private urlEndPoint:string = 'http://localhost:8080/api/productos';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})

  getProductos(): Observable<Producto[]>{
    return this.http.get<Producto[]>(this.urlEndPoint);
  }

  create(producto: Producto): Observable<Producto>{
    console.log(localStorage.getItem("noCT"));
    return this.http.post<Producto>(`${this.urlEndPoint}/${localStorage.getItem("noCT")}`, producto, {headers: this.httpHeaders} )
  }

  getProducto(codigo:string): Observable<Producto>{
    return this.http.get<Producto>(`${this.urlEndPoint}/${codigo}`).pipe(
      catchError(e => {
        this.router.navigate(['/productos']); // Para redireccionar a productos
        Swal.fire('Error al encontrar el prod', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    ) 
  }

  delete(codigo: string): Observable<Producto>{
    console.log("adios");
    return this.http.delete<Producto>(`${this.urlEndPoint}/${codigo}/${localStorage.getItem("noCT")}`, {headers: this.httpHeaders})
  }

  editarProd(prod:Producto): Observable<any>{
    //console.log(localStorage.getItem("noCT"));
    console.log("antes del put");
    // el noCT del que agrego este producto
    //return this.http.put<Producto>(`${this.urlEndPoint}/${prod.codigo}/editar/${localStorage.getItem("noCT")}`, {headers: this.httpHeaders}).pipe(
    return this.http.put<any>(`${this.urlEndPoint}/${prod.codigo}/editar/153249375`, prod,{headers: this.httpHeaders}).pipe(
      catchError( e => {
        //Swal.fire('Error al editar el usuario', `${e.error.mensaje}: ${e.error.error}`, 'error');
        Swal.fire(e.error.mensaje,  e.error.error, 'error');
        return throwError( () => e );
      })
    )
  }

}
