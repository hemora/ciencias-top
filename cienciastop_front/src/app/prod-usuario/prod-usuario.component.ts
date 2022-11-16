import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProdUsuarioService } from './prod-usuario.service';
import { Producto } from '../productos/producto';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-prod-usuario',
  templateUrl: './prod-usuario.component.html',
  styleUrls: ['./prod-usuario.component.css']
})
export class ProdUsuarioComponent implements OnInit {

  constructor(private route:ActivatedRoute, private prodUsuarioService: ProdUsuarioService) { }

  productos: Producto[];
  porBuscar: string = "";
  
  ngOnInit(): void {
    this.route.queryParams.subscribe((params: any) => {
      // console.log(params);
      this.porBuscar = params.data;
      // console.log(this.porBuscar);
    });
    this.prodUsuarioService.getBuscado().subscribe(
      productos => this.productos = productos
    );
    for (let index = 0; index < this.productos.length; index++) {
      const element = this.productos[index];
      console.log(element.codigo);
      
    }
  }
}
