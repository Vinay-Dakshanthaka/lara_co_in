import { Component, OnInit } from '@angular/core';
import { HomeService } from '../home/services/home.service';
import { TokenStorageService } from '../auth/token-storage.service';
declare var $: any;
import * as _ from 'underscore';
import { LoaderService } from '../services/loader.service';
import { Router, ActivatedRoute } from '@angular/router';

interface Review {
  name: string;
  rating: number;
  comment: string;
}
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  coursePackages: Array<any>;
  recruitersData: Array<any>;
  textTestimonialList:Array<any>;
  videoTestimonialList:Array<any>;
  pkgRow = true;
  allPkgs = false;
  showLogin = true;
  role: string;
  info: any;
  showModal:boolean = false;
  coreJava = "<table><tr><td>1. Core Java Basics</td> </tr><tr><td>2. Core Java advanced</td></tr></table>";
  constructor(public homeService: HomeService,
    public token: TokenStorageService,
    public loaderService: LoaderService,
    public router: Router,
    public activatedRoute: ActivatedRoute,
    public tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    let token = this.tokenStorageService.getToken();

    this.info = {
      token: token,
      username: this.tokenStorageService.getUsername(),
      authorities: this.tokenStorageService.getAuthorities()
    };
    if (this.info.authorities.length > 0) {
      this.role = this.info.authorities[0];
    }

    this.token.getUserId();
    this.activatedRoute.queryParams.subscribe(quesyParams => {
      let logout = quesyParams['logout'];
      if (logout != null && logout === "true") {
        let url = window.location.href.split('#')[0];
        window.location.href = url;
      }
    });
    if (this.tokenStorageService.getUserId() != null) {
      this.showLogin = false;
    }
    $(document).ready(function () {
      $("#flexiselDemo3").flexisel({
        visibleItems: 5,
        itemsToScroll: 1,
        autoPlay: {
          enable: true,
          interval: 2000,
          pauseOnHover: true
        }
      });
    });

     this.getHomePageElements();
     this.getCoursePackages();
     this.getTestimonials();
  }

  gotoBuy(packageId){
    
  }
  getCoursePackages() {
    this.homeService._getCoursePackages().subscribe(data => {
      this.coursePackages = data.coursePackageList;
      this.coursePackages = this.coursePackages.filter((cpg: any) => {
        return (cpg.isActive == 1)
      });
      _.each(this.coursePackages, (cp: any) => {
        cp.noOfCourses = cp.coursesUnderPackageList.length;
      })
      this.loaderService.hide();
    })
  }

  getTestimonials(){
    this.homeService._getTestimonials().subscribe(data=>{
      this.textTestimonialList = data.testimonialsData.filter((td:any)=>{
        return td.testimonialType === 'Text';
      });
      this.videoTestimonialList = data.testimonialsData.filter((td:any)=>{
        return td.testimonialType === 'Video';
      });
      console.log(this.textTestimonialList);
      console.log(this.videoTestimonialList);
    }, error=>{

    });
  }

  getAllTestimonials(){
    this.homeService._getAllTestimonials().subscribe(data=>{
      this.textTestimonialList = data.testimonialsData.filter((td:any)=>{
        return td.testimonialType === 'Text';
      });
      this.videoTestimonialList = data.testimonialsData.filter((td:any)=>{
        return td.testimonialType === 'Video';
      });
      console.log(this.textTestimonialList);
      console.log(this.videoTestimonialList);
    }, error=>{

    });
  }


  getHomePageElements() {
    this.loaderService.show();
    this.homeService._getHomePageElements()
      .subscribe(data => {
        this.coursePackages = data.homeElements.coursePackageList;
        _.each(this.coursePackages, (cp: any) => {
          cp.noOfCourses = cp.coursesUnderPackageList.length;
        })
        this.recruitersData = data.homeElements.recruitersData;
        this.loaderService.hide();
      }, error => {
      });
  }

  logout() {
    this.token.signOut();
    window.location.reload();
  }

  showAllPkgs() {
    this.allPkgs = true;
    this.pkgRow = false;
  }

  showLessPkgs() {
    this.allPkgs = false;
    this.pkgRow = true;
  }

  checkLoggedInUser(coursePackageId: number) {
    if (this.tokenStorageService.getUserId() != null) {
      this.router.navigate(['/coursedetails', coursePackageId]);
    } else {
      document.getElementById("showLoginAlert").click();
    }

  }

  changeTestimonialSrc(event: any) {
    console.log(event);
    this.showModal = true; // Show-Hide Modal Check
    //this.content = "This is content!!"; // Dynamic Data
    //this.title = "This is title!!";    // Dynamic Data
  }

  //Bootstrap Modal Close event
  hideTestimonialModal(){
    this.showModal = false;
  }

  showAllReviews(){    
    $('#showAllTxtTstmModal').modal('toggle');
    this.getAllTestimonials();
  }

  
  
}
// Function to animate counting



