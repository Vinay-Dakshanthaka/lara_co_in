import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CourseService } from '../../services/course.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { resolve } from 'url';

@Component({
  selector: 'app-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course-detail.component.css']
})
export class CourseDetailComponent implements OnInit {
  userId: string;
  coursePdfName: any;
  topicName: 'Topic1';

  showFrame = false
  subTopics: Array<any> = [
    { subTopicId: '1', subtopicName: 'Section1' },
    { subTopicId: '2', subtopicName: 'Section2' },
    { subTopicId: '3', subtopicName: 'Section3' }
  ];
  packageData: any

  constructor(private router: Router, private activatedRoute: ActivatedRoute,
    private courseService: CourseService,
    private tokenStorageService: TokenStorageService,
    private cdr: ChangeDetectorRef) {

  }

  ngOnInit() {
    this.userId = this.tokenStorageService.getUserId();
    this.coursePdfName = this.activatedRoute.snapshot.paramMap.get('coursePdfName')
    this.showFrame = true
    // console.log(this.coursePdfName)
    // this.cdr.detectChanges()
    // console.log(this.courseId)
    // this.getCourseDetailsByCoursePackageId(this.courseId);
  }

  getCourseDetailsByCoursePackageId(courseId: number) {
    this.courseService._getCoursePackage(courseId)
      .subscribe(data => {
        // console.log(data)
        this.packageData = data
      }, error => {
      });
  }

  getNewLineFormattedString(desc: string) {
    desc.replace(/\n/g, '<br>')
    return desc
  }

  get getPdfPath() {
    // Logic to generate and return the PDF path
    console.log("./assets/pdf/coursePackage/" + this.coursePdfName + ".pdf")
    return "./assets/pdf/coursePackage/" + this.coursePdfName + ".pdf"
  }


}
