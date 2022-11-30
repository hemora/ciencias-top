import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms'; //agregue formsModule
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

  /**
   * Metodo que usamos para cargar el producto correspondiente
   * al codigo proporcionado como parametro cuando seleccionamos
   * el producto en la tabla.
   */
  cargarProducto(): void{
    this.activateRoute.params.subscribe(params => {
      // Codigo del producto que buscaremos cargar 
      let codigo = params['codigo']
      if(codigo){
        this.productoService.getProducto(codigo).subscribe((producto) => this.producto = producto)
      }
    })
  }

  ngOnInit(): void {
    this.cargarProducto()
  }
  
  /**
   * Metodo que usamos para crear el formulario, 
   * ignoramos el codigo y el noCT porque estos no se pueden editar
   */
  createForm() {
    this.angForm = this.fb.group({
      nombre: new FormControl('', [Validators.required] ),
      //codigoP: new FormControl('', [Validators.required, Validators.minLength(12), Validators.maxLength(12)] ),
      stockInicial: new FormControl('', [Validators.required] ),
      currentStock: new FormControl('', [Validators.required] ),
      precio: new FormControl('', [Validators.required] ),
      descripcion: new FormControl('', [Validators.required] ),
      tipo: new FormControl('', [Validators.required] ),
      categoria: new FormControl('', [Validators.required] ),
      periodoRenta: new FormControl('', [Validators.required] ),
      imagen: new FormControl('', [Validators.required]),
      //noCT: new FormControl('', [Validators.required])
    });
  }

  
  /**
   * Este método es llamado desde el formulario
   * Se encarga de disparar el método de editar el producto
   */
  onSubmitForm():void {
    if (this.angForm.valid) {
      this.commitProd();
    } else {
      Swal.fire('Error al editar un prod', 'El form está incompleto o es incorrecto, intenta de nuevo.', 'error');
    }
  }
  
  /**
   * Este método ejecuta el metodo editarProd de productoService,
   * que realiza la conección con el BackEnd y si todo sale bien 
   * redireccionamos a la vista de productos
   */
  commitProd():void{

    this.productoService.editarProd(this.producto).subscribe(response => 
      {
        Swal.fire({
          position: 'center',
          icon: 'success',
          title:  `El producto ${response.producto.nombre} se ha editado con éxito`,
          showConfirmButton: false,
          timer: 3500
        })
        // redireccionamiento para volver a la lista de productos
        this.router.navigate(['/productos']);
      },
      error => console.log(error));
  }

}
