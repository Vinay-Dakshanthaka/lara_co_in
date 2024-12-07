export class SignUpInfo {
    firstName: string;   
    email: string;
    role: string[];
    //mobileNo: string;
    password: string;


    //constructor(firstName: string, email: string, mobileNo:string, password: string) {
    constructor(firstName: string, email: string, password: string) {
        this.firstName = firstName;
        this.email = email;
        //this.mobileNo = mobileNo;
        this.password = password;        
        this.role = ['learner'];
    } 
}
