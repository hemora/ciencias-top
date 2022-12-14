import { Injectable } from '@angular/core';
import { Producto } from './producto';
//import { PRODUCTOS } from './productos.json';
import { of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import Swal from 'sweetalert2';
import { Router } from '@angular/router'; // Para realizar el redireccionamiento
import { UserAuthService } from '../util/user-auth.service';


@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  miStorage = window.localStorage;

  constructor(
    private http: HttpClient, 
    private router:Router,
    private userAuthService: UserAuthService) { }

  public noCT: number = this.userAuthService.getNoCta();
  public rol: string = this.userAuthService.getRol();

  private urlEndPoint:string = 'http://localhost:8080/api/productos';
  public httpHeaders = new HttpHeaders()
      .set('Authorization',  `Bearer ${this.userAuthService.getToken()}`)
      .set('Content-Type',  'application/json')

  public authHeader = {
    headers: new HttpHeaders()
      .set('Authorization',  `Bearer ${this.userAuthService.getToken()}`)
  }

  getProductos(): Observable<Producto[]>{
    return this.http.get<Producto[]>(this.urlEndPoint, this.authHeader).pipe(
      catchError(e => {
        Swal.fire('Error al cargar los productos', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    );
  }

  getProductosFiltro(): Observable<Producto[]>{
    var aux = 'http://localhost:8080/api/productos-filtro ';
    return this.http.get<Producto[]>(aux, this.authHeader).pipe(
      catchError(e => {
        Swal.fire('Error al cargar los productos', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    );
  }

  create(producto: Producto): Observable<Producto>{
    return this.http.post<Producto>(`${this.urlEndPoint}/${this.noCT}`, producto,  this.authHeader).pipe(
      catchError(e => {
        this.router.navigate(['/productos']);
        Swal.fire('El código ya esta asociado a un producto existente.', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }

  getProducto(codigo:string): Observable<Producto>{
    return this.http.get<Producto>(`${this.urlEndPoint}/${codigo}`,this.authHeader).pipe(
      catchError(e => {
        this.router.navigate(['/productos']); // Para redireccionar a productos
        Swal.fire('Error al encontrar el prod', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    ) 
  }

  delete(codigo: string): Observable<Producto>{
    return this.http.delete<Producto>(`${this.urlEndPoint}/${codigo}/${this.noCT}`, this.authHeader).pipe(
      catchError(e => {
        this.router.navigate(['/productos']);
        Swal.fire('No ha sido posible eliminar el producto.', e.error.mensaje, 'error');
        return throwError( () => e);
      })
    )
  }

  /**
   * El noCT que sigue despues de /editar/ debe representar el noCT que tiene los permisos necesarios
   * para poder editarlo, que sea la persona que lo agrego o que sea admin
   * @param prod Producto que guardaremos
   */
  editarProd(prod:Producto): Observable<any>{
    console.log(localStorage.getItem("noCT"));
    //  En este caso simulando el noCT del que esta editando este producto
    // Colocando el noCT del usuario que agrego este producto y es el que esta logeado(conectado)
    //return this.http.put<Producto>(`${this.urlEndPoint}/${prod.codigo}/editar/${localStorage.getItem("noCT")}`, {headers: this.httpHeaders}).pipe(
    return this.http.put<any>(`${this.urlEndPoint}/${prod.codigo}/editar/${this.noCT}/${this.rol}`, prod,this.authHeader).pipe( // 153249375 para poder editar dead of winter
    //return this.http.put<any>(`${this.urlEndPoint}/${prod.codigo}/editar/153249375`, prod,this.authHeader).pipe( // 153249375 para poder editar dead of winter
      catchError( e => {
        Swal.fire(e.error.mensaje,  e.error.error, 'error');
        return throwError( () => e );
      })
    )
  }

}
