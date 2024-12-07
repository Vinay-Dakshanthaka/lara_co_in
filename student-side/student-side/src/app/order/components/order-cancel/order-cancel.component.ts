import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-order-cancel',
  templateUrl: './order-cancel.component.html',
  styleUrls: []
})
export class OrderCancelComponent implements OnInit {

  orderId:string;
  orderDetails:any;
  constructor(private router:Router,
      private activatedRoute: ActivatedRoute,
      private orderService:OrderService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {      
      this.orderId = params['orderId'];
    });
    this.getCancelledOrderDetails(this.orderId);
  }

  getCancelledOrderDetails(orderId:string){
    this.orderService._getCancelledOrderDetails(orderId).subscribe(data=>{
      this.orderDetails = data;
    });
  }

}
