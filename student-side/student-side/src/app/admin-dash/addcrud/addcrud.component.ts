import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TestService } from 'src/app/test.service';

@Component({
  selector: 'app-addcrud',
  templateUrl: './addcrud.component.html',
  styleUrls: ['./addcrud.component.css']
})
export class AddcrudComponent{

  questionTextToggle = true;
  optionOneToggle = true;
  optionTwoToggle = true;
  optionThreeToggle = true;
  optionFourToggle = true;
  optionFiveToggle=true;
  correctAnswerOneToggle=true;
  correctAnswerTwoToggle=true;
  descriptionToggle=true;
  
  sortBy = "questionText";
  orderBy = "asc";
//for main table
  pageData:any;
  pageTabs= 0;

  msgOk = false;
  msgDanger = false;
  content = "";

  searchForm: FormGroup;
  searchByForm: FormGroup;
  pageSizeForm: FormGroup;
  updateForm:FormGroup

  fetchQuestionList:any=[]
  fetchList:Array<any> = []
  fetchListSecondary:Array<any> = []
  selectedCorrectOptions: string[] = [];
  message : any;

  constructor(private fetchService: TestService,private formBuilder:FormBuilder) {
    this.searchForm = formBuilder.group({
      search: new FormControl('', Validators.required)
    })
    
    this.searchByForm = formBuilder.group({
      searchBy: new FormControl('', Validators.required)
    })
    this.pageSizeForm = formBuilder.group({
      pageSize: new FormControl('',[ Validators.min(5), Validators.max(15)])
    })
    this.updateForm=formBuilder.group({
       id:new FormControl('',Validators.required),
       questionText:new FormControl('',Validators.required),
       optionOne:new FormControl('',Validators.required),
       optionTwo:new FormControl('',Validators.required),
       optionThree:new FormControl('',Validators.required),
       optionFour:new FormControl('',Validators.required),
       optionFive:new FormControl('',Validators.required),
       correctAnswer:new FormControl('',Validators.required),
      //  correctAnswerTwo:new FormControl('',Validators.required),
       description:new FormControl('',Validators.required)
    })

    //intial fetching
    this.getPageData(null ,0, 5);
  }

  //****************************Searching******************************************* */
   //to get default sort and paging
   getPageData(e: any, pageNumber: number, pageSize: number){
    //check if search is on
    // if (this.searchForm.get('search')?.value !== "") {
    //   this.search(null, pageNumber, pageSize)
    // }
    const searchControl = this.searchForm.get('search');
    if (searchControl && searchControl.value !== "") {
      this.search(null, pageNumber, pageSize)
    }

    let formData = new FormData();
      formData.append('search', this.searchForm.value['search']);
    formData.append('searchBy', this.searchByForm.value['searchBy']);
    formData.append("pageNo", pageNumber + "");
    formData.append("pageSize", pageSize + "");
    formData.append("sortBy", this.sortBy);
    formData.append("orderBy", this.orderBy);
    // console.log(formData.)

    this.fetchService.getFetchedDefualtQuestions(formData).subscribe(
      stat => {
        let data:any = stat.valueOf();
        console.log(data)
        if (data['status'] == true){
          // this.showFlags('ok', data['message'])
          this.pageData = data;
          this.fetchQuestionList = data['fetchQuestionList']
          this.deselectAll(null)
          if (this.pageData.number - this.pageTabs == 3) this.pageTabs++;
          if (this.pageTabs - this.pageData.number == 1) this.pageTabs--;
          console.log(pageNumber, pageSize,this.pageTabs)
        }
        else{
          this.showFlags('danger', data['message'])
        }
      }
    )
    if (e!=null) e.preventDefault();
  }
  
    //search
    search(e: any, pageNumber: number, pageSize: number){

    }
    
