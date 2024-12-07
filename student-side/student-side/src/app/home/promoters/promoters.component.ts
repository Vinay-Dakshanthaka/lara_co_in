import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { HomeService } from '../services/home.service';

@Component({
  selector: 'app-promoters',
  templateUrl: './promoters.component.html',
  styleUrls: ['./promoters.component.css']
})
export class PromotersComponent implements OnInit {

  promotersForm: FormGroup;
  formSubmitted = false;
  constructor(private homeService: HomeService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.promotersForm = this.formBuilder.group({
      firstName: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      mobileNo: new FormControl('', Validators.required),
      oppurtunityType: new FormControl(),
      state: new FormControl('', Validators.required),
      address: new FormControl('', Validators.required)      
    });
  }
  get firstName(){
    return this.promotersForm.get('firstName');
  }
  get email(){
    return this.promotersForm.get('email');
  }
  get mobileNo(){
    return this.promotersForm.get('mobileNo');
  }
  get oppurtunityType(){
    return this.promotersForm.get('oppurtunityType');
  }
  get state(){
    return this.promotersForm.get('state');
  }
  get address(){
    return this.promotersForm.get('address');
  }
  save(){
    if(this.promotersForm.invalid){
      this.promotersForm.markAllAsTouched();
      return;
    }
    this.homeService.__businessPromoters(this.promotersForm).subscribe(
      results =>{
        console.log(results);
        this.formSubmitted = true;
      }
    )
  }


}
