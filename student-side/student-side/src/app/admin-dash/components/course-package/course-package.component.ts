import { Component, OnInit } from '@angular/core';
import { LoaderService } from 'src/app/common-service/loader.service';
import { CourseService } from 'src/app/course/services/course.service';
import { faPenToSquare } from '@fortawesome/free-solid-svg-icons'
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { ToastService } from 'src/app/toast/toast.service';

@Component({
  selector: 'app-course-package',
  templateUrl: './course-package.component.html',
  styleUrls: ['./course-package.component.css']
})
export class CoursePackageComponent implements OnInit {

  editIcon = faPenToSquare
  packageList = []
  packageForm: FormGroup
  constructor(
    private loaderService: LoaderService,
    private courseService: CourseService,
    private formBuilder: FormBuilder,
    private toastService: ToastService
  ) {
    this.packageForm = this.formBuilder.group({
      coursePackageId: new FormControl('', [Validators.required]),
      coursePackageName: new FormControl('', [Validators.required]),
      coursePackageDesc: new FormControl('', [Validators.required]),
      coursePackagePrice: new FormControl('', [Validators.required]),
      coursePackageOriginalPrice: new FormControl('', [Validators.required]),
      coursePackageOfferText: new FormControl('', [Validators.required]),
      coursePackageDuration: new FormControl('', [Validators.required]),
      isActive: new FormControl('', [Validators.required])
    })
  }

  ngOnInit() {
    this.fetchCoursePackageList()
  }


  fetchCoursePackageList() {
    this.loaderService.show()
    this.courseService._getCoursePackageList().subscribe(stat => {
      if (stat.exceptionReport == null) {
        // console.log(stat)
        this.packageList = stat.coursePackageList
      }
      // console.log(this.packageList)
      this.loaderService.hide()
    })
  }

  openModal(index: number) {
    const cPackage = this.packageList[index]
    this.packageForm.patchValue({
      coursePackageId: cPackage.coursePackageId,
      coursePackageName: cPackage.coursePackageName,
      coursePackageDesc: cPackage.coursePackageDesc,
      coursePackagePrice: cPackage.coursePackagePrice,
      coursePackageOriginalPrice: cPackage.coursePackageOriginalPrice,
      coursePackageOfferText: cPackage.coursePackageOfferText,
      coursePackageDuration: cPackage.coursePackageDuration,
      isActive: cPackage.isActive
    })
  }

  savePackage() {
    // console.log(this.packageForm.value)
    this.courseService._updatePackage(this.packageForm.value).subscribe(res => {
      if (res.status) {
        this.toastService.showSuccessToast(res.message)
        this.fetchCoursePackageList()
      }
      else {
        this.toastService.showDangerToast('Could not update')
      }
    })
  }

}
