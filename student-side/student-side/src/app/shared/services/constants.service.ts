import { Injectable } from '@angular/core';


/**
 * Global Constants Service provider
 */
@Injectable({
  providedIn: 'root'
})
export class ConstantsService {

  public readonly BASE_URL = 'http://localhost:8080/c2c'
  //public readonly BASE_URL = '.';
  //public readonly BASE_URL = ''
  // public readonly BASE_URL = 'https://lara.co.in/c2c/'


  public readonly
  ENDPOINT_BASE_URL: string = this._getBaseURL() + 'api';
  BASE_URL_trimmed = this._getBaseURL();
  constructor() { }

  public _getBaseURL() {
    // return 'https://d657-49-37-176-94.ngrok-free.app/c2c/'
    const baseUrl = window.location.origin;
    console.log(baseUrl)
    if (baseUrl === 'http://localhost:4200') {
      // return 'https://lara.co.in'
      return 'http://localhost:8080/c2c/'
      // return 'http://localhost:8090/c2c/'

      /* on the production app runs on port 8070 */
      return 'http://localhost:8070/c2c/'

      //return 'http://13.235.195.119:8080/c2c'
      //@dev
      //  return 'http://localhost:8080/'

    } else {
      return this.BASE_URL;
    }
  }
}
