import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Monedero } from './monedero';
import { MonederoService } from './monedero.service';

@Component({
  selector: 'app-editar-puma-puntos',
  templateUrl: './editar-puma-puntos.component.html',
  styleUrls: ['./editar-puma-puntos.component.css']
})
export class EditarPumaPuntosComponent implements OnInit {

  private monederoId: number = 1;
  private monedero: Monedero;

  sumaRestaGroup!: FormGroup;

  constructor(private fb: FormBuilder, private monederoService: MonederoService) { }

  ngOnInit(): void {
    this.sumaRestaGroup = this.fb.group({
      defCantidad: [''],
      otraCantidad: ['',  Validators.min(0)],
      opcion: ['', Validators.required]
    });

  }

  onSubmit() {
    if (this.sumaRestaGroup.valid) {
      console.log(this.sumaRestaGroup.value);
      // Se obtienen los datos del monedero para operar
      this.monederoService.getMonedero(this.monederoId).subscribe(
        (m: Monedero) => {
          console.log(m);
          this.monedero = m;
          console.log(this.monedero);
        }
      );
      // Se operan los datos del monedero
    }
  }

}
