import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { LearnerNotificationService } from 'src/app/dashboard/services/learner-notification.service';

@Component({
  selector: 'app-learner-notification',
  templateUrl: './learner-notification.component.html',
  styleUrls: ['./learner-notification.component.css']
})
export class LearnerNotificationComponent implements OnInit {

  learnerNotificationForm: FormGroup;
  coursePackages = [];
  learnerNotifications = [];
  constructor(private learnerNotificationService: LearnerNotificationService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.learnerNotificationForm = this.formBuilder.group({
      id: new FormControl(),
      dateAndTime: new FormControl(),     
      coursePackageIds: new FormControl(), 
      note: new FormControl()
    });
    this.learnerNotificationService._readAllCoursePackages().subscribe(
      results =>{
        this.coursePackages = results;
      }
    );
    this.readAll();    
  }
  save(){
    this.learnerNotificationService._saveLearnerNotification(this.learnerNotificationForm).subscribe(
      results =>{
        console.log(results);
        this.readAll();
      }
    );
  }
  readAll(){
    this.learnerNotificationService._getAllLearnerNotifications().subscribe(
      results =>{
        console.log(results);
        this.learnerNotifications = results;
      }
    );
  }

}
