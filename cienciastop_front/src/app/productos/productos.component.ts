import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Producto } from './producto';
import { ProductoService } from './producto.service';

@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css'],
})
export class ProductosComponent implements OnInit {
  
  productos: Producto[];

  producto:Producto = new Producto;

  @Output() messageEvent = new EventEmitter<Producto>();

  constructor(private productoService: ProductoService) { }

  ngOnInit(): void {
    this.productoService.getProductos().subscribe(
      productos => this.productos = productos
    );
  }

  sendMessage(producto:Producto){
    this.producto = producto;
    this.messageEvent.emit(this.producto);
  }

}
