import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { LiveClassService } from '../../services/live-class.service';

@Component({
  selector: 'app-live-class',
  templateUrl: './live-class.component.html',
  styleUrls: ['./live-class.component.css']
})
export class LiveClassComponent implements OnInit {

  liveClasses = [];
  constructor(private liveClassService: LiveClassService) { }

  ngOnInit(): void {
    this.liveClassService._getLiveClasses().subscribe(
      results =>{
        this.liveClasses = results;            
      }
    )
  }
  getDate(date: string){
    return date.substr(0, date.indexOf('T'));
  }

  getTime(date: string){
    return date.substr(date.indexOf('T') + 1);
  }

/*
  save(){
    this.httpClient.post('http://localhost:8086/save', this.liveClassForm.value).subscribe(
      results =>{
        console.log("saved");
        console.log(results);
        this.readAll();
        this.liveClassForm.reset()
      }
    )
  }
  readAll(){
    this.httpClient.get<any>('http://localhost:8086/readAll').subscribe(
      results =>{
        console.log(results);
        this.liveClasses = results;
      }
    );
  }
  read(userId: HTMLInputElement){
    this.httpClient.get<any>('http://localhost:8086/read' + '/' + userId.value).subscribe(
      results =>{
        console.log(results);
        this.liveClasses = results;
      }
    );
  }
*/

}





