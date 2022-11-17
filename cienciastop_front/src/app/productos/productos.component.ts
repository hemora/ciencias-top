import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { Producto } from './producto';
import { ProductoService } from './producto.service';

@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css'],
})
export class ProductosComponent implements OnInit {
  
  productos: Producto[];

  producto1: any;

  producto2: any;

  producto3: any;

  constructor(private productoService: ProductoService) { }
  ngOnInit(): void {
    this.productoService.getProductos().subscribe(
      productos => {
        this.productos = productos
        this.producto1 = productos[0]
        this.producto2 = productos[1]
        this.producto3 = productos[2]
      }
    );
  }

}
