import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-search-car',
  templateUrl: './search-car.component.html',
  styleUrl: './search-car.component.css'
})
export class SearchCarComponent {

  searchCarForm!: FormGroup;

  cars:any=[];

  listOfOption: Array<{ label: string; value: string }> = [];
  listOfBrands = ["BMW", "AUDI", "FERRARI", "TESLA", "VOLVO", "TOYOTA", "HONDA", "FORD", "NISSAN", "HYUNDAI", "LEXUS", "KIA"];
  listOfType = ["Petrol", "Hybrid", "Diesel", "Electric", "CNG"];
  listOfColor = ["Red", "White", "Blue", "Black", "Orange", "Grey", "Silver"];
  listOfTransmission = ["Manual", "Automatic"];

  isSpinning: boolean = false;

  constructor(private fb: FormBuilder,
    private adminService: AdminService){
      this.searchCarForm = this.fb.group({
        brand: [null, Validators.required],
        type: [null, Validators.required],
        transmission: [null, Validators.required],
        color: [null, Validators.required],
      })
  }

  searchCar(){
    this.isSpinning=true;
    this.adminService.searchcar(this.searchCarForm.value).subscribe((res) => {
      this.isSpinning=false;
      this.cars=[];
      res.carDtoList.forEach(((element: { processedImg: string; image: string; }) => {
        element.processedImg = 'data:image/jpeg;base64,'+element.image;
        this.cars.push(element);
      }))
    })
  }


}
