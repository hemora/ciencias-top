import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserAuthService {

  constructor() { }

  setRole(rol: string) {
    localStorage.setItem("currentRol", rol);
  }

  getRol(): string {
    return localStorage.getItem("currentRol");
  }

  setToken(jwtToken: string) {
    localStorage.setItem("jwtToken", jwtToken);
  }

  getToken(): string {
    return localStorage.getItem("jwtToken");
  }

  clear() {
    localStorage.clear();
  }

  isLoggedIn() {
    return this.getRol() && this.getToken();
  }

  getNoCta(): number {
    return Number(localStorage.getItem("noCt"));
  }

  setNoCta(noCt: string) {
    localStorage.setItem("noCt", noCt);
  }

}
