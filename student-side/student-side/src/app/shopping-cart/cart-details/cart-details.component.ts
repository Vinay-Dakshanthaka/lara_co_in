import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef, ViewChild, ElementRef } from '@angular/core';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { CourseService } from 'src/app/course/services/course.service';
import { ToastService } from 'src/app/toast/toast.service';
import { CartServiceService } from '../cart-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BillSummaryComponent } from '../bill-summary/bill-summary.component';
import { LoaderService } from 'src/app/common-service/loader.service';
import { OrdersService } from '../service/orders.service';

@Component({
    selector: 'app-cart-details',
    templateUrl: './cart-details.component.html',
    styleUrls: ['./cart-details.component.css'],
    // changeDetection: ChangeDetectionStrategy.OnPush
})
export class CartDetailsComponent implements OnInit {

    @ViewChild('coursePackages', { static: false }) coursePackages: ElementRef

    @ViewChild(BillSummaryComponent, { static: false }) bill: BillSummaryComponent

    userPackages = []

    emptyCart = false

    orderState = ""

    paymentStatus = ""

    orderClosed = false

    orderId = ""

    constructor(
        private cartService: CartServiceService,
        private toastService: ToastService,
        private tokenService: TokenStorageService,
        private router: Router,
        private cdr: ChangeDetectorRef,
        private route: ActivatedRoute,
        private loaderService: LoaderService,
        private ordersService: OrdersService
    ) { }

    ngOnInit() {
        if (this.tokenService.getLoggedInStatus()) {
            this.loaderService.show()
            this.orderId = this.route.snapshot.queryParamMap.get('orderId')
            // console.log(orderId)
            if (this.orderId) {
                // console.log(1)
                const fd = new FormData()
                fd.append('orderId', this.orderId + "")
                this.ordersService.checkStatus(fd).subscribe(
                    (res: any) => {
                        // console.log(res)
                        if (res.status) {
                            this.orderState = res.state
                            this.paymentStatus = res.paymentStatus
                            this.orderClosed = res.orderClosed
                            this.cartService.refreshCart()
                            this.loaderService.hide()
                        }
                        else this.getUserCart()
                    },
                    (error) => { this.getUserCart() }
                )
            }
            else this.getUserCart()
        }

        else {
            this.router.navigateByUrl("")
            this.toastService.showDangerToast('please login to continue ! :)')
        }
        this.cdr.detectChanges()
    }

    getUserCart() {
        let formData = new FormData()
        formData.append('userId', this.tokenService.getUserId())
        this.cartService.getUserCart(formData).subscribe(stat => {
            // console.log(stat)
            this.userPackages = stat as Array<any>
            this.emptyCartStatus()
            // console.log(this.userPackages)
            this.loaderService.hide()
        })
    }

    deleteFromCart(index: number) {
        // console.log(this.coursePackages)
        // console.log(index)
        this.userPackages.splice(index, 1)
        this.emptyCartStatus()
        this.bill.ngOnInit()
    }

    emptyCartStatus() {
        if (this.userPackages.length) {
            this.emptyCart = false
        }
        else {
            this.emptyCart = true
        }
    }
}
