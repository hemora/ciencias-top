import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Producto } from '../productos/producto';
import { SrchUserProdService } from './srch-user-prod.service';

@Component({
  selector: 'app-srch-user-prod',
  templateUrl: './srch-user-prod.component.html',
  styleUrls: ['./srch-user-prod.component.css']
})
export class SrchUserProdComponent implements OnInit {

  productos: Producto[];
  porBuscar: string = "";

  constructor(
    private route:ActivatedRoute,
    private srchUserProdService: SrchUserProdService
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params: any) => {
      this.porBuscar = params.data;
    });
    console.log(this.porBuscar);
    this.srchUserProdService.getBuscado(this.porBuscar).subscribe(
      productos => this.productos = productos
    );
  }
}
