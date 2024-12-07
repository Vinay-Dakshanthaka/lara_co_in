import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup,FormControl,Validators } from '@angular/forms';
import { DataService } from '../data.service';

@Component({
  selector: 'app-show-placementdata',
  templateUrl: './show-placementdata.component.html',
  styleUrls: ['./show-placementdata.component.css']
})
export class ShowPlacementdataComponent implements OnInit {

  selectedData : any;
  constructor(private service : DataService) { }

  ngOnInit() {
      
    this.service.getselectedEntry().subscribe(
      r1=>
      {
        this.selectedData = r1;
        console.log(r1);
        
      }
    )
  }

}