    getCorrectAnswerOptionTexts(question: any): string {
      const correctAnswerOptionTexts: string[] = [];
    
      for (const option of question.option) {
        // console.log(option);
        for (const correctAnswer of option.correctAnswer) {
          // console.log(correctAnswer);
          if (correctAnswer.option === option.id && correctAnswer.question===option.question) {
            correctAnswerOptionTexts.push(option.optionText);
          }
        }
      }
      return correctAnswerOptionTexts.join(', ');
    }
      
  deleteRecord(index:number){
    this.fetchQuestionList.splice(index);
    console.log(index);
    let formdata=new FormData();
    formdata.append('id',index + "");
    console.log(formdata);
    this.fetchService.deleteRecord(formdata).subscribe(stat=>{
       let data:any=stat.valueOf();
       console.log(data);
       if(data['status']==true){
         this.content=data['message']
         this.msgOk=true;
       }
       else{
        this.content=data['message']
        this.msgDanger=true;
       }
    })
  }

    update(index: number) {
    const data = this.fetchQuestionList[index];
    console.log("mak 1 : " + data);
    console.log("mak 2 : " + this.fetchQuestionList[index]);
    console.log(this.fetchQuestionList);
    
    // this.updateForm.patchValue({
    //   id: data.id,
    //   questionText:data.questionText,
    //   optionOne:data.option[0]?.optionText,
    //   optionTwo:data.option[1]?.optionText,
    //   optionThree:data.option[2]?.optionText,
    //   optionFour:data.option[3]?.optionText,
    //   correctAnswer: this.getCorrectAnswerText(data.id),
    //   description:data.description.descriptionText
    // })
    let optionOne = '';
    if (data.option.length > 0) {
      optionOne = data.option[0].optionText || '';
    }

    let optionTwo = '';
    if (data.option.length > 1) {
      optionTwo = data.option[1].optionText || '';
    }

    let optionThree = '';
    if (data.option.length > 2) {
      optionThree = data.option[2].optionText || '';
    }

    let optionFour = '';
    if (data.option.length > 3) {
      optionFour = data.option[3].optionText || '';
    }

    let optionFive = '';
    if (data.option.length > 4) {
      optionFive = data.option[4].optionText || '';
    }
    let descriptionText = '';
    if (data.description && data.description.descriptionText) {
      descriptionText = data.description.descriptionText;
    }

    this.updateForm.patchValue({
      id: data.id || '',
      questionText: data.questionText || '',
      optionOne: optionOne,
      optionTwo: optionTwo,
      optionThree: optionThree,
      optionFour: optionFour,
      optionFive: optionFive,
      correctAnswer: this.getCorrectAnswerText(data.id) || '',
      description: descriptionText
    });

    console.log(this.getCorrectAnswerText(data.id));
    console.log(this.updateForm.value);
  }

  getCorrectAnswerText(questionId: number) {
    // console.log(questionId); 
    const question = this.fetchQuestionList.find((q:any) => q.id === questionId);
    // console.log(question);
    const correctAnswerOptionTexts: string[] = [];
    
      for (const option of question.option) {
        // console.log(option);
        for (const correctAnswer of option.correctAnswer) {
          console.log(correctAnswer);
          if (correctAnswer.option === option.id && correctAnswer.question===option.question) {
            correctAnswerOptionTexts.push(option.optionText);
            // console.log(correctAnswerOptionTexts.push(option.optionText));
          }
        }
      }
      return correctAnswerOptionTexts.join(', ');
  }

 
  
  
  submit(option: any){
    console.log(this.updateForm.value);
    console.log(option)
    // var correctAns: Array<string> = this.updateForm.get('correctAnswer')?.value.split(",").filter((arg: any) => arg !== "")
    const correctAnswerControl = this.updateForm.get('correctAnswer');
    if (correctAnswerControl) {
      const correctAnswerValue = correctAnswerControl.value || '';
      const correctAns: Array<string> = correctAnswerValue.split(',').filter((arg: any) => arg !== '');
        console.log(correctAns);

      var correctIds = option.filter((arg1:any) => {
        console.log(arg1.optionText)
        return correctAns.find((arg:any) => {
          console.log(arg)
          return arg === arg1.optionText
        })
      })
    }
    var correct: Array<string>  = [] 
    correctIds.forEach((arg: any) => correct.push(arg.id as string))
    console.log(correctIds) 
    console.log(typeof correct.join(','))
    console.log(correct.join(','))
    // this.updateForm.get('correctAnswer')?.setValue(correct.join(","))
    const correctAnswerControlOne = this.updateForm.get('correctAnswer');
    if (correctAnswerControlOne) {
      correctAnswerControlOne.setValue(correct.join(','));
    }
    console.log(this.updateForm.value)

    this.fetchService.updateQuestion(this.updateForm.value).subscribe(stat => {
      let data:any=stat.valueOf();
      console.log(data);
      if(data['status']==true){
        this.content=data['message']
        this.msgOk=true;
      }
      else{
        this.content=data['message']
        this.msgDanger=true;
      }
    })
    this.refresh()
  }
  
