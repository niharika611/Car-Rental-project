import { Component } from '@angular/core';
import { CustomerServiceService } from '../../services/customer-service.service';

@Component({
  selector: 'app-customer-dashboard',
  templateUrl: './customer-dashboard.component.html',
  styleUrl: './customer-dashboard.component.css'
})
export class CustomerDashboardComponent {

  cars: any=[];
  constructor(private customerService: CustomerServiceService) { 

  }

  ngOnInit(){
    this.getAllCars();
  }

  getAllCars(){
    this.customerService.getAllCars().subscribe((res) =>{
      res.forEach(((element: { processedImg: string; image: string; }) => {
        console.log(this.cars);
        element.processedImg = 'data:image/jpeg;base64,'+element.image;
        this.cars.push(element);
      }))
    })
  }


}
