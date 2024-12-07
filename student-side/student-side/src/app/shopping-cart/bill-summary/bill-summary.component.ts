import { Component, ElementRef, OnInit, Output, ViewChild, EventEmitter } from '@angular/core';
import { CartServiceService } from '../cart-service.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { UserProfileService } from 'src/app/dashboard/services/user-profile.service';
import { CourseBuyoutService } from '../service/course-buyout.service';
import { SHA256 } from 'crypto-js'
import { OrdersService } from '../service/orders.service';
import { doesNotThrow } from 'assert';
import { LoaderService } from 'src/app/common-service/loader.service';
import { PhonePeParamsService } from '../service/phone-pe-params.service';
import { ToastService } from 'src/app/toast/toast.service';
import { JsonPipe } from '@angular/common';
import { ThrowStmt } from '@angular/compiler';

@Component({
    selector: 'app-bill-summary',
    templateUrl: './bill-summary.component.html',
    styleUrls: ['./bill-summary.component.css']
})
export class BillSummaryComponent implements OnInit {

    @Output() orderCompleteEvent = new EventEmitter()

    GST = 18
    userPackages = []

    constructor(
        private cartService: CartServiceService,
        private tokenService: TokenStorageService,
        private courseBuyout: CourseBuyoutService,
        private orderService: OrdersService,
        private loaderService: LoaderService,
        private phonePeParamsService: PhonePeParamsService,
        private toastService: ToastService
    ) { }

    ngOnInit() {
        this.getUserCart()
    }

    getUserCart() {
        let formData = new FormData()
        formData.append('userId', this.tokenService.getUserId())
        this.cartService.getUserCart(formData).subscribe(stat => {
            this.userPackages = stat as Array<any>
            // console.log(this.userPackages)
        })
    }

    get getGst() {
        return ((this.GST / 100) * this.getSubTotal)
    }

    get getSubTotal() {
        return this.userPackages.reduce((total, coursePackage) => coursePackage.coursePackagePrice + total, 0)
    }

    get getGrandTotal() {
        const subTotal = this.getSubTotal
        return Math.round(subTotal + this.getGst)
    }

    getIndianCurrency(amount: number) {
        return new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(amount)
    }

    async checkout(e: Event) {
        // let formData = new FormData()
        this.loaderService.show()
        const orderId = await this.initiateOrder()

        // this.phonePeParamsService.getParams(
        //     {
        //         orderId: orderId,
        //         userId: this.tokenService.getUserId(),
        //         amount: this.getGrandTotal
        //     }
        // ).subscribe(
        //     (res: any) => {
        //         if (res.status && res.response) {
        //             this.loaderService.hide()
        //             window.open(res.response)
        //         }
        //         else {
        //             this.loaderService.hide()
        //             this.toastService.showDangerToast(res.message)
        //         }
        //     },
        //     (error) => {
        //         this.loaderService.hide()
        //         // console.log(error)
        //         this.toastService.showDangerToast("Oops! Something Went Wrong. Please Try Again.")
        //     }
        // )

        // const payload = {
        //     "merchantId": this.MERCHANT_ID,
        //     "merchantTransactionId": orderId,
        //     "amount": this.getGrandTotal * 100,
        //     "merchantUserId": this.tokenService.getUserId(),
        //     "redirectUrl": this.FRONTEND_URL + "#/cartDetails?orderId=" + orderId,
        //     "redirectMode": "REDIRECT",
        //     "callbackUrl": this.BACKEND_URL + "c2c/api/orders/v2/confirmOrder/" + orderId + "/" + this.MERCHANT_ID,
        //     "paymentInstrument": { "type": "PAY_PAGE" }
        // }

        const payload = {
            amount: this.getGrandTotal,
            userId: this.tokenService.getUserId(),
            orderId: orderId

        }

        // const base64 = btoa(JSON.stringify(payload))
        // const checksum = SHA256(base64 + this.API_ENDPOINT + this.SALT_INDEX) + "###" + this.SALT_INDEX
        const { response }: any = await this.phonePeParamsService.getParams(payload)
        // if (!base64 || !checksum) {
        //     // this.toastService.showDangerToast(message)
        //     this.loaderService.hide()
        //     return
        // }
       
        window.location.replace(response)
        // console.log(JSON.parse(payload))
        // // console.log(JSON.parse(JSON.stringify(payload1)))
        // // console.log(base641)



        // fetch('https://api.phonepe.com/apis/hermes/pg/v1/pay', options)
        //     .then(response => response.json())
        //     .then(response => {

        //         console.log(response)
        //     })
        //     .catch(err => console.error(err));
        
    }

    postCheckout() {
        let coursePackagesInfo = []
        this.userPackages.forEach((pkg: any) => {
            coursePackagesInfo.push({
                coursePackageId: pkg.coursePackageId,
                coursePackageName: pkg.coursePackageName,
                coursePackagePrice: pkg.coursePackagePrice
            })
        })
        // formData.append('coursePackagesInfo', JSON.stringify(coursePackagesInfo))
        // formData.append('userId', this.tokenService.getUserId())
        const requestBody = {
            userId: this.tokenService.getUserId(),
            coursePackagesInfo: coursePackagesInfo,
            totalAmount: this.getGrandTotal,
            subTotal: this.getSubTotal,
            gst: this.getGst
        }

        console.log(requestBody)
        this.courseBuyout.subscribeLearnerToPackages(requestBody).subscribe((data: any) => {
            // console.log(data)
            this.cartService.refreshCart()
            this.orderCompleteEvent.emit()
        })
    }

    generateOrderID() {
        // Get current date and time
        const currentDate = new Date();

        // Format date and time components
        const year = currentDate.getFullYear();
        const month = (currentDate.getMonth() + 1).toString().padStart(2, '0'); // Adding 1 because months are zero-based
        const day = currentDate.getDate().toString().padStart(2, '0');
        const hours = currentDate.getHours().toString().padStart(2, '0');
        const minutes = currentDate.getMinutes().toString().padStart(2, '0');
        const seconds = currentDate.getSeconds().toString().padStart(2, '0');

        // Combine date, time, and random number
        const orderID = `M${year}${month}${day}${hours}${minutes}${seconds}`;

        return orderID;
    }

    async initiateOrder() {
        let coursePackagesInfo = []
        this.userPackages.forEach((pkg: any) => {
            coursePackagesInfo.push({
                coursePackageId: pkg.coursePackageId,
                coursePackageName: pkg.coursePackageName,
                coursePackagePrice: pkg.coursePackagePrice
            })
        })
        // formData.append('coursePackagesInfo', JSON.stringify(coursePackagesInfo))
        // formData.append('userId', this.tokenService.getUserId())
        const requestBody = {
            userId: this.tokenService.getUserId(),
            coursePackagesInfo: coursePackagesInfo,
            totalAmount: this.getGrandTotal,
            subTotal: this.getSubTotal,
            gst: this.getGst
        }

        return await this.orderService.initiateOrder(requestBody)
    }

    // async sha256(message: string) {
    //   const encoder = new TextEncoder();
    //   const data = encoder.encode(message);
    //   const buffer = await window.crypto.subtle.digest('SHA-256', data);
    //   const hashArray = Array.from(new Uint8Array(buffer));
    //   const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
    //   // console.log(hashArray)
    //   return hashHex;
    // }
}
