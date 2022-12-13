import { Component, OnInit } from '@angular/core';
import { Renta } from './renta';
import { RentaService } from './renta.service';
import Swal from 'sweetalert2';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-renta-admin',
  templateUrl: './renta-admin.component.html',
  styleUrls: ['./renta-admin.component.css']
})
export class RentaAdminComponent implements OnInit {

  rentas: Renta[];

  constructor(private rentaService: RentaService, private router: Router) { }

  ngOnInit(): void {
    this.rentaService.getRentas().subscribe(
      rentas => this.rentas = rentas
    );
  }
  public update(renta: Renta):void{
    this.rentaService.update(renta.id).subscribe(renta => {
      console.log(renta);
      // this.router.navigate(['/rentas-admin/'])
      Swal.fire('Renta Actualizada', `La renta ${renta.id} se ha actualizado correctamente`, 'success')
    }
    )
    
  }
 

 

}
