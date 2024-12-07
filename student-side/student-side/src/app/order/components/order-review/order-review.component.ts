import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CourseService } from 'src/app/course/services/course.service';
import { OrderService } from '../../services/order.service';
import { ConstantsService } from 'src/app/shared/services/constants.service';

@Component({
  selector: 'app-order-review',
  templateUrl: './order-review.component.html',
  styleUrls: ['./order-review.component.css']
})
export class OrderReviewComponent implements OnInit {
  coursePackageId:number;
  invalidCouponCode;
  couponCode = "none";
  reductionPercentage;
  coursePackageDetails;//:Object = {};
  courseList:Array<any> = [];
  noOfCourses:number;
  paymentBaseUrl:string;
  isSubscribed = false;
  gstValue = 0.0;
  constructor(private router: Router, 
    private activatedRoute: ActivatedRoute,
    private courseService:CourseService,
    private orderService: OrderService,
    private constantsService: ConstantsService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {      
      this.coursePackageId = +params['coursePackageId'];
    });
    this.checkSubscription(this.coursePackageId);
    this.paymentBaseUrl = this.constantsService.ENDPOINT_BASE_URL+"/orderp/processOrder"
    this.getCoursePackage(this.coursePackageId);
  }

  checkSubscription(coursePackageId:number){
    this.courseService._checkCoursePackageSubscription(coursePackageId).subscribe(data=>{
      if(data){
        this.isSubscribed = true;
      }
    });
  }

  originalPrice;

  getCoursePackage(coursePackageId:number){
    this.courseService._getCoursePackage(coursePackageId)
      .subscribe(data=>{
        this.coursePackageDetails = data;
        this.courseList = data.coursesUnderPackageList;
        this.noOfCourses = data.coursesUnderPackageList.length
        console.log("coursePackageDetails.coursePackagePrice", this.coursePackageDetails.coursePackagePrice);
        console.log("this.coursePackageDetails.gstPercentage", this.coursePackageDetails.gstPercentage);
        this.originalPrice = this.coursePackageDetails.coursePackagePrice;
        this.gstValue = this.coursePackageDetails.coursePackagePrice * this.coursePackageDetails.gstPercentage / 100;

      });
      
  }  
  getFinalPriceWithCouponCode(couponCode){
    
    this.courseService._getCouponCode(couponCode)
    .subscribe(data=>{
      if(data){
        this.couponCode = couponCode;
        this.invalidCouponCode = "";
        //console.log("coupon:" + data);
        this.reductionPercentage = data.reductionPercentage;
        //console.log("reduction percentage:" + reductionPercentage);
        
        this.coursePackageDetails.coursePackagePrice = this.originalPrice - ((this.originalPrice * this.reductionPercentage) / 100);
        this.gstValue = this.coursePackageDetails.coursePackagePrice * this.coursePackageDetails.gstPercentage / 100;
      }
      else{
        this.invalidCouponCode = couponCode + " is invalid or expired";
      }
    },
    error =>{
      this.invalidCouponCode = couponCode + " is invalid or expired";
      error = "";
    })
  }

}
