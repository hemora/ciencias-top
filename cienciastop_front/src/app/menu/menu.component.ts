import { Component, OnInit } from '@angular/core';
import { UserAuthService } from '../util/user-auth.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor(public authService: UserAuthService) { }

  ngOnInit(): void {
  }

  public autor = "Monica Miranda";

}
