import { Component, OnInit } from '@angular/core';
import { Renta } from './renta';
import { RentaService } from './renta.service';
import swal from 'sweetalert2';
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
    this.rentaService.update(renta.id).subscribe(renta => 
      {
        console.log(renta);
        swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Se ha editado el usuario correctamente',
          showConfirmButton: false,
          timer: 3500
        })
        //Llamamos al método de redirección para volver a la lista de usuarios
        this.redirectrentaList();
      },
      error => console.log(error));
    
  }
 

  //Redirección a lista de usuarios
  redirectrentaList() {
    this.router.navigate(['/renta-admin']);
  }

}
