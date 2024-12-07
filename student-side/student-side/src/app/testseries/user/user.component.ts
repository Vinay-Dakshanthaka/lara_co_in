import { Component ,OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Subject, Subscription, interval, } from 'rxjs';
import { TestService } from '../../test.service';
import { Router } from '@angular/router';

interface MultipleOptions {
  id: number;
  // text: string;
  // questionId:number;
  // option:number;
}

interface Options{
   id:number
   correctAnswers:Array<any>,
   optionText:string;
   questionId:number;
  //  option:number;
}
interface UserAnswer {
  questionId: number;
  selectedOptions: MultipleOptions[]; 
}
interface Question {
  id: number;
  questionText: string;
  option: Array<Options>;
  correctAnswers: Array<MultipleOptions>;
  description:Array<any>
  // ... other properties ...
}
@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent{
  UserForm:FormGroup;
  fetchList:Array<Question>= [];
  currentQuestionIndex = 0; 
  userAnswers: UserAnswer[] = [];
  totalCorrectAnswers = 0; 
  totalQuestions=0;
  correctAnswers: boolean[] = [];
  timerExpired : boolean = false;
  // timer:number=0;
  timerSubscription: Subscription | undefined;
  private timer$ :Subject<void> = new Subject<void>();
  questionTimer=30;
  content=''
  msgOk=false;
  msgDanger=false;
  hideUserForm=false;
  questionblock=false;
  showResults:boolean=false;
  showSummary: boolean = false;
  userTest:any=[]
  currentQuestion: any = {}; // Initialize with your question data
  selectedOptions: Map<number, { id: number, text: string }[]> = new Map<number, { id: number, text: string }[]>();

  remainingTime: number =  30*60; 
  initialColor = 'green';
  useremail : any;


  constructor(private fetchService: TestService,private router : Router) {
  //   this.UserForm=formBuilder.group({
  //     firstName: new FormControl('',[Validators.required, Validators.minLength(3)]),
  //     lastName: new  FormControl('',[Validators.required, Validators.minLength(3)]),
  //     email: new FormControl('',Validators.compose([Validators.required,
  //       Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$')
  //       ])),
  //     phoneNumber:new FormControl('', [Validators.required, Validators.minLength(10),Validators.pattern('[- +()0-9]{10,12}')]),
  //     chooseQuestions:new FormControl('', [Validators.required,Validators.minLength(1)]),
  //  })
  //    this.timerSubscription = new Subscription();
  this.fetchService.getfetch().subscribe(stat => {
    let data: any = stat.valueOf();
    console.log(data);
    this.fetchList = data;
    console.log(this.fetchList);
    this.setCurrentQuestion();
    // this.startTimer();
    console.log("timer 1");
    
  }); 
}

//   usersubmit(){
//     console.log(this.UserForm.value);
//     this.fetchService.userSave(this.UserForm.value).subscribe(stat=>{
//      let data:any=stat.valueOf();
//      if(data['status']==true){
//        this.msgOk=true;
//        this.hideUserForm=true;
//        this.fetchList=data['questionList']
//        console.log(this.fetchList);
//         this.content=data['message']
//         this.setCurrentQuestion(); 
//      }
//      else{
//       this.hideUserForm=false;
//        this.msgDanger=true;
//        this.content=data['message']
//      }
//     })
//  }

ngOnInit(): void {
  // this.setCurrentQuestion();
  this.startTimer();
}

  // ngOnDestroy(): void {
  //   this.timer$.next();
  //   this.timer$.complete();
  //   if (this.timerSubscription) {
  //     this.timerSubscription.unsubscribe();
  //   }
  // }

  setCurrentQuestion() {
    console.log(this.currentQuestionIndex);
    this.currentQuestion = this.fetchList[this.currentQuestionIndex];
    // this.resetTimer();
    // this.timerExpired = false;
    console.log(this.currentQuestion);
    console.log(this.currentQuestion.questionText);
    // console.log("timer 2");
  }

  prevQuestion() {
    if (this.currentQuestionIndex > 0) {
      this.currentQuestionIndex--;
      this.timerExpired = false; 
      if (this.timerSubscription) {
        this.timerSubscription.unsubscribe();
      }
      // this.resetTimer();
      this.setCurrentQuestion();
      // this.startTimer(); 
    }
  }

  nextQuestion() {
    if (this.currentQuestionIndex < this.fetchList.length - 1) {
      this.currentQuestionIndex++;
      this.totalQuestions++;
      this.setCurrentQuestion();
      this.resetTimer();
      // this.startTimer();
    }
  }
  
  // startTimer() {
  //   this.questionTimer=30;
  //   this.timerSubscription = interval(1000)
  //     .pipe(takeUntil(this.timer$))
  //     .subscribe(() => {
  //       if (this.questionTimer > 0) {
  //         this.questionTimer--;
  //       } else {
  //         this.timerExpired = true;
  //         if (this.timerSubscription) {
  //           this.timerSubscription.unsubscribe();
  //         }
  //         this.nextQuestion();
  //       }
  //     });
  // }

  resetTimer() {
    this.questionTimer = 30;
  }
  
  isLastQuestion(): boolean {
    return this.currentQuestionIndex === this.fetchList.length - 1;
  }
 
  toggleAnswer(option: any, questionId: number) {
    const selectedOptions = this.selectedOptions.get(questionId) || [];
    const optionIndex = selectedOptions.findIndex(selectedOption => selectedOption.id === option.id);
    // console.log(selectedOptions);
    console.log(optionIndex);
  
    if (optionIndex === -1) {
      selectedOptions.push({ id: option.id, text: option.optionText });
    } else {
      selectedOptions.splice(optionIndex, 1);
    }

    this.selectedOptions.set(questionId, selectedOptions);
    console.log(selectedOptions);
  }


  isOptionSelected(option: any, questionId: number): boolean {
    const selectedOptions = this.selectedOptions.get(questionId)||[];
    if (selectedOptions) {
      selectedOptions
      return selectedOptions.some(selectedOption => selectedOption.id === option.id);
    }
    return false;
  }
  

  getCorrectAnswer(option: any){
    if (option.correctAnswer.length) return option.optionText
    return "";
  }


  evaluateAnswers() {
    console.log(this.selectedOptions);

   this.useremail=  sessionStorage.getItem('email');
   console.log(this.useremail);
    //  console.log(this.userAnswers);
    //  const email = this.UserForm.get('email')?.value;
    //  let email = '';
    // const emailControl = this.UserForm.get('email');
    // if (emailControl) {
    //   email = emailControl.value;
    // }
    const answersToSend:any[] = [];

    this.selectedOptions.forEach((options, questionId) => {
      const questionAnswers = options.map(option => {
        return {
          optionId: option.id,
          optionText: option.text
        };
      });
      answersToSend.push({
        questionId: questionId,
        selectedOptions: questionAnswers
      });
    });

    const dataToSend = {
      email: this.useremail,
      userAnswers: answersToSend
    };
    console.log(dataToSend);
     this.fetchService.testevaluate(dataToSend).subscribe(stat=>{
       let data:any=stat.valueOf();
       console.log("value : " + stat);
       
       if(data['status']==true){
        this.questionblock=true;
        this.userTest=data['userTestDetails']
        console.log(this.userTest);
        //  this.content=data['message']
         this.msgOk=true;
         this.showResults=true;
       }
       else{
        this.questionblock=true;
        //  this.content=data['message']
         this.msgDanger=true;
         this.showResults=true;
       }
     })
  }

// Method to toggle the summary display
 toggleSummary() {
    this.showSummary = !this.showSummary;
  }

  startTimer() {
    const timerInterval = setInterval(() => {
      if (this.remainingTime <= 0) {
        clearInterval(timerInterval);
        this.timerExpired = true;
        this.onTimerExpired();
        this.redirectToSummary(); // Redirect to the summary page
      } else {
        this.remainingTime--;
      }
    }, 1000); // Update every 1 second (1000 milliseconds)
  }

  onTimerExpired() {
   
    const timeRemainingElement = document.getElementById('timeRemaining');
    if (timeRemainingElement) {
      timeRemainingElement.classList.add('time-over');
    }



    setTimeout(()=>
    {
      this.evaluateAnswers();
    },1000);
  }
  

  formatTime(seconds: number): string {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes}:${remainingSeconds < 10 ? '0' : ''}${remainingSeconds}`;
  }
  

  redirectToSummary() {
    this.router.navigate(['/summary']); // Redirect to your summary page route
  }

 
  getAnswerResultClass(question: Question): string {
    const selectedOptions = this.selectedOptions.get(question.id) || [];
    const correctOptionIds = question.option
      .filter((option: any) => option.correctAnswer.length > 0)
      .map((option: any) => option.id);
  
    const selectedOptionIds = selectedOptions.map(option => option.id);
    const allCorrect = this.arrayEquals(correctOptionIds, selectedOptionIds);
  
    if (allCorrect) {
      return 'correct-answer';
    } else {
      return 'wrong-answer';
    }
  }

  getAnswerResultText(question: Question): string {
    const selectedOptions = this.selectedOptions.get(question.id) || [];
    const correctOptionIds = question.option
      .filter((option: any) => option.correctAnswer.length > 0)
      .map((option: any) => option.id);
  
    const selectedOptionIds = selectedOptions.map(option => option.id);
    const allCorrect = this.arrayEquals(correctOptionIds, selectedOptionIds);
  
    if (allCorrect) {
      return 'Correct';
    } else {
      return 'Wrong';
    }
  }
  
  arrayEquals(array1: any[], array2: any[]): boolean {
    if (array1.length !== array2.length) {
      return false;
    }
  
    return array1.every((value, index) => value === array2[index]);
  }
  
  // getPercentage() {
  //   return (this.totalCorrectAnswers / this.totalQuestions) * 100;
  // }

  // isEligible() {
  //   return this.getPercentage() >= 40;
  // }


  //****************************** Getters *************************888 */
    get firstName(){
      return this.UserForm.get('firstName')
    }
    get lastName(){
      return this.UserForm.get('lastName')
    }
    get email(){
      return this.UserForm.get('email')
    }
    get phoneNumber(){
      return this.UserForm.get('phoneNumber')
    }
    get chooseQuestions(){
      return this.UserForm.get('chooseQuestions')
    }

    get selectedOption()
    {
      return this.selectedOptions;
    }
}