  refresh()
  {
    if (this.searchForm.get('search').value !== "") {
      console.log("from fn search")
      this.search(null, this.pageData.number, this.pageData.size)
    }
    this.getPageData(null, this.pageData.number, this.pageData.size);
  }
  
  //***********************************Events*********************************************** */


  onCheck(e: any, user: any, by: string){
    if (by === 'primary') {
      if (!e.target.checked) this.fetchList.splice(this.fetchList.indexOf(user),1)
      else this.fetchList.push(user)
    }
    else if (by === 'secondary'){
      if (!e.target.checked) this.fetchListSecondary.splice(
        this.fetchListSecondary.findIndex(o => o.email === user.email),1)
      else this.fetchListSecondary.push(user)
    }
    console.log(this.fetchList)
  }

  // selectAll(e: any){
  //   this.fetchList.splice(0)
  //   for (let i = 0 ; i < this.fetchQuestionList.length ; i++){
  //     this.fetchQuestionList[i].isSelected = true;
  //   }   
  //   this.fetchQuestionList.forEach((questionText) => this.fetchList.push(questionText))
  //   console.log(this.fetchList)
  //   if (e!=null) e.preventDefault();
  // }

  deselectAll(e: any){
    for (let i = 0 ; i < this.fetchQuestionList.length ; i++){
      this.fetchQuestionList[i].isSelected = false;
    }
    this.fetchList.splice(0)
    console.log(this.fetchList)
    if (e!=null) e.preventDefault();
  }

  //************************************Sorting Togglers**********************************/
  questionTextToggler(e: Event){
    //changing view 
    if (this.questionTextToggle) this.questionTextToggle = false;
    else this.questionTextToggle = true;

    //asigning sortBy field
    this.sortBy = "questionText"

    //changing order
    if (this.orderBy === "asc") this.orderBy = "desc"
    else this.orderBy = "asc"

   //check if search is on
    if (this.searchForm.get('search').value !== "") {
      console.log("from fn search")
      this.search(null, this.pageData.number, this.pageData.size)
    }
    this.getPageData(null, this.pageData.number, this.pageData.size);

    console.log(this.sortBy, this.orderBy)
    e.preventDefault();
  }

  optionOneToggler(e: Event){
    //changing view 
    if (this.optionOneToggle) this.optionOneToggle = false;
    else this.optionOneToggle = true;

    //asigning sortBy field
    this.sortBy = "optionOne"

    //changing order
    if (this.orderBy === "asc") this.orderBy = "desc"
    else this.orderBy = "asc"

   //check if search is on
    // if (this.searchForm.get('search')?.value !== "") {
    //   console.log("from fn search")
    //   this.search(null, this.pageData?.number, this.pageData?.size)
    // }
    const searchControl = this.searchForm.get('search');
    if (searchControl && searchControl.value !== "") {
      console.log("from fn search");
      this.search(null, this.pageData && this.pageData.number, this.pageData && this.pageData.size);
    }
    this.getPageData(null, this.pageData.number, this.pageData.size);
    

    console.log(this.sortBy, this.orderBy)
    e.preventDefault();
  }

