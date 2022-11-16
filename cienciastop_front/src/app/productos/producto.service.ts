import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Producto } from './producto';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  
  constructor(private http: HttpClient) { }

  private urlEndPoint: string = 'http://localhost:8080/api/productos';

  /**
   * Realiza una consulta de todos los productos en la base de datos
   * y los regresa en un arreglo.
   * @returns El arreglo con todos los productos en la base de datos.
   */
  getProductos(): Observable<Producto[]>{
    return this.http.get<Producto[]>(this.urlEndPoint);
  }

  /**
   * Realiza una consulta de todos los productos que contengan la 
   * cadena ingresada en su nombre o en su código.
   * @param entrada La cadena a buscar.
   * @returns Un arreglo con los productos que contengana la entrada
   * en su nombre o código.
   */
  getProducto(entrada:string): Observable<Producto[]>{
    return this.http.get<Producto[]>('http://localhost:8080/api/busqueda?entrada=' + entrada)
  }

}
