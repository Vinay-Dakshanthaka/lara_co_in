import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { UploadService } from '../services/upload.service';

@Component({
  selector: 'app-bulk-subscription',
  templateUrl: './bulk-subscription.component.html',
  styleUrls: ['./bulk-subscription.component.css']
})
export class BulkSubscriptionComponent implements OnInit {

  form: FormGroup;
  error: string;
  userId: number = 1;
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
  

  onSubmit() {
    const formData = new FormData();
    formData.append('file', this.form.value.avatar);

    this.uploadService.bulkUpload(formData).subscribe(
      (res) => this.uploadResponse = res,
      (err) => this.error = err
    );
  }
}
