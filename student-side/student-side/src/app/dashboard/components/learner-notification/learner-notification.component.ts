import { Component, OnInit } from '@angular/core';
import { LearnerNotificationService } from '../../services/learner-notification.service';

@Component({
  selector: 'app-learner-notification',
  templateUrl: './learner-notification.component.html',
  styleUrls: ['./learner-notification.component.css']
})
export class LearnerNotificationComponent implements OnInit {

  learnerNotifications = [];
  constructor(private learnerNotification: LearnerNotificationService) { }

  ngOnInit(): void {
    this.learnerNotification._getLearnerNotificationsForCurrentUser().subscribe(
      results =>{
        this.learnerNotifications = results;            
      }
    )
  }
  getDate(date: string){
    return date.substr(0, date.indexOf('T'));
  }
}
