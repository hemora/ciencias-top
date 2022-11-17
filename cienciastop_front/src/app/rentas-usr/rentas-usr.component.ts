import { Component, OnInit } from '@angular/core';
import { RentaService } from './renta.service';
import swal from 'sweetalert2';
import { Renta } from './renta';
import { Router } from '@angular/router';

@Component({
  selector: 'app-rentas-usr',
  templateUrl: './rentas-usr.component.html',
  styleUrls: ['./rentas-usr.component.css']
})
export class RentasUsrComponent implements OnInit {

  renta: Renta = new Renta();
  constructor(private rentaService: RentaService, private router: Router) { }

  ngOnInit(): void {
  }

  

}
