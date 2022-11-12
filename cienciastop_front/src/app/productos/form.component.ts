import { Component, OnInit } from '@angular/core';
import { Producto } from './producto';
import { ProductoService } from './producto.service';
import { Router, ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { Observable } from 'rxjs';
import { FormGroup,  FormBuilder,  Validators, FormControl } from '@angular/forms';


@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  titulo: string = "Añadir Producto"
  producto: Producto = new Producto()
  angForm: FormGroup;

  constructor(private productoService: ProductoService, private router: Router, private activateRoute: ActivatedRoute, private fb: FormBuilder) {
    this.createForm();
  }

  ngOnInit(): void {
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

  public create():void{
    console.log(this.angForm.value);
    this.productoService.create(this.angForm.value).subscribe(producto =>
      {
        this.router.navigate(['/productos'])
        swal.fire('Nuevo Producto', `Producto ${producto.nombre} creado con éxito`, 'success')
      }
    )
  }

  public update():void{
    this.productoService.update(this.producto).subscribe(producto => 
      {
      this.router.navigate(['/productos'])
      swal.fire('Producto Actualizado', `${producto.nombre} actualizado con éxito`, 'success')
      }
    )
  }

  createForm() {
    this.angForm = this.fb.group({
      nombre: new FormControl('', [Validators.required] ),
      codigo: new FormControl('', [Validators.required, Validators.minLength(12), Validators.maxLength(12)] ),
      stockInicial: new FormControl('', [Validators.required] ),
      precio: new FormControl('', [Validators.required] ),
      descripcion: new FormControl('', [Validators.required] ),
      tipo: new FormControl('', [Validators.required] ),
      categoria: new FormControl('', [Validators.required] ),
      periodoRenta: new FormControl('', [Validators.required] ),
      imagen: new FormControl('', [Validators.required])
    });
  }

}
