import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navegacion',
  templateUrl: './navegacion.component.html',
  styleUrls: ['./navegacion.component.css']
})
export class NavegacionComponent implements OnInit {

  constructor(private route : Router) { }

  ngOnInit(): void {
  }

  busquedaProdAdmin(): void {
    this.route.navigate(['/prod-admin']);
  }

}
