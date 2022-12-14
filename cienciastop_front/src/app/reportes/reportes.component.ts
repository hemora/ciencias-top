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
  groupCarrera: Object[];
  pMasBaratos: Producto[];
  groupStatus: Object[];
  conMasRentas: Object[];
  masRentados: Object[];
  masRetardos: Object[];

  constructor(
    private route: Router,
    private reportesService: ReportesService
    ) { }

  ngOnInit(): void {
    this.agrupamientoCarrera();
    this.topFiveBaratos();
    this.agrupamientoStatus();
    this.topFiveConMasRentas();
    this.topFiveMasRentados();
    this.topTenConMasRetardos();
  }

  public agrupamientoCarrera(): void {
    this.reportesService.agrupamientoCarrera().subscribe(
      groupCarrera => this.groupCarrera = groupCarrera
    );
  }

  public topFiveBaratos(): void {
    this.reportesService.topFiveBaratos().subscribe(
      pMasBaratos => this.pMasBaratos = pMasBaratos
    );
  }

  public agrupamientoStatus(): void {
    this.reportesService.agrupamientoStatus().subscribe(
      groupStatus => this.groupStatus = groupStatus
    );
  }

  public topFiveConMasRentas(): void {
    this.reportesService.topFiveConMasRentas().subscribe(
      conMasRentas => this.conMasRentas = conMasRentas
    );
  }

  public topFiveMasRentados(): void {
    this.reportesService.topFiveMasRentados().subscribe(
      masRentados => this.masRentados = masRentados
    );
  }

  public topTenConMasRetardos(): void {
    this.reportesService.topTenConMasRetardos().subscribe(
      masRetardos => this.masRetardos = masRetardos
    );
  }

}
