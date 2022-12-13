import { Component, OnInit } from '@angular/core';
import { RentaService } from './renta.service';
import swal from 'sweetalert2';
import { Renta } from './renta';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-rentas-usr',
  templateUrl: './rentas-usr.component.html',
  styleUrls: ['./rentas-usr.component.css']
})
export class RentasUsrComponent implements OnInit {

  renta: any;
  constructor(private rentaService: RentaService, 
    private rutaActiva: ActivatedRoute, 
    private router: Router) { }

  ngOnInit(): void {
    this.rentaService.verRenta(this.rutaActiva.snapshot.params['id']).subscribe(
      busqueda => this.renta = busqueda
    );
  }

  rentado() {
    if (this.renta.status_entrega) {
      return 'Entregado';
    }
    return 'Sin entregar';
  }


  

}
