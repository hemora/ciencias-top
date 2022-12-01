import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Monedero } from '../editar-puma-puntos/monedero';
import { MonederoService } from '../editar-puma-puntos/monedero.service';
import { Renta } from '../renta-admin/renta';
import { RentaService } from '../rentas-usr/renta.service';
import { Usuario } from '../usuarios/usuario';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {

  usuario: Usuario = new Usuario();    
  monedero: Monedero = new Monedero();
  /* 
   Datos provicionales
   Sustituir con las rentas del periodo actual y 
   devoluciones (status-false) dado un usuario.
  */
  rentas = [
    {nombre: 'Balón de Voleibol', codigo:'Ss045', cantidad:1, imagen:''},
    {nombre: 'Replicas', codigo:'Vs816', cantidad:1, imagen:''},
    {nombre: 'Audifonos', codigo:'Ts200', cantidad:2, imagen:''},
    {nombre: 'Producto', codigo:'Ps500', cantidad:1, imagen:''},
    {nombre: 'Producto', codigo:'Ps500', cantidad:1, imagen:''},
    {nombre: 'Producto', codigo:'Ps500', cantidad:1, imagen:''}
  ];
  devoluciones = [
    {nombre: 'UNO', codigo:'Gs001', cantidad:2, renta:'28/11/2022'},
    {nombre: 'Un Verano Sin Ti', codigo:'Ms200', cantidad:1, renta:'10/11/2022'},
    {nombre: 'Set de Pin-Pong', codigo:'Ss120', cantidad:1, renta:'11/11/2022'}
  ];

  mes = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
  "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
  ];

  myDate = new Date();  
  month = this.mes[this.myDate.getUTCMonth()];


  constructor(private monederoService: MonederoService, private rentasService: RentaService) { }  

  ngOnInit(): void {
    this.usuario = history.state;
    console.log(this.usuario);      
    
    let periodoAux = new Intl.DateTimeFormat('es-MX').format(new Date()).split('/');    
    let periodo = periodoAux[2] + '-' + periodoAux[1];
    
    this.monederoService.getMonedero(this.usuario.noCT, periodo).subscribe(
      response => {
        this.monedero = response.monedero;
      }
    );    

    /*this.rentasService.getRentas(this.usuario.noCT).subscribe(
      response => {
        this.rentas = response.rentas;
      }
      this.rentasService.getDevoluciones(this.usuario.noCT, status false).subscribe(
      response => {
        this.devoluciones = response.devoluciones;
      }
    );*/
  }

  /**
   * Funcion para identificar si han pasado mas de 7 dias
   * desde la fecha de devolucion de un producto.
   * @param renta fecha en que se rento el producto.
   * @returns true si han pasado mas de 7 dias,
   *          false en otro caso.
   */
  esMalo(renta:string) {
    var fecha_renta = renta.split("/").reverse().join("/");    
    var current = this.myDate.toString().split("/").reverse().join("/");
    var fechadesde = new Date(fecha_renta).getTime();
    var fechahasta = new Date(current).getTime();    
    var dias = fechahasta - fechadesde;
    var diff = dias/(1000 * 60 * 60 * 24);
    return diff>7;
  }  

}
