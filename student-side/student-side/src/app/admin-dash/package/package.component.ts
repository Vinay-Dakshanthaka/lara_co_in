import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DataService } from 'src/app/data.service';
import { Observable } from 'rxjs';

export interface PackageData {
  id: number;
  packageName: string;
  startDate: string;
  duration: string;
  demoStartDate: string;
  demoEndDate: string;
  scholarshipDate: string;
  selected: boolean; // Add a 'selected' field to the interface

}

@Component({
  selector: 'app-package',
  templateUrl: './package.component.html',
  styleUrls: ['./package.component.css']
})
export class PackageComponent {

  packageForm: FormGroup;
  saveMessage: any;
  updateMessage: any;
  packagedata: any = []; // Ensure that the type is specified
  hiddenu: boolean = true;
  hiddens: boolean = false;
  entryToUpdate: any;
  selectedRowIndex: number = -1;
  selectedData: PackageData[] = [];
  selecting: any;
  id : any;

  newPackageData = {
    id: "",
    "packageName": "",
    "startDate": "",
    "duration": "",
    "demoStartDate": "",
    "demoEndDate": "",
    "scholarshipDate": ""
  }

  constructor(private formBuilder: FormBuilder, private service: DataService, private http: HttpClient) {
    this.packageForm = this.formBuilder.group({
      packageName: ['', Validators.required],
      startDate: ['', Validators.required],
      demoStartDate: ['', Validators.required],
      demoEndDate: ['', Validators.required],
      duration: ['', Validators.required],
      scholarshipDate: ['', Validators.required],
      selected: false
    });
  }

  save(data: any) {
    console.log(this.packageForm.value);
    this.service.packageDataSave(this.packageForm.value).subscribe(
      r1=>
      {
        console.log("data saved successfully");
        
      }
    )
    this.saveMessage = "Data Saved Successfully";
    
    setTimeout(() => {
      this.packageForm.reset();
      this.ngOnInit();
    }, 500);
  }

  ngOnInit() {
    this.saveMessage = "";
    this.updateMessage = "";
    this.loadCourseData();
  }

  loadCourseData() {
    this.service.getPackageData().subscribe(
      data => {
        this.packagedata = data;
        // If 'selected' field is true, mark the checkbox as selected
        this.packagedata.forEach((item :any) => {
          if (item.selected) {
            item.selected = true;
            this.selectedData.push(item);
          }
        });
        sessionStorage.setItem("packageselected", JSON.stringify(this.selectedData));
        console.log(this.packagedata);
      }
    );
  }

  updateEntry(index: any) {
    this.id = index + 1;
    this.hiddenu = false;
    this.hiddens = true;
    this.entryToUpdate = this.packagedata[index];
    this.packageForm.patchValue({
      packageName: this.entryToUpdate.packageName,
      startDate: this.entryToUpdate.startDate,
      duration: this.entryToUpdate.duration,
      demoStartDate: this.entryToUpdate.demoStartDate,
      demoEndDate: this.entryToUpdate.demoEndDate,
      scholarshipDate: this.entryToUpdate.scholarshipDate
    });
  }

  changes() {
    this.newPackageData.id = this.id;
    
    this.newPackageData.packageName = this.packageForm.value.packageName;
    this.newPackageData.startDate = this.packageForm.value.startDate;
    this.newPackageData.duration = this.packageForm.value.duration;
    this.newPackageData.demoStartDate = this.packageForm.value.demoStartDate;
    this.newPackageData.demoEndDate = this.packageForm.value.demoEndDate;
    this.newPackageData.scholarshipDate = this.packageForm.value.scholarshipDate;
    
    this.service.updatePackagData(this.newPackageData).subscribe(
      r1 =>
      {
        console.log("data updated successfully");
        this.updateMessage = r1.message;
        
        setTimeout(() => {
          this.packageForm.reset();
          this.ngOnInit();
        }, 500);
        
      }
    )
  }

  select(index: number) {

    this.packagedata[index].selected = !this.packagedata[index].selected;

    sessionStorage.setItem("courseselected", JSON.stringify(this.packagedata));

    const updatedData = {
      ...this.packagedata[index]
    };


    this.service.updatePackagData(updatedData).subscribe(
      (r1) => {
        console.log("Selected status updated in backend:");


        if (this.packagedata[index].selected) {
          this.selectedData.push(this.packagedata[index]);
        } else {
          const selectedIndex = this.selectedData.findIndex(item => item.id === this.packagedata[index].id);
          if (selectedIndex !== -1) {
            this.selectedData.splice(selectedIndex, 1);
          }
        }

        sessionStorage.setItem("packageselected", JSON.stringify(this.selectedData));
      },
      error => {
        console.error("Error updating selected status in backend:", error);
      }
    );
  }

}
