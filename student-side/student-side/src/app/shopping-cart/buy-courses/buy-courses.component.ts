import { Component, OnInit, ViewChild, Input, ChangeDetectorRef, EventEmitter, ElementRef, Output } from '@angular/core';
import { LoaderService } from 'src/app/common-service/loader.service';
import { CourseService } from 'src/app/course/services/course.service';
import { ToastService } from 'src/app/toast/toast.service';
import { CartServiceService } from '../cart-service.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { CartComponent } from '../cart/cart.component';
import { coreServices } from 'videogular2/core';
import { ChangeDetectionStrategy } from '@angular/compiler/src/compiler_facade_interface';
import { HasElementRef } from '@angular/material/core/typings/common-behaviors/color';
import { UserProfileService } from 'src/app/dashboard/services/user-profile.service';
import { concatAll } from 'rxjs/operators';

@Component({
    selector: 'app-buy-courses',
    templateUrl: './buy-courses.component.html',
    styleUrls: ['./buy-courses.component.css']
})
export class BuyCoursesComponent implements OnInit {

    @ViewChild(CartComponent, { static: true }) cart: CartComponent

    @Output() deleteEvent = new EventEmitter<number>()

    @Input() packageList = []

    @Input() cartDetails = false

    userPackages = []

    packageIdSet = new Set()

    noPackage = false


    constructor(
        private loaderService: LoaderService,
        private courseService: CourseService,
        private toastService: ToastService,
        private cartService: CartServiceService,
        private tokenService: TokenStorageService,
        private cdr: ChangeDetectorRef,
        private userProfileService: UserProfileService
    ) { }

    ngOnInit() {
        if (!this.cartDetails) {
            this.getPackages()
            this.getUserCart()
        }
        else {
            // console.log(this.packageList.length)
        }
    }

    async getPackages() {
        this.loaderService.show()
        // console.log(1)
        await this.getUserSubscribedPackages()
        // console.log(2)
        if (localStorage.getItem('_loggedIn') && localStorage.getItem('_loggedIn') === 'yes') {
            // let formData = new FormData()
            // formData.append('userId', this.tokenService.getUserId())
            this.courseService._getUnsubscribedCoursesForUser(this.tokenService.getUserId()).subscribe((stat: any) => {
                if (stat.exceptionReport == null) {
                    // console.log(stat)
                    this.packageList = stat.coursePackageList
                    this.packageList = this.packageList.filter((pkg: any) => (!(this.packageIdSet.has(pkg.coursePackageId)) && pkg.isActive))
                }
                // console.log(this.packageList)
                this.checkForNoPackage()
                this.loaderService.hide()
            })
        }
        else {
            this.courseService._getCoursePackageList().subscribe(stat => {
                if (stat.exceptionReport == null) {
                    // console.log(stat)
                    this.packageList = stat.coursePackageList
                    this.packageList = this.packageList.filter((pkg: any) => !!pkg.isActive)
                }
                console.log(this.packageList)
                this.checkForNoPackage()
                this.loaderService.hide()
            })
        }
        // console.log(3)
    }

    getNewLineFormattedString(desc: string) {
        desc.replace(/\n/g, '<br>')
        return desc
    }

    addToCart(e: any, packageId: number) {
        // console.log(this.tokenService.getLoggedInStatus())
        if (!this.tokenService.getLoggedInStatus()) {
            this.toastService.showDangerToast('Please login to make purchase ! :)')
            return
        }
        let formData = new FormData()
        formData.append('userId', this.tokenService.getUserId())
        formData.append('packageId', packageId + '')
        this.cartService.addToCart(formData).subscribe(stat => {
            // console.log(stat)
            // console.log('from addToCart')
            this.cartService.refreshCart()
            this.toastService.showSuccessToast('Added to cart ! Now please Go To Cart which is located on top right corner of the page and proceed to checkout.')
            this.changePackageAddOrRemoveOptions(e.target)
            this.getUserCart()
            this.cdr.detectChanges()
        })
    }

    deleteFromCart(e: any, packageId: number, index: number) {
        let formData = new FormData()
        formData.append('userId', this.tokenService.getUserId())
        formData.append('packageId', packageId + '')
        this.cartService.deleteFromCart(formData).subscribe(stat => {
            this.cartService.refreshCart()
            // console.log(index)
            this.deleteEvent.emit(index)
            this.getUserCart()
            this.cdr.detectChanges()
            this.toastService.showSuccessToast('Deleted from cart !')
        })
    }

    getUserCart() {
        let formData = new FormData()
        formData.append('userId', this.tokenService.getUserId())
        this.cartService.getUserCart(formData).subscribe(stat => {
            // console.log(stat)
            this.userPackages = stat as Array<any>
            // console.log(this.userPackages)
        })
    }

    packageMatch(packageId: number) {
        return this.userPackages.findIndex(coursePackage => coursePackage.coursePackageId === packageId) >= 0 ? true : false;
    }

    changePackageAddOrRemoveOptions(element: any) {
        const addOptions = element.closest('.course-card').querySelector('.addOptions')
        // console.log(addOptions)
        // console.log(element.closest('.course-card').querySelector('.removeOptions'))
        const removeOptions = element.closest('.course-card').querySelector('.removeOptions')
        // console.log(removeOptions)
    }

    getUserSubscribedPackages() {
        return new Promise((res) => {
            this.userProfileService._getAllCoursePackForLearner().subscribe(
                (data: any) => {
                    // console.log(data.valueOf())
                    data.forEach((element: any) => {
                        this.packageIdSet.add(element.coursePackageId)
                    });
                    res(true)
                }
            )
        })
    }
    checkForNoPackage() {

        if (this.packageList.length === 0) this.noPackage = true
        else this.noPackage = false
    }

    getOfferPercentage(cPack: any) {
        return Math.round(((cPack.coursePackageOriginalPrice - cPack.coursePackagePrice) / cPack.coursePackageOriginalPrice) * 100)
    }

    getPackageContentName(cPack: any) {
        return "./assets/pdf/coursePackage/" + cPack.coursePackageName + cPack.coursePackageId + ".pdf"
    }
}

