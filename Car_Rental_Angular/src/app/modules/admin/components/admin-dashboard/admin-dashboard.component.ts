import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {

  cars:any=[];

  constructor(private adminService: AdminService,
    private message: NzMessageService,
    private router: Router){

  }

  ngOnInit(){
    this.getAllCars();
  }

  getAllCars(){
    this.adminService.getAllCars().subscribe((res) =>{
      res.forEach(((element: { processedImg: string; image: string; }) => {
        element.processedImg = 'data:image/jpeg;base64,'+element.image;
        this.cars.push(element);
      }))
    })
  }

  deleteCar(id:number){
    console.log(id);
    this.adminService.deleteCar(id).subscribe((res) =>{
      this.cars = [];
      this.getAllCars();
      this.message.success("Car deleted successfully",{nzDuration: 5000});
    })
  }
}
