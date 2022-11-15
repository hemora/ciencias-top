import { Component } from '@angular/core'
import { Title, Meta } from '@angular/platform-browser'

@Component({
  selector: 'editar-usr',
  templateUrl: 'editar-usr.component.html',
  styleUrls: ['editar-usr.component.css'],
})
export class EditarUsr {
  
  constructor(private title: Title, private meta: Meta) {
    this.title.setTitle('EditarUsr - exported project')
    this.meta.addTags([
      {
        property: 'og:title',
        content: 'EditarUsr - exported project',
      }
    ])
  }

}