  optionTwoToggler(e: Event){
    //changing view 
    if (this.optionTwoToggle) this.optionTwoToggle = false;
    else this.optionTwoToggle = true;

    //asigning sortBy field
    this.sortBy = "optionTwo"

    //changing order
    if (this.orderBy === "asc") this.orderBy = "desc"
    else this.orderBy = "asc"

   //check if search is on
    // if (this.searchForm.get('search')?.value !== "") {
    //   console.log("from fn search")
    //   this.search(null, this.pageData?.number, this.pageData?.size)
    // }
    const searchControl = this.searchForm.get('search');
    if (searchControl && searchControl.value !== "") {
      console.log("from fn search");
      this.search(null, this.pageData && this.pageData.number, this.pageData && this.pageData.size);
    }
    this.getPageData(null, this.pageData.number, this.pageData.size);
    

    console.log(this.sortBy, this.orderBy)
    e.preventDefault();
  }

  optionThreeToggler(e: Event){
    //changing view 
    if (this.optionThreeToggle) this.optionThreeToggle = false;
    else this.optionOneToggle = true;

    //asigning sortBy field
    this.sortBy = "optionThree"

    //changing order
    if (this.orderBy === "asc") this.orderBy = "desc"
    else this.orderBy = "asc"

   //check if search is on
    // if (this.searchForm.get('search')?.value !== "") {
    //   console.log("from fn search")
    //   this.search(null, this.pageData?.number, this.pageData?.size)
    // }
    const searchControl = this.searchForm.get('search');
    if (searchControl && searchControl.value !== "") {
      console.log("from fn search");
      this.search(null, this.pageData && this.pageData.number, this.pageData && this.pageData.size);
    }
    this.getPageData(null, this.pageData.number, this.pageData.size);
    

    console.log(this.sortBy, this.orderBy)
    e.preventDefault();
  }


  optionFourToggler(e: Event){
    //changing view 
    if (this.optionFourToggle) this.optionFourToggle = false;
    else this.optionOneToggle = true;

    //asigning sortBy field
    this.sortBy = "optionFour"

    //changing order
    if (this.orderBy === "asc") this.orderBy = "desc"
    else this.orderBy = "asc"

   //check if search is on
    // if (this.searchForm.get('search')?.value !== "") {
    //   console.log("from fn search")
    //   this.search(null, this.pageData?.number, this.pageData?.size)
    // }
    const searchControl = this.searchForm.get('search');
    if (searchControl && searchControl.value !== "") {
      console.log("from fn search");
      this.search(null, this.pageData && this.pageData.number, this.pageData && this.pageData.size);
    }
    this.getPageData(null, this.pageData.number, this.pageData.size);
    

    console.log(this.sortBy, this.orderBy)
    e.preventDefault();
  }

  optionFiveToggler(e: Event){
    //changing view 
    if (this.optionFiveToggle) this.optionFiveToggle = false;
    else this.optionOneToggle = true;

    //asigning sortBy field
    this.sortBy = "optionFive"

    //changing order
    if (this.orderBy === "asc") this.orderBy = "desc"
    else this.orderBy = "asc"

   //check if search is on
    // if (this.searchForm.get('search')?.value !== "") {
    //   console.log("from fn search")
    //   this.search(null, this.pageData?.number, this.pageData?.size)
    // }
    const searchControl = this.searchForm.get('search');
    if (searchControl && searchControl.value !== "") {
      console.log("from fn search");
      this.search(null, this.pageData && this.pageData.number, this.pageData && this.pageData.size);
    }
    this.getPageData(null, this.pageData.number, this.pageData.size);
    

    console.log(this.sortBy, this.orderBy)
    e.preventDefault();
  }


