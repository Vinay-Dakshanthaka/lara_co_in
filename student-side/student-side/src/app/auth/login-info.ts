export class AuthLoginInfo {
    email: string;
    password: string;
    otp:string

    constructor(email: string, password: string, otp: string) {
        this.email = email;
        this.password = password;
        this.otp = otp;
    }
}
