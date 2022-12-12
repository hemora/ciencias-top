import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Monedero } from '../editar-puma-puntos/monedero';
import { MonederoService } from '../editar-puma-puntos/monedero.service';
import { Renta } from '../renta-admin/renta';
import { ProductoService } from '../productos/producto.service';
import { Usuario } from '../usuarios/usuario';
import { UsuarioService } from '../usuarios/usuario.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {

  noCT: number;
  /* Diccionario de datos del usuario */
  map_data = new Map();
  /* Usuario de quien queremos el perfil */
  usuario: Usuario = new Usuario();
  /* Monedero actual */
  monedero: Monedero = new Monedero();
  /* Información de rentas activas y por devolver */
  rentas = new Array<Renta>();
  devoluciones = new Array<Renta>();

  mes = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
  ];

  myDate = new Date();
  month = this.mes[this.myDate.getUTCMonth()];


  constructor(private router: ActivatedRoute, private monederoService: MonederoService, private usuarioService: UsuarioService,
    private productoService: ProductoService) { }

  ngOnInit(): void {
    this.router.params.subscribe(params => {
      this.noCT = params['noCT'];
    });
    console.log(this.noCT);
    this.setPerfilData(this.noCT);
  }

  setPerfilData(noCT: number) {
    this.usuarioService.getPerfilUsr(noCT).subscribe(
      response => {
        this.map_data = new Map(Object.entries(response));
        console.log(this.map_data);        
        this.warning(this.map_data);
        this.usuario = this.map_data.get("dataUsuario");
        this.rentas = this.map_data.get("rentasActuales");
        this.devoluciones = this.map_data.get("rentasVencidas");
      }
    );    
    this.setMonedero(noCT);
  }

  warning(map_data: Map<any, any>) {
    let msg = map_data.get("mensaje");
    if (msg != null) {
      Swal.fire(
        {
          title: 'No hay rentas activas que mostrar',
          text: msg,
          icon: 'warning'
        }
      );
    }
  }

  setMonedero(noCT: number) {
    let periodoAux = new Intl.DateTimeFormat('es-MX').format(new Date()).split('/');
    let periodo = periodoAux[2] + '-' + periodoAux[1];

    this.monederoService.getMonedero(noCT, periodo).subscribe(
      response => {
        this.monedero = response.monedero;
      }
    );
  }

  /**
   * Funcion para identificar si han pasado mas de 7 dias
   * desde la fecha de devolucion de un producto.
   * @param renta fecha en que se rento el producto.
   * @returns true si han pasado mas de 7 dias,
   *          false en otro caso.
   */
  esMalo(fecha_entrega: Date) {
    var fecha_renta = fecha_entrega.toString().split("/").reverse().join("/");
    var current = this.myDate.toString().split("/").reverse().join("/");
    var fechadesde = new Date(fecha_renta).getTime();
    var fechahasta = new Date(current).getTime();
    var dias = fechahasta - fechadesde;
    var diff = dias / (1000 * 60 * 60 * 24);
    return diff > 7;
  }

  getDateFormat(date: Date) {
    return (date.toString().split("T")[0]).split("-").reverse().join("/");
  }

}


