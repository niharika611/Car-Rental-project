import { Component } from '@angular/core';
import { CustomerServiceService } from '../../services/customer-service.service';

@Component({
  selector: 'app-my-bookings',
  templateUrl: './my-bookings.component.html',
  styleUrl: './my-bookings.component.css'
})
export class MyBookingsComponent {

  bookings:any;

  isSpinning:boolean=false;

  constructor(private customerService: CustomerServiceService) { 
    this.getMyBookings();
  }

  ngOnInit(){
    
  }

  getMyBookings(){
    this.isSpinning=true;
    this.customerService.getBookingsByUserId().subscribe((res)=>{
      this.isSpinning=false;
      this.bookings=res;
    })
  }

}
