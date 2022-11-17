import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Producto } from '../productos/producto';
import { SrchUserProdService } from './srch-user-prod.service';

@Component({
  selector: 'app-srch-user-prod',
  templateUrl: './srch-user-prod.component.html',
  styleUrls: ['./srch-user-prod.component.css']
})
export class SrchUserProdComponent implements OnInit {

  productos: Producto[];
  porBuscar: string = "";

  constructor(
    private route: ActivatedRoute,
    private srchUserProdService: SrchUserProdService,
    private router: Router
  ) { }

  ngOnInit(): void {
    //this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.route.queryParams.subscribe((params: any) => {
      this.porBuscar = params.data;
    });
    console.log("recibi " + this.porBuscar)
    this.srchUserProdService.getBuscado(this.porBuscar).subscribe(
      productos => this.productos = productos
    );
  }
  // https://medium.com/angular-in-depth/refresh-current-route-in-angular-512a19d58f6e
  // https://www.auroria.io/angular-route-reuse-strategy/
  // https://www.geekstrick.com/tech-talks/how-to-reload-refresh-the-data-when-navigate-to-same-route-in-angular-9/
  // https://medium.com/@rajeshpillai1996/what-is-routereusestrategy-how-to-cache-components-with-angular-routereusestrategy-82da7790cd2b
  // https://stackoverflow.com/questions/53592940/how-to-refresh-data-with-angular
}
