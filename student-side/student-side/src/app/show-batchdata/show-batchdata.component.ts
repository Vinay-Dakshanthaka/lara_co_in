import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DataService } from '../data.service';

@Component({
  selector: 'app-show-batchdata',
  templateUrl: './show-batchdata.component.html',
  styleUrls: ['./show-batchdata.component.css']
})
export class ShowBatchdataComponent implements OnInit {
  selectedData = [];

  constructor(private http: HttpClient, private service: DataService) { }

  ngOnInit() {
    this.service.getSelectedPackage().subscribe(
      r1 => {
        this.selectedData = r1;
        console.log(r1);

      }
    )
  }

}
