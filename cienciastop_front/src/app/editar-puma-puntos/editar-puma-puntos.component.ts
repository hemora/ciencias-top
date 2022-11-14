import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-editar-puma-puntos',
  templateUrl: './editar-puma-puntos.component.html',
  styleUrls: ['./editar-puma-puntos.component.css']
})
export class EditarPumaPuntosComponent implements OnInit {

  sumaRestaGroup!: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.sumaRestaGroup = this.fb.group({
      defCantidad: [''],
      otraCantidad: ['',  Validators.min(0)],
      opcion: ['', Validators.required]
    })
  }

  onSubmit() {
    console.log(this.sumaRestaGroup.value)
  }

}
