import { getTreeNoValidDataSourceError } from '@angular/cdk/tree';
import { Component, ElementRef, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DataService } from 'src/app/data.service';
import { ToastService } from 'src/app/toast/toast.service';

@Component({
  selector: 'app-addclass',
  templateUrl: './addclass.component.html',
  styleUrls: ['./addclass.component.css']
})
export class AddclassComponent implements OnInit {

  websiteContent: FormGroup;
  msgOk = false;
  msgDanger = false;
  msgOkOne = false;
  msgDangerOne = false;
  msgOkTwo = false;
  msgDangerTwo = false;
  content = '';
  data = '';
  msgOne = '';
  msgTwo = '';
  fetchList: any = []
  updateForm: FormGroup;
  selectedFile: any
  scheduleForm: FormGroup
  showUploadStatus = false
  uploadStatus = false
  msgContent = ''
  msgBox = false

  constructor(
    private formBuilder: FormBuilder,
    private service: DataService,
    private router: Router,
    private elementRef: ElementRef,
    private toastService: ToastService
  ) {
    this.websiteContent = this.formBuilder.group({
      setDate: new FormControl(),
      currentDate: new FormControl('', Validators.required),
      classList: new FormArray([])
    });
    this.updateForm = formBuilder.group({
      id: new FormControl('', Validators.required),
      date: new FormControl('', Validators.required),
      startTime: new FormControl('', Validators.required),
      endTime: new FormControl('', Validators.required),
      batchName: new FormControl('', Validators.required),
      instructor: new FormControl('', Validators.required),
      classType: new FormControl('', Validators.required),
      topic: new FormControl('', Validators.required),
      place: new FormControl('', Validators.required),
    })
    console.log("constructor,,,,,,,,,");
    this.addRecord();

    this.scheduleForm = formBuilder.group({
      date: new FormControl('', Validators.required)
    })
  }

  ngOnInit() {
    console.log("ngOnInit method,,,,,,,")
    this.msgDangerTwo = false
    this.msgDangerOne = false
    this.msgOkOne = false
    this.msgOkOne = false
    this.fetchData();
  }



  get allrecords() {
    return this.websiteContent.get('classList') as FormArray;
  }

  addRecord(record?: any) {
    const newRecord = this.formBuilder.group({
      // id: new FormControl(),
      currentDate: new FormControl(),
      startTime: new FormControl(),
      endTime: new FormControl(),
      batchName: new FormControl(),
      instructor: new FormControl(),
      classType: new FormControl(),
      topic: new FormControl(),
      place: new FormControl(),
    });
    this.allrecords.push(newRecord);
  }

  removeRecord(event: Event, index: number) {
    if (this.allrecords.length > 1) this.allrecords.removeAt(index);
  }

  submitAllRecords() {
    console.log(this.websiteContent.value);
    const formData = this.websiteContent.getRawValue();
    console.log(formData);

    this.service.addClassRecord(formData).subscribe(
      response => {
        let data: any = response.valueOf();
        console.log(data);
        if (data['status'] == true) {
          this.content = data['message'];
          this.msgOk = true;
          this.ngOnInit();
          this.allrecords.clear()
          this.addRecord()
          //  this.fetchData();
        }
        else {
          this.msgOk = false;
        }
      }
    );
  }

  fetchData() {
    this.service.getSelectAll().subscribe(reponse => {
      const data: any = reponse.valueOf();
      this.fetchList = data;
      console.log(this.fetchList);
      console.log(data);

      const setDate = data[0].date;
      console.log(setDate);
      //  this.websiteContent.get('setDate')?.setValue(setDate);
      //  this.allrecords.clear();
      //  data.forEach((record:any)=>{
      //    this.addRecord(record);
      //  })
      console.log("forEach");
    })
  }

  deleteRecord(e: Event) {
    e.preventDefault()
    // this.fetchList.splice(index);
    this.service.deleteRecord(this.updateForm.value.id).subscribe(stat => {
      let data: any = stat.valueOf();
      console.log(data);
      if (data['status'] == true) {
        this.msgOne = data['message']
        this.msgOk = true;
        this.ngOnInit();
        this.hideUpdateModal(null)
      }
      else {
        this.msgOne = data['message']
        this.msgDanger = true;
      }
    })
  }

  update(index: number) {
    this.msgDangerTwo = false
    this.msgDangerOne = false
    this.msgOkOne = false
    this.msgOkOne = false
    const data = this.fetchList[index];
    console.log("mak 2 : " + this.fetchList[index]);
    console.log(this.fetchList);

    this.updateForm.patchValue({
      id: data.id,
      date: data.date,
      startTime: data.startTime,
      endTime: data.endTime,
      batchName: data.batchName,
      instructor: data.instructor,
      classType: data.classType,
      topic: data.topic,
      place: data.place
    })
    console.log(this.updateForm.value);
  }

  submit() {
    console.log(this.updateForm.value);
    this.service.updateclass(this.updateForm.value).subscribe(stat => {
      let data: any = stat.valueOf();
      console.log(data);
      if (data['status'] = true) {
        this.msgBox = true
        this.msgOkTwo = true;
        this.msgTwo = data['message']
        this.ngOnInit();
      }
      else {
        this.msgBox = true
        this.msgDangerTwo = true;
        this.msgTwo = data['message']
      }
    })

  }

  upload(e: any) {
    console.log('from selectedFile')
    this.selectedFile = e.target.files[0]
  }

  scheduleFormUpload() {
    this.showUploadStatus = false
    this.uploadStatus = false
    this.msgContent = ''
    if (!this.selectedFile) {
      return
    }
    console.log(this.scheduleForm.value)
    const formData = new FormData()
    formData.append('file', this.selectedFile)
    formData.append('date', this.scheduleForm.get('date').value + '')
    this.service.uploadQuestions(formData).subscribe(stat => {
      if (stat['status']) {
        this.toastService.showSuccessToast(this.msgContent = stat['message'])
        this.fetchData();
      }
      else {
        this.toastService.showDangerToast(this.msgContent = stat['message'])
      }
    })
  }

  hideUpdateModal(e: any) {
    this.msgBox = false
    this.elementRef.nativeElement.querySelector('.overlay').classList.add('d-none')
    if (e != null) e.preventDefault()
  }
  showUpdateModal(e: any, index: number) {
    this.elementRef.nativeElement.querySelector('.overlay').classList.remove('d-none')
    this.update(index)
    if (e != null) e.preventDefault()
  }

}
