import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';

import { Router } from '@angular/router';
import "../smartprogramming/smartprogramming.component.css";
import { HttpClient } from '@angular/common/http'; 
import { FormGroup, FormBuilder } from '@angular/forms';

import { CompilecodeService } from '../../services/compilecode-service.service';
import { LoaderService } from 'src/app/services/loader.service';


export class CompileForm {
  source_code:string;
  language_id:string;
  stdin:string;
}

@Component({
  selector: 'app-smartprogramming',
  templateUrl: './smartprogramming.component.html',
  styleUrls: ['./smartprogramming.component.css']
})
export class SmartProgrammingComponent implements OnInit {
    compilerForm: FormGroup;
    saveForm : FormGroup;
    smartForm : FormGroup;

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
    levels : any ;
    topics:any;
    questions :any;
    learnerId:any;
    disableexecution :any=true;
    programDescription: any
 
    constructor(private router: Router,private http: HttpClient, private compilecodeService : CompilecodeService, private form: FormBuilder ,private loaderService: LoaderService) {
        
        this.compilerForm = this.form.group({
            language_id: [{ value: '62', disabled: false }],
            source_code: [{ value: '', disabled: false }],
            questionId: [{ value: '', disabled: false }],
            stdin: [{ value: '', disabled: false }]
          });

          this.saveForm = this.form.group({
            learnerId: this.learnerId,
            questionId: '',
            solution: '',
          });

          
          this.smartForm = this.form.group({
            levels: [''],
            topics:[''],
            questions:[''],
          });

          
      

          
    }
   

    ngOnInit() {
    this.learnerId=this.compilecodeService.getUserId();
    this.compilecodeService.gettopics().subscribe(data => {
        this.topics =data;
        
    });
    this.compilecodeService.getlevels().subscribe(data => {
        this.levels =data;
        
    });

        this.textareaValue ='';
        
        this.compilecodeService.getsavecodes().subscribe(data => {
            this.notes = data;
            this.compilecodeService.sendProgramData(data);
        });

      
    }
    ontopicChange(Value:any) {
        if(this.smartForm.value.topics !=="" && this.smartForm.value.levels !=="" ) {
            this.compilecodeService.getquestions(this.smartForm.value.topics,this.smartForm.value.levels).subscribe(data => {
                    this.questions =data;
                });

        }
        

    }
    onlevelChange(Value:any) {
        if(this.smartForm.value.topics !=="" && this.smartForm.value.levels !=="" ) {
            this.compilecodeService.getquestions(this.smartForm.value.topics,this.smartForm.value.levels).subscribe(data => {
                    this.questions =data;
                    
                });
    }
}
    reset(){

        this.compilerForm = this.form.group({
            language_id: [{ value: '62', disabled: false }],
            source_code: [{ value: '', disabled: false }],
	     questionId: [{ value: '', disabled: false }],
            stdin: [{ value: '', disabled: false }]
          });

          this.output = '';
          this.time = '';
          this.memory = '';
          this.updateid='';
          this.showButton=false;

          this.disableexecution=true;
      }
    compile(form:any){
        let code :string;
        let base  = form.source_code;
        
        code= btoa(base)
        
        this.loaderService.show();
        this.compilecodeService.getsubmissions(form,code).subscribe(
          res=>{
            let response: any = res;
            this.generateOutput(response.token , form);
      
          },
          err=>{
           
            this.output="something went wrong try again";
            this.loaderService.hide();
          }
        )
       
      }

      generateOutput(token:string , form:any){
        this.compilecodeService.getOutput(token)
        .subscribe(
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
      
            this.loaderService.hide();
            setTimeout(() => { 
            if(this.output && this.output.trim()==="Good work. Logic is perfect") {
                this.loaderService.show();
                this.saveForm = this.form.group({
                    learnerId: this.learnerId,
                    questionId:form.value.questionId,
                    solution: btoa(form.value.source_code),
                  });
                this.compilecodeService.savequestions(this.saveForm.value)
                    .pipe(first())
                    .subscribe(
                        (data:any) => {
                           
                           
                            this.loaderService.hide();
                            //this.toasterService.pop('success', 'Code Saved Successfully', '');
                            
                        },
                        (error:any) => {
                            
                        
                            this.loaderService.hide();
                            //this.toasterService.pop('error', error, '');
                        });
                        //this.reset();
            }
            else  {

               
            }
        }, 3000);
          },
          err =>{
            this.output=err;
            this.loaderService.hide();
          }
        )
      }
      

    openPrograms(item:any) {
        this.disableexecution=false;
	this.programDescription = item.programDescription;
        this.compilerForm = this.form.group({
            language_id: [{ value: '62', disabled: false }],
            source_code: [{ value: atob(item.programContent), disabled: false }],
	     questionId: [{ value: item.id, disabled: false }],
            stdin: [{ value: '', disabled: false }],
          });

          this.updateid= item.id;

          // this.saveForm.value.notes = item.note;
          // this.saveForm.value.title = item.title;
        //this.textareaValue = item.snippet;
        this.saveForm = this.form.group({
          learnerId: this.learnerId,
          questionId: item.id,
          solution: '',
          
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
                        this.notes=data;
                    },
                    error => {
                      
                    
                        this.loaderService.hide();
                    });

                }else {
                    this.loaderService.hide();

                    this.compilecodeService.updatecode(this.saveForm.value,this.updateid)
                        .pipe(first())
                        .subscribe(
                            data => {
                   
                                this.loaderService.hide();
                                this.notes=data;
                            },
                            error => {
                             
                            
                                this.loaderService.hide();
                            });
        


                }
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