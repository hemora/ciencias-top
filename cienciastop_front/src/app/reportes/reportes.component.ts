import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Producto } from '../productos/producto';
import { ReportesService } from './reportes.service';

@Component({
  selector: 'app-reportes',
  templateUrl: './reportes.component.html',
  styleUrls: ['./reportes.component.css']
})
export class ReportesComponent implements OnInit {

  control = new FormControl();
  agrupamiento: Object[];
  pMasBaratos: Producto[];

  constructor(
    private route: Router,
    private reportesService: ReportesService
    ) { }

  ngOnInit(): void {
    this.agrupamientoCarrera();
    this.topFiveBaratos();
  }

  public agrupamientoCarrera(): void {
    this.reportesService.agrupamientoCarrera().subscribe(
      agrupamiento => this.agrupamiento = agrupamiento
    );
  }

  public topFiveBaratos(): void {
    this.reportesService.topFiveBaratos().subscribe(
      pMasBaratos => this.pMasBaratos = pMasBaratos
    );
  }

}
