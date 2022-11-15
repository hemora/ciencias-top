import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core'
import { RouterModule } from '@angular/router'
import { CommonModule } from '@angular/common'

import { ComponentsModule } from '../../components/components.module'
import { EditarUsr } from './editar-usr.component'

const routes = [
  {
    path: '',
    component: EditarUsr,
  },
]

@NgModule({
  declarations: [EditarUsr],
  imports: [CommonModule, ComponentsModule, RouterModule.forChild(routes)],
  exports: [EditarUsr],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class EditarUsrModule {}
