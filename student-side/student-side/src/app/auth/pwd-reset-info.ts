export class PwdResetInfo {
    email: string;
    userId: string;
    password: string;
    cPassword: string;
    activationCode: string;
    currentPassword: string;
    newPassword: string;
   
    constructor(email: string, userId: string, password: string, cPassword: string, activationCode: string,
        currentPassword:string, newPassword:string){
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.cPassword = cPassword;
        this.activationCode = activationCode;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}