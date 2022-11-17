import { Injectable } from '@angular/core';
import { Producto } from './producto';
import { PRODUCTOS } from './productos.json';
import { Observable } from 'rxjs';
import { of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  miStorage = window.localStorage;

  constructor(private http: HttpClient) { }

  private urlEndPoint:string = 'http://localhost:8080/api/productos';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})

  getProductos(): Observable<Producto[]>{
    return this.http.get<Producto[]>(this.urlEndPoint);
  }

  create(producto: Producto): Observable<Producto>{
    console.log(localStorage.getItem("noCT"));
    return this.http.post<Producto>(`${this.urlEndPoint}/${localStorage.getItem("noCT")}`, producto, {headers: this.httpHeaders} )
  }

  getProducto(codigo: string): Observable<Producto>{
    return this.http.get<Producto>(`${this.urlEndPoint}/${codigo}`)
  }

  delete(codigo: string): Observable<Producto>{
    console.log("adios");
    return this.http.delete<Producto>(`${this.urlEndPoint}/${codigo}/${localStorage.getItem("noCT")}`, {headers: this.httpHeaders})
  }
}
