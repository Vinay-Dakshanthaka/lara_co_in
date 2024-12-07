import { Component, OnInit } from '@angular/core';
import { HomeService } from '../services/home.service';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.css']
})
export class ContactFormComponent implements OnInit {
  contactForm: FormGroup;
  formSubmitted = false;
  constructor(private homeService: HomeService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.contactForm = this.formBuilder.group({
      firstName: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      mobileNo: new FormControl('', Validators.required),
      courseType: new FormControl(),
      learnerType: new FormControl(),
      specificMessage: new FormControl()      
    });
  }
  get firstName(){
    return this.contactForm.get('firstName');
  }
  get email(){
    return this.contactForm.get('email');
  }
  get mobileNo(){
    return this.contactForm.get('mobileNo');
  }
  save(){
    if(this.contactForm.invalid){
      this.contactForm.markAllAsTouched();
      return;
    }
    let values = this.contactForm.value.courseType;
    let commaSeparatedValues = '';
    for(var i = 0; i < values.length; i++){
      commaSeparatedValues += values[i] + ', ';
    }
    this.contactForm.value.courseType = commaSeparatedValues;
    this.homeService.__enqury(this.contactForm).subscribe(
      results =>{
        console.log(results);
        this.formSubmitted = true;
      }
    )
  }
}
