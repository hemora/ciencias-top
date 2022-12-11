import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup,  FormBuilder,  Validators, FormControl } from '@angular/forms';
import { Producto } from '../productos/producto';
import { ProductoService } from '../productos/producto.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-agrega-prd',
  templateUrl: './agrega-prd.component.html',
  styleUrls: ['./agrega-prd.component.css']
})
export class AgregaPrdComponent implements OnInit {

  producto: Producto = new Producto();
  agregForm: FormGroup;
  miStorage = window.localStorage;
  

  constructor(private productoService: ProductoService, private router: Router, private activateRoute: ActivatedRoute, private fb: FormBuilder) {
    this.createForm();
  }

  ngOnInit(): void {
    localStorage.setItem("noCT", "123456789");
  }

  public create():void{
    console.log(this.agregForm.value);
    this.productoService.create(this.agregForm.value).subscribe(producto =>
      {
        this.router.navigate(['/productos'])
        swal.fire('Nuevo Producto', `Producto creado con éxito`, 'success')
      }
    )
  }

  createForm() {
    this.agregForm = new FormGroup({
      nombre: new FormControl({nombre: ''},Validators.compose([Validators.required])),
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
