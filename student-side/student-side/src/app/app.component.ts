import { Component } from '@angular/core';
import { LoaderService } from './common-service/loader.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'my-app';

  constructor(public loaderService: LoaderService){

  }
}
