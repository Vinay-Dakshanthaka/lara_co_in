
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from  '@angular/forms';
import { UploadService } from '../../services/upload.service';
//import { UploadService } from  'src/app/Services/upload.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: []
})
export class UploadComponent implements OnInit {

  form: FormGroup;
  error: string;
  userId: number = 1;
  bulkUploadModel: number =1;
  uploadResponse = { status: '', message: '', filePath: '' };
  constructor(private formBuilder: FormBuilder, private uploadService: UploadService) { }

  ngOnInit() {
    this.form = this.formBuilder.group({
      avatar: ['']
    });
  }

  onFileChange(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.form.get('avatar').setValue(file);
    }
  }
  chooseBulUploadType(event:any){
    this.bulkUploadModel = event.target.value;
  }

  onSubmit() {
    const formData = new FormData();
    formData.append('file', this.form.value.avatar);

    this.uploadService.upload(formData, this.bulkUploadModel).subscribe(
      (res) => this.uploadResponse = res,
      (err) => this.error = err
    );
  }
}