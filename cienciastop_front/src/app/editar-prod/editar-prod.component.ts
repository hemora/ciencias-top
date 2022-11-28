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
    this.createForm2();
  }

  cargarProducto(): void{
    this.activateRoute.params.subscribe(params => {
      let codigo = params['codigo']
      console.log(codigo);
      if(codigo){
        this.productoService.getProducto(codigo).subscribe((producto) => this.producto = producto)
      }
      console.log('Exito en cargar');
      console.log("Prod cargado: ");
      console.log(this.producto);
    })
  }

  ngOnInit(): void {
    //this.prod = history.state;
    this.cargarProducto()
    console.log(this.producto);
  }
  // Metodo que crea el formulario

  createForm() {
    this.angForm = this.fb.group({
      nombre: new FormControl({ nombre: this.producto.nombre }, [Validators.required] ),
      stock_inicial: new FormControl({stock_inicial: this.producto.stock_inicial }, [Validators.required] ),
      current_stock: new FormControl({current_stock: this.producto.current_stock }, [Validators.required] ),
      precio: new FormControl({precio: this.producto.precio}, [Validators.required] ),
      descripcion: new FormControl({descripcion: this.producto.descripcion}, [Validators.required] ),
      tipo: new FormControl({tipo: this.producto.tipo}, [Validators.required] ),
      categoria: new FormControl({categoria: this.producto.categoria}, [Validators.required] ),
      periodo_renta: new FormControl({periodo_renta: this.producto.periodo_renta}, [Validators.required] ),
      imagen: new FormControl({imagen: this.producto.imagen}, [Validators.required]),
      //noCT: new FormControl({noCT: this.producto.noCT}, [Validators.required]),
      //periodoRenta: new FormControl({ periodoRenta: this.producto.periodo_renta }, Validators.compose([Validators.required])),
      //imagen: new FormControl({ imagen: this.producto.imagen }, Validators.compose([Validators.required]))
    });
  }
  createForm2() {
    //this.angForm = new FormGroup({
    this.angForm = this.fb.group({
      nombre: new FormControl('', [Validators.required] ),
      //codigoP: new FormControl('', [Validators.required, Validators.minLength(12), Validators.maxLength(12)] ),
      stock_inicial: new FormControl('', [Validators.required] ),
      current_stock: new FormControl('', [Validators.required] ),
      precio: new FormControl('', [Validators.required] ),
      descripcion: new FormControl('', [Validators.required] ),
      tipo: new FormControl('', [Validators.required] ),
      categoria: new FormControl('', [Validators.required] ),
      periodo_renta: new FormControl('', [Validators.required] ),
      imagen: new FormControl('', [Validators.required]),
      //noCT: new FormControl('', [Validators.required])
    });
  }

  //Este método es llamado desde el formulario
  //Se encarga de disparar el método de guardado de productos
  onSubmitForm():void {
    if (this.angForm.valid) {
      console.log(this.producto);
      console.log("lo que se va a enviar");
      this.commitProd();
    } else {
      Swal.fire('Error al editar un prod', 'El form está incompleto o es incorrecto, intenta de nuevo.', 'error');
    }
  }
  //Este método llama al editarProd de usuarioService.
  commitProd():void{
    this.productoService.editarProd(this.producto).subscribe(response => 
      {
        console.log(response.producto);
        console.log('lo de arriba esta en base');
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title:  `El producto ${response.producto.nombre} se ha editado con éxito`,
          showConfirmButton: false,
          timer: 3500
        })
        //Llamamos al método de redirección para volver a la lista de usuarios
        this.router.navigate(['/productos']);
      },
      error => console.log(error));
  }

}
