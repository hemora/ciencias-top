import { Component, OnInit } from '@angular/core';
import { HistorialService } from './historial.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-historial',
  templateUrl: './historial.component.html',
  styleUrls: ['./historial.component.css']
})
export class HistorialComponent implements OnInit {

  entrada: string = "123456789"
  // rentas: Renta[];

  constructor(
    private historialService: HistorialService
  ) { }

  ngOnInit(): void {
    // this.historial();
  }

  /*
  public historial(): void {
    this.historialService.getHistorial(this.entrada).subscribe(
      rentas => this.rentas = rentas
    );
  }
  */

}
