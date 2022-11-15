import { Component, OnInit, Input } from '@angular/core';
import { Producto } from '../productos/producto';

@Component({
  selector: 'app-ver-productos',
  templateUrl: './ver-productos.component.html',
  styleUrls: ['./ver-productos.component.css'],
  template: `
    Message:
    <app-ver-productos (messageEvent)="receiveMessage($event)"></app-ver-productos>`
})
export class VerProductosComponent implements OnInit {

  constructor() { }

  producto:Producto = new Producto;
  
  ngOnInit(): void {
  }

  receiveMessage($event){
    this.producto = $event;
  }
}
