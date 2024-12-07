import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-order-complete',
  templateUrl: './order-complete.component.html',
  styleUrls: ['./order-complete.component.css']
})
export class OrderCompleteComponent implements OnInit {
  orderId:string;
  paymentTxnDetail:any;
  orderSuccess = false;
  orderFailed = false;
  constructor(private router:Router,
      private activatedRoute: ActivatedRoute,
      private orderService:OrderService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {      
      this.orderId = params['orderId'];
    });
    this.getPaymentDetails(this.orderId);
  }


  getPaymentDetails(orderId:string){
    this.orderService._getPaymentDetails(orderId).subscribe(data=>{
      this.paymentTxnDetail = data;
      if(data.orderStatus === "Success"){
        this.orderSuccess = true;
      }else{
        this.orderFailed = true;
      }
    }, error=>{
    });
  }
}
