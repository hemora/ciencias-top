import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { MenuComponent } from './menu/menu.component';
import { ProductosComponent } from './productos/productos.component';
import { BusquedaComponent } from './busqueda/busqueda.component';
import { VerProductosComponent } from './ver-productos/ver-productos.component';
import { RouterModule, Routes } from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { MenuAdminComponent } from './menu-admin/menu-admin.component';
import { MenuProveComponent } from './menu-prove/menu-prove.component';
import { SortDirective } from './directive/usuarios.directive';
import { EditarUsrComponent } from './editar/editar-usr.component';
import { EditarPumaPuntosComponent } from './editar-puma-puntos/editar-puma-puntos.component';
import { AgregarUsrComponent } from './agregar-usr/agregar-usr.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SrchAdminProdComponent } from './srch-admin-prod/srch-admin-prod.component';
import { SrchUserProdComponent } from './srch-user-prod/srch-user-prod.component';
import { AgregaPrdComponent } from './agrega-prd/agrega-prd.component';
import { RentaAdminComponent } from './renta-admin/renta-admin.component';
import { RentasUsrComponent } from './rentas-usr/rentas-usr.component';
import { ReportesComponent } from './reportes/reportes.component';
import { EditarProdComponent } from './editar-prod/editar-prod.component';
import { HistorialComponent } from './historial/historial.component';
import { InicioUiComponent } from './inicio-ui/inicio-ui.component';
import { ReestablecerContraseniaComponent } from './reestablecer-contrasenia/reestablecer-contrasenia.component';
import { PerfilComponent } from './perfil/perfil.component';
import { PerfilAdminComponent } from './perfil/perfil-admin.component';

const routes: Routes = [
  {path: "", redirectTo: "/productos", pathMatch: "full"},
  {path: "usuarios", component: UsuariosComponent},
  {path: "productos", component: ProductosComponent},
  {path: "usuarios/agregar-usr", component: AgregarUsrComponent},
  {path: "productos/agrega-prd", component: AgregaPrdComponent},
  {path: "productos/srch-admin-prod", component: SrchAdminProdComponent},
  {path: "productos/srch-user-prod", component: SrchUserProdComponent},
  {path: "usuarios/editar", component: EditarUsrComponent},
  {path: "usuarios/pumapuntos", component: EditarPumaPuntosComponent},
  {path: 'ver-productos/:codigo', component: VerProductosComponent },
  {path: 'renta-admin', component: RentaAdminComponent },
  {path: 'rentas-usr/:id', component: RentasUsrComponent},
  {path: 'reportes', component: ReportesComponent},
  {path: 'productos/editar-prod', component: EditarProdComponent},
  {path: 'productos/editar-prod/:codigo', component: EditarProdComponent},
  {path: 'productos/historial', component: HistorialComponent},
  {path: 'login', component: InicioUiComponent},
  {path: 'usuarios/reestablecer-contrasenia', component: ReestablecerContraseniaComponent},
  {path: 'ver-perfil/:noCT', component: PerfilComponent},  
  {path: 'usuarios/ver-perfil/:noCT', component: PerfilAdminComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    ProductosComponent,
    BusquedaComponent,    
    VerProductosComponent,
    HeaderComponent,
    MenuComponent,
    UsuariosComponent,
    MenuAdminComponent,
    MenuProveComponent,
    SortDirective,
    EditarUsrComponent,
    EditarPumaPuntosComponent,
    AgregarUsrComponent,
    SrchAdminProdComponent,
    SrchUserProdComponent,
    AgregaPrdComponent,
    RentaAdminComponent,
    RentasUsrComponent,
    ReportesComponent,
    EditarProdComponent,
    HistorialComponent,
    InicioUiComponent,
    ReestablecerContraseniaComponent,
    PerfilComponent,
    PerfilAdminComponent
  ],
  
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,    
    RouterModule.forRoot(routes),
    ReactiveFormsModule,
    CommonModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
