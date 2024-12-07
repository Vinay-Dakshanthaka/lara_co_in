import { Component, OnInit } from '@angular/core';
import { ConstantsService } from 'src/app/shared/services/constants.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AdminService } from '../../services/admin.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-testimonial',
  templateUrl: './testimonial.component.html',
  styleUrls: [],
  animations: [	
		trigger('simpleFadeAnimation', [
		  state('in', style({opacity: 1})),
		  transition(':enter', [style({opacity: 0}),animate(600 )]),	
		  transition(':leave', animate(600, style({opacity: 0})))
		])
	  ]
})
export class TestimonialComponent implements OnInit {
  testimonialsList:Array<any>;
  testimonialForm: FormGroup;
  testimonialId:number = 0;
  addTestimonialFormDiv:boolean = false;
  testimonialListDiv:boolean = true;
  editTestimonialFormDiv:boolean = false;
  updateRespAlert:boolean = false;
  updateRespAlertText:string = '';

  constructor(private constantService:ConstantsService, private router:Router, private activatedRoute:ActivatedRoute,
    private adminService: AdminService, private fb:FormBuilder) { }

  ngOnInit() {
    this.getAllTestimonials();  
    this.initTestimonialForm();  
  }

  getAllTestimonials(){
    this.adminService._getAllTestimonials().subscribe(data=>{
      this.testimonialsList = data.testimonialsData;
    }, error=>{
    });
  }

  switchAction(action:string, testimonialId:number){
    if(action === 'add'){
      this.addTestimonialFormDiv = true;
      this.testimonialListDiv = false;
      this.editTestimonialFormDiv = false;
    }else if(action === 'list'){
      this.getAllTestimonials();
      this.addTestimonialFormDiv = false;
      this.testimonialListDiv = true;
      this.editTestimonialFormDiv = false;
      this.initTestimonialForm();
    }else if(action === 'edit'){
      this.addTestimonialFormDiv = false;
      this.testimonialListDiv = false;
      this.editTestimonialFormDiv = true;
      this.initTestimonialFormData(testimonialId);
    }    
  }

  initTestimonialForm(){
    this.testimonialForm = this.fb.group({
      testimonialId: [this.testimonialId],
      testimonialType: ['', Validators.required],
      testimonialText: ['', Validators.required],
      testimonialDesc: '',
      rating:'',
      testimonialVideoUrl: '',
      testimonialThubmPath: ''
    });
  }

  initTestimonialFormData(testimonialId:number){
    this.adminService._getTestimonialById(testimonialId).subscribe(data=>{
      this.testimonialForm = this.fb.group(data);
    });    
  }

  addTestimonial(){
    this.adminService._saveTestimonial(this.testimonialForm.value).subscribe(data=>{
      if(this.testimonialForm.value.testimonialId != 0){
        this.updateRespAlertText = 'Testimonial has been updated successfully';
      }else{
        this.updateRespAlertText = 'Testimonial has been added successfully';
      }
      if(data){
        this.updateRespAlert = true;        
        this.initTestimonialForm();
        this.switchAction('list',0);
        setTimeout(() => {
          this.updateRespAlert = false;					
          }, 3000);
      }else{

      }
    }, error=>{
      console.log(error);
    });
  }

  deleteTestimonial(testimonialId:number){
    this.adminService._deleteTestimonialById(testimonialId).subscribe(data=>{
      if(data){        
        this.updateRespAlertText = 'Testimonial has been deleted successfully';
        this.updateRespAlert = true;
        this.switchAction('list',0);
        setTimeout(() => {
          this.updateRespAlert = false;					
          }, 3000);
      }
    });
  }

}
