import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductosComponent } from './productos/productos.component';
import { BusquedaComponent } from './busqueda/busqueda.component';
import { NavegacionComponent } from './navegacion/navegacion.component';
import { VerProductosComponent } from './ver-productos/ver-productos.component';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProdUsuarioComponent } from './prod-usuario/prod-usuario.component';
import { ProdAdminComponent } from './prod-admin/prod-admin.component';

const routes: Routes = [
  {path: '', redirectTo: '/productos', pathMatch: 'full'},
  {path: 'productos', component: ProductosComponent},
  {path: 'prod-usuario', component: ProdUsuarioComponent},
  {path: 'prod-admin', component: ProdAdminComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    ProductosComponent,
    BusquedaComponent,
    NavegacionComponent,
    VerProductosComponent,
    ProdAdminComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,    
    RouterModule.forRoot(routes),
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }