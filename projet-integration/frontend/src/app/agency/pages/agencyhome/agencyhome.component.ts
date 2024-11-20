import { Component } from '@angular/core';
import { SidebarComponent } from '../../components/sidebar/sidebar.component';
import { EditoffersComponent } from '../editoffers/editoffers.component';
import { RouterModule } from '@angular/router';
@Component({
  selector: 'app-agencyhome',
  standalone: true,
  imports: [SidebarComponent, EditoffersComponent,RouterModule],
  templateUrl: './agencyhome.component.html',
})
export class AgencyhomeComponent {

}
