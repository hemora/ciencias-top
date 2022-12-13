import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import Swal from 'sweetalert2';
import { Monedero } from './monedero';
import { Router } from '@angular/router';
import { MonederoService } from './monedero.service';
import {formatDate} from '@angular/common';
import { Usuario } from '../usuarios/usuario';

@Component({
  selector: 'app-editar-puma-puntos',
  templateUrl: './editar-puma-puntos.component.html',
  styleUrls: ['./editar-puma-puntos.component.css']
})
export class EditarPumaPuntosComponent implements OnInit {

  private monederoId: number = 317804520;
  //private monederoPeriodo: string = '2022-01';
  monedero: Monedero;
  usuario: Usuario;

  sumaRestaGroup!: FormGroup;

  constructor(private fb: FormBuilder, private monederoService: MonederoService,
    private router: Router) { }

  ngOnInit(): void {
    this.sumaRestaGroup = this.fb.group({
      defCantidad: [''],
      otraCantidad: ['',  Validators.min(0)],
      opcion: ['', Validators.required]
    });

    // ME pasas el usuario modificado
    this.usuario = history.state;

    //let periodoAux = (new Date()).toDateString().split(' ');
    let periodoAux = new Intl.DateTimeFormat('es-MX').format(new Date()).split('/');
    console.log(periodoAux);
    let periodo = periodoAux[2] + '-' + periodoAux[1];
    console.log(periodo)
    
    this.monederoService.getMonedero(this.usuario.noCT, periodo).subscribe(
      response => {
        this.monedero = response.monedero;
        console.log(this.monedero)
      }
    );
  }

  onSubmit() {
    if (this.sumaRestaGroup.valid) {
      console.log(this.sumaRestaGroup.value.defCantidad);

      console.log("aaaaa")

      // Se operan los datos del monedero
      let n = this.sumaRestaGroup.value.defCantidad as number;
      let m = this.sumaRestaGroup.value.otraCantidad as number;
      let updatedPP = 0;
      var monederoUpdate: Monedero = new Monedero();
      if (this.sumaRestaGroup.value.opcion == 'restar') {
        updatedPP = -1 * (Number(n) + Number(m)) ;
      } else {
        updatedPP = (Number(n) + Number(m));
      }
      //let periodo = new Date();
      let periodoAux = new Intl.DateTimeFormat('es-MX').format(new Date()).split('/');
      console.log(periodoAux)
      let periodo = periodoAux[2] + '-' + periodoAux[1];
      monederoUpdate.periodo = periodo;
      console.log(periodo)
      console.log(monederoUpdate);

      //const monederoUpdate = {
      //  'status': '',
      //  'pumaPuntos': '',
      //  'periodo': ''
      //}
      this.monederoService.sumarRestarPumaPuntos(this.monedero.ownerId, updatedPP).subscribe(
        response => {
          Swal.fire('Saldo Actualizado'
          , 'Saldo actual: ' + response.monedero.pumaPuntos
          , 'success');
          this.monedero.pumaPuntos = response.monedero.pumaPuntos;
          console.log(response);
        }
      );
      // Te vuelvo a pasar el estado
      //this.router.navigate(['/usuarios/editar'])
      this.router.navigateByUrl('/usuarios/editar', { state: this.usuario });
    }
  }
}
