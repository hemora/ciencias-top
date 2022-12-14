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

  public devolver(renta: Renta): void {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })
    
    swalWithBootstrapButtons.fire({
      title: 'Estas seguro?',
      text: `¿Seguro que desea devolver el producto ${renta.producto.nombre}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Devolver',
      cancelButtonText: 'Cancelar',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.rentaService.delete(renta.id).subscribe(
          Response => {
            this.rentas = this.rentas.filter(rent => rent !== renta)
            Swal.fire({
              position: 'center',
              icon: 'success',
              title: 'Se ha devuelto el producto',
              showConfirmButton: false,
              timer: 3500
            })
          }
        )
      }
    })
  }
 

 

}
