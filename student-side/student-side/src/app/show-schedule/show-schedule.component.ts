import { Component, OnInit } from '@angular/core';
import { DataService } from '../data.service';
import { DatePipe } from '@angular/common';
import { Statement } from '@angular/compiler';

@Component({
  selector: 'app-show-schedule',
  templateUrl: './show-schedule.component.html',
  styleUrls: ['./show-schedule.component.css'],
  providers:[DatePipe]
})
export class ShowScheduleComponent implements OnInit {

  constructor(private service : DataService) { }

  fetchList  :any=[]
listRecords:any;
  ngOnInit(){
    this.service.getSelectAll().subscribe(stat=>{
       this.fetchList = stat;
       localStorage.setItem("data",JSON.stringify(stat));
       this.listRecords=localStorage.getItem('data');
       console.log(this.fetchList);
    })
 }
}
