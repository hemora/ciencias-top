import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { Usuario } from './usuario';
import { UsuarioService } from './usuario.service';
import { USUARIOS } from './usuarios.json';

@Component({
  selector: 'app-usr-srch',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css']
})
export class UsrSrchComponent implements OnInit {

  usuarios: Usuario[];
  busqueda: String;

  constructor(private router : Router, private activeRouter : ActivatedRoute, private usuarioService: UsuarioService) { }

  ngOnInit(): void {
    this.activeRouter.params.subscribe(params => {
      this.busqueda = params['busqueda'];
    });
    this.commitbusqueda();
  }

  commitbusqueda() {            
    this.usuarioService.busquedaAuxiliar(this.busqueda).subscribe(
      usuarioData => {
        console.log(usuarioData);
        this.usuarios= [usuarioData].flat();
      },
        error => console.log(error));
  }

  onSubmitForm() {    
    this.commitbusqueda();    
    this.router.navigate(['/usuarios/srch/' + this.busqueda]);    
  }

  delete(usuario: Usuario): void {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: true
    })
    
    swalWithBootstrapButtons.fire({
      title: '¿Está seguro de que desea eliminar?',
      text: `Una vez realizada esta acción se inhabilitara al usuario ${usuario.nombre} de la base de datos`,
      showCancelButton: true,
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.usuarioService.eliminar(usuario.noCT).subscribe(
          Response => {
            this.usuarios =  this.usuarios.filter(usr => usr !== usuario)
            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Se ha eliminado un usuario',
              showConfirmButton: false,
              timer: 3500
            })
            this.usuarioService.desactivarMonedero(usuario.noCT).subscribe(
              response => {
                console.log(response)
              }
            )
          }
        )

      }
    })
    
  }
}
