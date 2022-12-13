import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Monedero } from '../editar-puma-puntos/monedero';
import { MonederoService } from '../editar-puma-puntos/monedero.service';
import { UserAuthService } from '../util/user-auth.service';

@Injectable({
    providedIn: 'root'
})
export class HeaderService {

    noCT: number; 
    map_data = new Map();
    monedero : number;

    constructor( 
        public authService: UserAuthService,         
        private monederoService: MonederoService) { }    
    
    setPumaPts() {
        this.noCT = this.authService.getNoCta();        
        let periodoAux = new Intl.DateTimeFormat('es-MX').format(new Date()).split('/');
        let periodo = periodoAux[2] + '-' + periodoAux[1];

        this.monederoService.getMonedero(this.noCT, periodo).subscribe(
            response => {
              this.map_data = new Map(Object.entries(response));
              this.monedero = this.map_data.get("monedero").pumaPuntos;                   
        });    
    }
}