  correctAnswerOneToggler(e: Event){
    //changing view 
    if (this.correctAnswerOneToggle) this.correctAnswerOneToggle = false;
    else this.correctAnswerOneToggle = true;

    //asigning sortBy field
    this.sortBy = "correctAnswerOne"

    //changing order
    if (this.orderBy === "asc") this.orderBy = "desc"
    else this.orderBy = "asc"

   //check if search is on
    // if (this.searchForm.get('search')?.value !== "") {
    //   console.log("from fn search")
    //   this.search(null, this.pageData?.number, this.pageData?.size)
    // }
    const searchControl = this.searchForm.get('search');
    if (searchControl && searchControl.value !== "") {
      console.log("from fn search");
      this.search(null, this.pageData && this.pageData.number, this.pageData && this.pageData.size);
    }
    this.getPageData(null, this.pageData.number, this.pageData.size);
    
    
    console.log(this.sortBy, this.orderBy)
    e.preventDefault();
  }

  correctAnswerTwoToggler(e: Event){
    //changing view 
    if (this.correctAnswerTwoToggle) this.correctAnswerTwoToggle = false;
    else this.correctAnswerTwoToggle = true;

    //asigning sortBy field
    this.sortBy = "correctAnswerTwo"

    //changing order
    if (this.orderBy === "asc") this.orderBy = "desc"
    else this.orderBy = "asc"

   //check if search is on
    // if (this.searchForm.get('search')?.value !== "") {
    //   console.log("from fn search")
    //   this.search(null, this.pageData?.number, this.pageData?.size)
    // }
    const searchControl = this.searchForm.get('search');
    if (searchControl && searchControl.value !== "") {
      console.log("from fn search");
      this.search(null, this.pageData && this.pageData.number, this.pageData && this.pageData.size);
    }
    this.getPageData(null, this.pageData.number, this.pageData.size);
    
    
    console.log(this.sortBy, this.orderBy)
    e.preventDefault();
  }

  descriptionToggler(e: Event){
    //changing view 
    if (this.descriptionToggle) this.descriptionToggle = false;
    else this.descriptionToggle = true;

    //asigning sortBy field
    this.sortBy = "description"

    //changing order
    if (this.orderBy === "asc") this.orderBy = "desc"
    else this.orderBy = "asc"

   //check if search is on
    // if (this.searchForm.get('search')?.value !== "") {
    //   console.log("from fn search")
    //   this.search(null, this.pageData?.number, this.pageData?.size)
    // }
    const searchControl = this.searchForm.get('search');
    if (searchControl && searchControl.value !== "") {
      console.log("from fn search");
      this.search(null, this.pageData && this.pageData.number, this.pageData && this.pageData.size);
    }
    this.getPageData(null, this.pageData.number, this.pageData.size);
    
    
    console.log(this.sortBy, this.orderBy)
    e.preventDefault();
  }

  
  //set the page size
  applySize(e: Event){
    //check the condition if met then apply
    // if (this.pageSizeForm.get('pageSize')?.value>=5 && this.pageSizeForm.get('pageSize')?.value<=this.pageData.totalElements){
    //   console.log(this.pageSizeForm.get('pageSize')?.value)
    //   this.pageData.size = Number(this.pageSizeForm.get('pageSize')?.value)
    //   this.getPageData(null, this.pageData.number, this.pageData.size)
    // }
    const pageSizeControl = this.pageSizeForm.get('pageSize');
    if (pageSizeControl && pageSizeControl.value >= 5 && pageSizeControl.value <= this.pageData.totalElements) {
      console.log(pageSizeControl.value);
      this.pageData.size = Number(pageSizeControl.value);
      this.getPageData(null, this.pageData.number, this.pageData.size);
    } 
    e.preventDefault();
  }
//************************************************************************************** */

//*******************************Utility Methods**************************************** */
  showFlags(by: string, content: string){
    this.msgDanger = false;
    this.msgOk = false;
    if (by == 'ok'){
      this.msgOk = true;
      this.content = content
    }
    else{
      this.msgDanger = true;
      this.content = content;
    }
    this.resetFlags()
  }
  resetFlags(){
    setTimeout(() => {
      this.msgDanger = false;
      this.msgOk = false;
    }, 10000);
  }

}
