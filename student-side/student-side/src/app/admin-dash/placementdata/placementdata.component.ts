import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder,FormGroup,FormControl,Validators } from '@angular/forms';
import { DataService } from 'src/app/data.service';

interface PlacementData {
  id: string;
  date: string;
  companyName: string;
  studentAttended: string;
  studentPlaced: string;
  salaryPackage: string;
  selected: boolean;
}

@Component({
  selector: 'app-placementdata',
  templateUrl: './placementdata.component.html',
  styleUrls: ['./placementdata.component.css']
})
export class PlacementdataComponent {

  placementForm : FormGroup;
  saveMessage : any;
  updateMessage : any;
  placementdata : any = [];
  hiddenu : boolean = true;
  hiddens : boolean = false;
  selectedRowIndex: number = -1;
  selectedData: PlacementData[] = [];
  selecting : any;

   newPlacementData = 
   {
     "id" :  "",
     "date" : "",
     "companyName" : "",
     "studentAttended" : "",
     "studentPlaced" : "",
     "salaryPackage" : ""
   }

  constructor(private formBuilder: FormBuilder,private service : DataService,private http : HttpClient) { 
    this.placementForm = this.formBuilder.group({
      companyName: ['', Validators.required],
      studentPlaced: ['', Validators.required],
      studentAttended: ['', Validators.required],
      salaryPackage : ['',Validators.required],
      date: ['', Validators.required],
      selected : false
    });
  }

  save(data : any)
  {
    this.service.PlacementDataSave(this.placementForm.value).subscribe(
      r1 =>
      {
        console.log("Data Saved Successfully");
        
      }
    )
    this.saveMessage = "Data Saved Successfully";
    
    setTimeout(() => {
      this.placementForm.reset();
       this.ngOnInit();
    }, 500);
   

   }

   ngOnInit()
  { 
    this.saveMessage = "";
    this.updateMessage = "";
     this.loadPlacementData();
  }

  loadPlacementData()
  {
    this.service.getPlacementData().subscribe(
      data =>
      {
        this.placementdata = data;

        this.placementdata.forEach((item : any) => 
        {
          if(item.selected)
          {
            item.selected = true;
            this.selectedData.push(item);
          }
        });
        sessionStorage.setItem("selected",JSON.stringify(this.selectedData));
        console.log(this.placementdata);
        
      }
    );
  }

  updateEntry(index: number) {
    this.hiddenu = false;
    this.hiddens = true;
    const entryToUpdate = this.placementdata[index];
    var date = document.getElementById("date") as HTMLInputElement;
    var studentAttended = document.getElementById("studentAttended") as HTMLInputElement;
    var studentPlaced = document.getElementById("studentPlaced") as HTMLInputElement;
    var salaryPackage = document.getElementById("salaryPackage") as HTMLInputElement;
    var companyName = document.getElementById("companyName") as HTMLInputElement;

    date.value = this.placementdata[index].date;
    studentAttended.value = this.placementdata[index].studentAttended;
    studentPlaced.value = this.placementdata[index].studentPlaced;
    salaryPackage.value = this.placementdata[index].salaryPackage;
    companyName.value = this.placementdata[index].companyName;
    this.newPlacementData.id = this.placementdata[index].id;

  }

  changes()
  {
    
    var date = document.getElementById("date") as HTMLInputElement;
    var studentAttended = document.getElementById("studentAttended") as HTMLInputElement;
    var studentPlaced = document.getElementById("studentPlaced") as HTMLInputElement;
    var salaryPackage = document.getElementById("salaryPackage") as HTMLInputElement;
    var companyName = document.getElementById("companyName") as HTMLInputElement;
    console.log(date.value);

   this.newPlacementData.date = date.value;
   this.newPlacementData.companyName = companyName.value;
   this.newPlacementData.studentAttended = studentAttended.value;
   this.newPlacementData.studentPlaced = studentPlaced.value;
   this.newPlacementData.salaryPackage = salaryPackage.value;

   console.log(this.newPlacementData);
   
     this.service.updatePlacementData(this.newPlacementData).subscribe(
      r1 =>
      {
        console.log("data updated successfully");
        this.updateMessage = r1.message;

        setTimeout(() => {
          this.placementForm.reset();
          this.ngOnInit();
        }, 500); 
      }
     )

  }
  
  select(index: number) {
    this.placementdata[index].selected = !this.placementdata[index].selected;
    sessionStorage.setItem("selected",JSON.stringify(this.placementdata));
    const updatedData = {
      ...this.placementdata[index]
    };
    this.service.updatePlacementData(updatedData).subscribe(
      (r1) => {
        console.log("selected status updated in backend");
    if (this.placementdata[index].selected) {
      this.selecting = this.placementdata[index];
      this.service.updatePlacementData(this.selecting);
      this.selectedData.push(this.placementdata[index]);
      
    }
    else {
      const selectedIndex = this.selectedData.findIndex((item ) => item.id === this.placementdata[index].id);
      if (selectedIndex !== -1) {
        this.selectedData.splice(selectedIndex, 1);
      }
    }
      sessionStorage.setItem("selected" ,JSON.stringify(this.selectedData)); 
  },
      error =>{
      console.error("Error updating selected status in backend:",error);
    
  }
    );

  }


}
