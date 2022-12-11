import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Monedero } from '../editar-puma-puntos/monedero';
import { MonederoService } from '../editar-puma-puntos/monedero.service';
import { Renta } from '../renta-admin/renta';
import { RentaService } from '../renta-admin/renta.service';
import { ProductoService } from '../productos/producto.service';
import { Usuario } from '../usuarios/usuario';
import { Producto } from '../productos/producto';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {

  usuario: Usuario = new Usuario();    
  monedero: Monedero = new Monedero();
  
  map_rentas = new Map();
  rentas = new Array<Renta> ();
  devoluciones = new Array<Renta> ();

  mes = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
  "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
  ];

  myDate = new Date();  
  month = this.mes[this.myDate.getUTCMonth()];


  constructor(private monederoService: MonederoService, private rentaService: RentaService, 
    private productoService: ProductoService) { }  

  ngOnInit(): void {    
    this.usuario = history.state;
    console.log(this.usuario);      
    
    let periodoAux = new Intl.DateTimeFormat('es-MX').format(new Date()).split('/');    
    let periodo = periodoAux[2] + '-' + periodoAux[1];
    
    this.monederoService.getMonedero(this.usuario.noCT, periodo).subscribe(
      response => {                
        this.monedero = JSON.parse(response.monedero);      
      }
    );    

    this.rentaService.getRentasUsr(this.usuario.noCT).subscribe(
      response => {
        this.map_rentas = new Map(Object.entries(response));
        console.log(this.map_rentas);        
        this.rentas = this.map_rentas.get("rentasActuales");        
        this.devoluciones = this.map_rentas.get("rentasVencidas");        
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
  esMalo(fecha_entrega:Date) {    
    var fecha_renta = fecha_entrega.toString().split("/").reverse().join("/");  
    var current = this.myDate.toString().split("/").reverse().join("/");
    var fechadesde = new Date(fecha_renta).getTime();
    var fechahasta = new Date(current).getTime();    
    var dias = fechahasta - fechadesde;
    var diff = dias/(1000 * 60 * 60 * 24);
    return diff>7;
  }  
  
  getDateFormat(date:Date) {
    return (date.toString().split("T")[0]).split("-").reverse().join("/");
  }

}
