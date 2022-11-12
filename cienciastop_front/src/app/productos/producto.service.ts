import { Injectable } from '@angular/core';
import { Producto } from './producto';
import { Observable } from 'rxjs';
import { of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private urlEndPoint:string = 'http://localhost:8080/api/productos';

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'})

  constructor(private http: HttpClient) { }


  getProductos(): Observable<Producto[]>{
    return this.http.get<Producto[]>(this.urlEndPoint);
  }

  create(producto: Producto): Observable<Producto>{
    return this.http.post<Producto>(this.urlEndPoint, producto, {headers: this.httpHeaders} )
  }

  getProducto(codigo: string): Observable<Producto>{
    return this.http.get<Producto>(`${this.urlEndPoint}/${codigo}`)
  }

  update(producto: Producto): Observable<Producto>{
    return this.http.put<Producto>(`${this.urlEndPoint}/${producto.codigo}`, producto, {headers: this.httpHeaders})
  }

  delete(codigo: string): Observable<Producto>{
    return this.http.delete<Producto>(`${this.urlEndPoint}/${codigo}`, {headers: this.httpHeaders})
  }
}
