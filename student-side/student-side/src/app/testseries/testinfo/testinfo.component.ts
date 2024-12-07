import { Component, OnInit,ElementRef, ViewChild } from '@angular/core';
import { SignupDropdownService } from 'src/app/signup-dropdown.service';
import { Router } from '@angular/router';
import { Message } from '@angular/compiler/src/i18n/i18n_ast';
import { TestService } from 'src/app/test.service';


@Component({
  selector: 'app-testinfo',
  templateUrl: './testinfo.component.html',
  styleUrls: ['./testinfo.component.css']
})
export class TestinfoComponent implements OnInit { 
  constructor(private router : Router,private service : TestService) { }
 

  isLoggedIn : boolean = false;

  data : any;

  content = '';

  clickCount = 0;
  isButtonDisabled = false;
  message : any = '';

  ngOnInit() {
  }

   start()
   {
    //  this.data   = sessionStorage.getItem('AuthUsername');
    //  console.log(this.data);
    //    if(this.data != null)
    //    {
    //      this.isLoggedIn = true;
    //    }
    //     console.log(this.isLoggedIn)

    //         if(this.isLoggedIn)
    //        {
    //         this.message = "Best Of Luck....!"

    //          setTimeout(()=>
    //          {
    //           this.router.navigate(['/usersave']);
    //          },1000);
             
             
    //       }
    //       else
    //       {
    //           this.message = "Please do login first to take assessment"

    //           setTimeout(()=>
    //           {
    //             this.message = '';
    //           },3000);
    //       }

    //     return this.isLoggedIn;

    console.log("start method")
      if(sessionStorage.getItem('AuthUsername'))
      {
        let formData = new FormData()
        formData.append('email', sessionStorage.getItem('AuthUsername'))
        this.service.userTestCheck(formData).subscribe(stat=>{
            let data:any=stat.valueOf();
            if(data['status'] == true){
              console.log(this.data);
              this.content=data['message'];
              console.log(this.isLoggedIn)
              this.isLoggedIn = true;
              this.message = "Best Of Luck....!"
                setTimeout(()=>
              {
              this.router.navigate(['/usersave']);
              },1000);  
            }
            else{
              this.isLoggedIn = false
              this.message = data['message']
              setTimeout(()=>
              {
                this.message = '';
              },3000);
            }
          })
      }
      else
      {
          this.message = "Please do login first to take assessment"
          setTimeout(()=>
          {
            this.message = '';
          },3000);
      }

        
   }

   


}
