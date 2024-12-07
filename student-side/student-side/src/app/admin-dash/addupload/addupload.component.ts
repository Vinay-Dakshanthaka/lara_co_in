import { Component } from '@angular/core';
import { TestService } from 'src/app/test.service';

@Component({
  selector: 'app-addupload',
  templateUrl: './addupload.component.html',
  styleUrls: ['./addupload.component.css']
})
export class AdduploadComponent  {

  selectedFile: File | null = null;
  content = '';
  msgOk=false;
  msgDanger=false;
  fetchQuestions:any=[]
  
  constructor(private uploadService: TestService) {}
  
  ngOnInit(): void {}
  
  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    event.preventDefault()
  }
  
  //upload questions file by admin
  onUpload() {
    console.log("upload");
    if (this.selectedFile) {
      const formData = new FormData();
      formData.append('file', this.selectedFile);
      console.log(formData);
  
      this.uploadService.addtest(formData).subscribe( 
        response => {
          let data:any = response.valueOf();
          console.log(data);
          if (data['status'] == true) {
            console.log("inside response")
            this.msgOk=true;
            this.content = data['message'];
          } else {
            this.msgDanger=true;
            console.log("inside response")
            this.content = data['message'];
          }
        }
      );
    }
  }
}
