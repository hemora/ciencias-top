import { Component, OnInit } from '@angular/core';
import { HistorialService } from './historial.service';
import Swal from 'sweetalert2';
import { Renta } from '../rentas-usr/renta';

@Component({
  selector: 'app-historial',
  templateUrl: './historial.component.html',
  styleUrls: ['./historial.component.css']
})
export class HistorialComponent implements OnInit {

  rentas: Renta[];

  constructor(
    private historialService: HistorialService
  ) { }

  ngOnInit(): void {
    this.historial();
    //console.log(this.rentas)
  }

  public historial(): void {
    this.historialService.getHistorial().subscribe(
      rentas => this.rentas = rentas
    );
  }

}
