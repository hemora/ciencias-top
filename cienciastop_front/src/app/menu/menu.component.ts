import { Component, OnInit } from '@angular/core';
import { UserAuthService } from '../util/user-auth.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  public usuario: any

  constructor(public authService: UserAuthService) {
  }
  
  
  ngOnInit(): void {
    console.log("aaaaaaaaaaaa")
    console.log(this.usuario)
    this.usuario = this.authService.getNoCta()
  }

}
