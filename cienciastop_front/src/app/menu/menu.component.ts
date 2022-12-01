import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  // usuario provicional - reemplazar con datos del usuario logeado
  usuario = {nombre: 'Daniel', apellidos:'Ruelas Milanés', noCT:317804520, telefono:5455111666, correo:'sango265@ciencias.unam.mx', carrera:'Ciencias de la Computación', rol:' Administrador', contrasenya:'12345678', status:1}

  constructor() { }

  ngOnInit(): void {
  }

}
