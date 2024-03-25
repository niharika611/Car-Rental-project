import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { StorageService } from '../../../../auth/services/storage/storage.service';
import { CustomerServiceService } from '../../services/customer-service.service';

@Component({
  selector: 'app-book-car',
  templateUrl: './book-car.component.html',
  styleUrl: './book-car.component.css'
})
export class BookCarComponent {

  car:any;

  isSpinning=false;
  processedImage:any;
  dateFormat!: "DD-MM-YYYY";
  validateForm!:FormGroup;

  carId:number=this.activatedRoute.snapshot.params["id"];

  constructor(private customerService: CustomerServiceService,
    private activatedRoute:ActivatedRoute,
    private fb:FormBuilder,
    private message: NzMessageService,
    private router: Router){

  }

  ngOnInit(){
    this.validateForm=this.fb.group({
      toDate:[null,Validators.required],
      fromDate:[null,Validators.required],
    })
    this.getCarById();
  }

  getCarById(){
    this.customerService.getCarById(this.carId).subscribe((res)=>{
      console.log(res);
      this.processedImage = 'data:image/jpeg;base64,'+res.image;
      this.car=res;
    })
  }

  bookCar(data:any){
    console.log(data);
    this.isSpinning=true;
    let bookCarDto={
      toDate:data.toDate,
      fromDate:data.fromDate,
      userId:StorageService.getUserId(),
      carId:this.carId
    }
    console.log(bookCarDto);
    this.customerService.bookCar(bookCarDto).subscribe((res)=>{
      console.log(res);
      this.message.success("Booking request submitted successfully",{nzDuration: 5000});
      this.router.navigateByUrl("/customer/dashboard");
    },
    error =>{
      this.message.error("Something went wrong",{nzDuration: 5000});
    })
  }
}
