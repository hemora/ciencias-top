import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Producto } from '../productos/producto';
import { ProductoService } from '../productos/producto.service';
import { catchError } from 'rxjs';
import { SrchUserProdService } from '../srch-user-prod/srch-user-prod.service';
import { Usuario } from '../usuarios/usuario';
import Swal from 'sweetalert2';
import { RentaService } from '../rentas-usr/renta.service';
import { UserAuthService } from '../util/user-auth.service';

@Component({
  selector: 'app-ver-productos',
  templateUrl: './ver-productos.component.html',
  styleUrls: ['./ver-productos.component.css']
})
export class VerProductosComponent implements OnInit {

  producto: any;
  usuario: Usuario = new Usuario;
  miStorage = window.localStorage;
  public noCT: number = this.userAuthService.getNoCta();

  constructor(private productoService: ProductoService, 
    private rentaService: RentaService,
    private rutaActiva: ActivatedRoute, 
    private router: Router,
    private userAuthService: UserAuthService) { }
  
  ngOnInit(): void {
    this.productoService.getProducto(this.rutaActiva.snapshot.params['codigo']).subscribe(
      busqueda => this.producto = busqueda
    );
  }
  public rentar(){
    console.log(this.producto.codigo);
    console.log(this.noCT);
    this.rentaService.rentarProducto(this.producto.codigo,this.noCT).subscribe(renta =>
      {
        this.router.navigate(['/rentas-usr'])
        Swal.fire('Nueva Renta', `Renta ${this.producto.codigo} creado con éxito`, 'success')
      }
    )
  }

}
