import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { Producto } from '../productos/producto';
import { ProductoService } from '../productos/producto.service';

@Component({
  selector: 'app-editar-prod',
  templateUrl: './editar-prod.component.html',
  styleUrls: ['./editar-prod.component.css']
})
export class EditarProdComponent implements OnInit {

  titulo: string = "Editar Producto"
  //Cramos un nuevo producto vacío
  producto: Producto = new Producto();
  angForm: FormGroup;
  miStorage = window.localStorage;
  
  constructor(
    private productoService: ProductoService, 
    private router: Router, 
    private activateRoute: ActivatedRoute, 
    private fb: FormBuilder) {
    this.createForm();
  }

  ngOnInit(): void {
    //this.prod = history.state;
    //console.log(this.prod);
    this.cargarProducto()
  }

  cargarProducto(): void{
    this.activateRoute.params.subscribe(params => {
      let codigo = params['codigo']
      if(codigo){
        this.productoService.getProducto(codigo).subscribe((producto)=>this.producto = producto)
      }
    })
  }

  // Metodo que crea el formulario

  createForm() {
    this.angForm = this.fb.group({
      nombre: new FormControl('', [Validators.required] ),
      stockInicial: new FormControl('', [Validators.required] ),
      precio: new FormControl('', [Validators.required] ),
      descripcion: new FormControl('', [Validators.required] ),
      tipo: new FormControl('', [Validators.required] ),
      categoria: new FormControl('', [Validators.required] ),
      periodoRenta: new FormControl('', [Validators.required] ),
      imagen: new FormControl('', [Validators.required])
      //periodoRenta: new FormControl({ periodoRenta: this.producto.periodo_renta }, Validators.compose([Validators.required])),
      //imagen: new FormControl({ imagen: this.producto.imagen }, Validators.compose([Validators.required]))
    });
  }
  //createForm() {
  //  this.angForm = new FormGroup({
  //    nombre: new FormControl({nombre:this.prod.nombre}, [Validators.required] ),
  //    codigoP: new FormControl({ codigo: this.prod.codigo }, [Validators.required, Validators.minLength(12), Validators.maxLength(12)] ),
  //    stockInicial: new FormControl(10, [Validators.required] ),
  //    precio: new FormControl('', [Validators.required] ),
  //    descripcion: new FormControl('', [Validators.required] ),
  //    tipo: new FormControl('', [Validators.required] ),
  //    categoria: new FormControl('', [Validators.required] ),
  //    periodoRenta: new FormControl('', [Validators.required] ),
  //    imagen: new FormControl('', [Validators.required]),
  //  });
  //}

  //Este método es llamado desde el formulario
  //Se encarga de disparar el método de guardado de productos
  onSubmitForm() {
    if (this.angForm.valid) {
      console.log(this.producto);
      console.log("lo que se envia");
      this.commitProd();
    } else {
      Swal.fire('Error al editar un prod', 'El form está incompleto o es incorrecto, intenta de nuevo.', 'error');
    }
  }
  //Este método llama al editarProd de usuarioService.
  commitProd() {
    this.productoService.editarProd(this.producto).subscribe(
      prodData => {
        console.log(prodData);
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Se ha editado el prod correctamente',
          showConfirmButton: false,
          timer: 3500
        })
        //Llamamos al método de redirección para volver a la lista de usuarios
        this.router.navigate(['/productos']);
      },
      error => console.log(error));
  }

}
