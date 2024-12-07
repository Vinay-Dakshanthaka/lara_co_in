import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { CouponService } from '../../services/coupon.service';

@Component({
  selector: 'app-coupon',
  templateUrl: './coupon.component.html',
  styleUrls: ['./coupon.component.css']
})
export class CouponComponent implements OnInit {

  couponForm: FormGroup;
  coupons = [];
  constructor(private couponService: CouponService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.couponForm = this.formBuilder.group({
      couponCode: new FormControl(),
      couponDesc: new FormControl(),
      startDate: new FormControl(),
      endDate: new FormControl(),
      reductionPercentage: new FormControl(),
      cusomerGroup: new FormControl()
    });
    this.readAll();
  }
  save(){
    this.couponService._saveCoupon(this.couponForm).subscribe(
      results =>{
        console.log(results);
        this.readAll();
      }
    );
  }
  readAll(){
    this.couponService._readAllCoupons().subscribe(
      results =>{
        this.coupons = results;
      }
    );
  }


}
