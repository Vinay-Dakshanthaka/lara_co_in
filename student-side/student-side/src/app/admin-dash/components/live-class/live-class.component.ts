import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { LiveClassService } from '../../services/live-class.service';

@Component({
  selector: 'app-live-class',
  templateUrl: './live-class.component.html',
  styleUrls: ['./live-class.component.css']
})
export class LiveClassComponent implements OnInit {

  liveClassForm: FormGroup;
  coursePackages = [];
  liveClasses = [];
  constructor(private liveClassService: LiveClassService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.liveClassForm = this.formBuilder.group({
      id: new FormControl(),
      dateAndTime: new FormControl(),
      coursePackageIds: new FormControl(),
      liveUrl: new FormControl(),
      password: new FormControl(),
      topic: new FormControl(),
      dependents: new FormControl(),
      isPublic: new FormControl(),
      note: new FormControl()
    });
    this.liveClassService._readAllCoursePackages().subscribe(
      results =>{
        this.coursePackages = results;
      }
    );
    this.readAll();    
  }
  save(){
    this.liveClassService._saveLiveClass(this.liveClassForm).subscribe(
      results =>{
        console.log(results);
        this.readAll();
      }
    );
  }
  readAll(){
    this.liveClassService._readAllLiveClasses().subscribe(
      results =>{
        console.log(results);
        this.liveClasses = results;
      }
    );
  }
}
