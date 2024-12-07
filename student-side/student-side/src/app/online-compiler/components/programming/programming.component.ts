import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';
import { Router } from '@angular/router';



import { HttpClient } from '@angular/common/http'; 

import { LoaderService } from 'src/app/common-service/loader.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { AuthService } from 'src/app/auth/auth.service';
import { FormGroup, FormBuilder } from '@angular/forms';
//import { NgxUiLoaderService } from 'ngx-ui-loader';
//import {ToasterModule, ToasterService} from 'angular2-toaster';
import { CompilecodeService } from '../../services/compilecode-service.service';

export class CompileForm {
  source_code:string;
  language_id:string;
  stdin:string;
}

@Component({
  selector: 'app-programming',
  templateUrl: './programming.component.html',
  styleUrls: ['./programming.component.css']
})
export class ProgrammingComponent implements OnInit {
    learnerId:any; 
    compilerForm: FormGroup;
    saveForm : FormGroup;
    title : any;
    loading = false;
    textareaValue : any;
    showButton =false;
    notes : any;
    output:string = '';
    memory:string= '';
    time:string= '';
    date : Date;
    updateid : any = '';
    //private toasterService: ToasterService;

    constructor(private router: Router,private http: HttpClient,
        private compilecodeService : CompilecodeService, private form: FormBuilder ,private loaderService: LoaderService) {
          	this.loadPrograms();
	  //this.toasterService = toasterService;
        this.compilerForm = this.form.group({
            language_id: [{ value: '62', disabled: false }],
            source_code: [{ value: '\
            public class Main {\n\
                public static void main(String[] args) {\n\
                    System.out.println(\"hello, world\");\n\
                }\n\
            }\n', disabled: false }],
            stdin: [{ value: '', disabled: false }]
          });

          this.saveForm = this.form.group({
            learnerId: this.learnerId,
            program: '',
            notes: '',
            title: '',
          });

          
    }
   

    ngOnInit() {
       
        this.textareaValue ='';
        this.learnerId=this.compilecodeService.getUserId();
        this.compilecodeService.getsavecodes().subscribe(data => {
            this.notes = data;
            this.compilecodeService.sendProgramData(data);
        });


    }
    reset(){
        //form.reset();

        this.compilerForm = this.form.group({
            language_id: [{ value: '62', disabled: false }],
            source_code: [{ value: '\
            public class Main {\n\
                public static void main(String[] args) {\n\
                      System.out.println(\"hello, world\");\n\
                }\n\
            }\n', disabled: false }],
            stdin: [{ value: '', disabled: false }]
          });

          this.output = '';
          this.time = '';
          this.memory = '';
          this.updateid='';
          this.showButton=false;
          this.saveForm = this.form.group({
            learnerId: this.learnerId,
            program: '',
            notes: '',
            title: '',
          });

        
      }
    compile(form:any){
      
        let code :string;
        let base  = form.source_code;
        
        code= btoa(base)
        
        //this.loaderService.show();
        this.compilecodeService.getsubmissions(form,code).subscribe(
          res=>{
            let response: any = res;
	    this.generateOutput(response.token , form);
	    this.loaderService.hide();
          },
          err=>{
            console.log(err);
            this.output="something went wrong try again";
            this.loaderService.hide();
          }
        );

      }

      generateOutput(token:string , form:any){
        this.compilecodeService.getOutput(token).subscribe(
          res=>{

            let response: any = res;
            this.showButton =true;
            if(response.status.id===1 ||  response.status.id===2){
              this.generateOutput(token , form);
            }
      
            if(response.status.id===3)
            {
              this.output = atob(response.stdout);
              this.time = response.time;
              this.memory = response.memory;
            }
            else if (response.status.id===6){
              this.output = atob(response.compile_output);
               this.time = response.time;
              this.memory = response.memory;
            }
            else{
              this.output = atob(response.stderr);
              this.time = response.time;
              this.memory = response.memory;
            }
      
            //this.loaderService.show();
          },
          err =>{
            this.output="some error";
	    //console.log("error:" + response.status.id);
            this.loaderService.hide();
          }
        )
      }
      

    openPrograms(item:any) {

        this.compilerForm = this.form.group({
            language_id: [{ value: '62', disabled: false }],
            source_code: [{ value: atob(item.program), disabled: false }],
            stdin: [{ value: '', disabled: false }],
          });

          this.updateid= item.id;

        this.saveForm = this.form.group({
          learnerId: this.learnerId,
          program: '',
          notes: item.note,
          title: item.title,
        });
        this.showButton=false;
    }
   
    saveFormValues(saveform:any) {
        
        this.saveForm = this.form.group({
            learnerId: this.learnerId,
            program: btoa(this.compilerForm.value.source_code),
            notes: saveform.notes,
            title: saveform.title,
          });

         
  
          if(this.updateid=='') { 
          this.loaderService.show();
            this.compilecodeService.savecode(this.saveForm.value)
                .pipe(first())
                .subscribe(
                    data => {
                        
                        this.loaderService.hide();
                        //this.toasterService.pop('success', 'Code Saved Successfully', '');
                        this.notes=data;
                    },
                    error => {
                       
                       
                    
                        this.loaderService.hide();
                        //this.toasterService.pop('error', error, '');
                    });

                }else {
                    this.loaderService.show();

                    this.compilecodeService.updatecode(this.saveForm.value,this.updateid)
                        .pipe(first())
                        .subscribe(
                            data => {
                             
                                this.loaderService.show();
                                //this.toasterService.pop('success', 'Updated Successfully', '');
                                this.notes=data;
                            },
                            error => {
                                //this.toasterService.pop('error', error, '');
                            
                                this.loaderService.hide();
                            });
        


                }
		this.loadPrograms();
	}
	loadPrograms()
	{
		//alert("from loadPrograms");
	             this.compilecodeService.getProgramData().subscribe((data) => {
                    try {
                      if (data) {
                        this.notes = data;
                      }
              
                    } catch (e) {
              
                    }
                  }, err => {
              
                  })
	}




}