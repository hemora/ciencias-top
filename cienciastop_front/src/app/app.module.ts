import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductosComponent } from './productos/productos.component';
import { BusquedaComponent } from './busqueda/busqueda.component';
import { NavegacionComponent } from './navegacion/navegacion.component';
import { VerProductosComponent } from './ver-productos/ver-productos.component';
import { RouterModule, Routes } from '@angular/router';
import {HttpClientModule} from '@angular/common/http';

const routes: Routes = [
  {path: '', redirectTo: '/productos', pathMatch: 'full'},
  {path: 'productos', component: ProductosComponent},
  {path: 'ver-productos/:codigo', component: VerProductosComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    ProductosComponent,
    BusquedaComponent,
    NavegacionComponent,
    VerProductosComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,    
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
