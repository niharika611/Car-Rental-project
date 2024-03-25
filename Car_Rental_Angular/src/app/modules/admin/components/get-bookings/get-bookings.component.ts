import { Component } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-get-bookings',
  templateUrl: './get-bookings.component.html',
  styleUrl: './get-bookings.component.css'
})
export class GetBookingsComponent {

  isSpinning:boolean=false;

  bookings:any;

  constructor(private adminService: AdminService,
    private message: NzMessageService,){
    this.getAllBookings();
  }

  getAllBookings(){
    this.isSpinning=true;
    this.adminService.getBookings().subscribe((res)=>{
      this.isSpinning=false;
      this.bookings=res;
    })
  }

  changeBookingStatus(bookingId:number, status:string){
    this.isSpinning=true;
    console.log(bookingId + ' '+status);
    this.adminService.changeBookingStatus(bookingId,status).subscribe((res) => {
      this.isSpinning=false;
      console.log(res);
      this.getAllBookings();
      this.message.success("Booking status changed successfully",{nzDuration: 5000});
    },
    error =>{
      this.message.success("Something went wrong",{nzDuration: 5000});
    })
  }
}
