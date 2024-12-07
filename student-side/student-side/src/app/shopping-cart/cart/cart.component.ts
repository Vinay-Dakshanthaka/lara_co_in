import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { faShoppingCart } from '@fortawesome/free-solid-svg-icons';
import { CartServiceService } from '../cart-service.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CartComponent implements OnInit {
  cartIcon = faShoppingCart

  cartItems = 0

  constructor(
    public cartService: CartServiceService,
    public tokenService: TokenStorageService,
    public cdr: ChangeDetectorRef
  ) { }

  ngOnInit() {
    this.getRealTimeCart()
    this.cartService.cartRefresh.subscribe(() => {
      this.getRealTimeCart()
    })
  }

  getRealTimeCart() {
    let formData = new FormData()
    formData.append('userId', this.tokenService.getUserId())
    this.cartService.getCartNoOfItems(formData).subscribe(stat => {
      // console.log(stat)
      // console.log(typeof stat)
      this.cartItems = stat as number
      this.cdr.detectChanges()
    })
  }


}
