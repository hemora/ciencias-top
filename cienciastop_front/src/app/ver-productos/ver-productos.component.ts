import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Producto } from '../productos/producto';
import { ProductoService } from '../productos/producto.service';
import { catchError } from 'rxjs';

@Component({
  selector: 'app-ver-productos',
  templateUrl: './ver-productos.component.html',
  styleUrls: ['./ver-productos.component.css']
})
export class VerProductosComponent implements OnInit {

  producto: Producto = new Producto;

  constructor(private productoService: ProductoService, private rutaActiva: ActivatedRoute) { }
  
  ngOnInit(): void {
    this.productoService.getProducto(this.rutaActiva.snapshot.params['codigo']).subscribe(
      busqueda => this.producto = busqueda[0]
    );
  }

}
