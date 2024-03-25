import { HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-update-car',
  templateUrl: './update-car.component.html',
  styleUrl: './update-car.component.css'
})
export class UpdateCarComponent {

  imgChanged: boolean =false;
  selectedFile:any;
  imagePreview!: any;
  updateForm!: FormGroup;
  isSpinning: boolean = false;
  carId: number=this.activateRoute.snapshot.params["id"];
  existingImage!: string | null;
  listOfOption: Array<{ label: string; value: string }> = [];
  listOfBrands = ["BMW", "AUDI", "FERRARI", "TESLA", "VOLVO", "TOYOTA", "HONDA", "FORD", "NISSAN", "HYUNDAI", "LEXUS", "KIA"];
  listOfType = ["Petrol", "Hybrid", "Diesel", "Electric", "CNG"];
  listOfColor = ["Red", "White", "Blue", "Black", "Orange", "Grey", "Silver"];
  listOfTransmission = ["Manual", "Automatic"];

  constructor(private adminService: AdminService,
    private activateRoute: ActivatedRoute,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router){

  }

  ngOnInit(){
    this.getCarById();
    this.updateForm = this.fb.group({
      name: [null, Validators.required],
      brand: [null, Validators.required],
      type: [null, Validators.required],
      color: [null, Validators.required],
      transmission: [null, Validators.required],
      price: [null, Validators.required],
      description: [null, Validators.required],
      year: [null, Validators.required],
    })
  }

  getCarById(){
    this.isSpinning=true;
    this.adminService.getCarById(this.carId).subscribe((res) => {
      this.isSpinning=false;
      console.log(res);
      const carDto=res;
      this.existingImage='data:image/jpeg;base64,'+res.image;
      // console.log(carDto);
      // console.log(this.existingImage);
      this.updateForm.patchValue(carDto);
    })
  }

  updateCar(){
    if (this.updateForm) {
      this.isSpinning = true;
      const formData: FormData = new FormData();
      if(this.imgChanged && this.selectedFile){
      formData.append('img', this.selectedFile);
      }
      formData.append('brand', this.updateForm.get('brand')?.value);
      formData.append('name', this.updateForm.get('name')?.value);
      formData.append('type', this.updateForm.get('type')?.value);
      formData.append('color', this.updateForm.get('color')?.value);
      formData.append('year', (this.updateForm.get('year')?.value).toISOString());
      formData.append('transmission', this.updateForm.get('transmission')?.value);
      formData.append('description', this.updateForm.get('description')?.value);
      formData.append('price', this.updateForm.get('price')?.value);
     
      const data ={
        'image':this.imagePreview?.split(',')[1],
        'brand': this.updateForm.get('brand')?.value,
        'name':this.updateForm.get('name')?.value,
        'type':this.updateForm.get('type')?.value,
        'color':this.updateForm.get('color')?.value,
        'year':(this.updateForm.get('year')?.value).toISOString(),
        'transmission':this.updateForm.get('transmission')?.value,
        'description':this.updateForm.get('description')?.value,
        'price':this.updateForm.get('price')?.value
      }
      this.adminService.updateCar(this.carId,data).subscribe((res) =>{
        this.isSpinning =false;
        this.message.success("Car updated successfully",{nzDuration: 5000});
        this.router.navigateByUrl("/admin/dashboard");
      },
      error =>{
        this.message.success("Error while updating car",{nzDuration: 5000});
      })
    }
  }

  onFileSelected(event:any){
    this.selectedFile=event.target.files[0];
    this.imgChanged=true;
    this.existingImage=null;
    this.previewImage();
  }
  previewImage() {
    const reader = new FileReader();
    reader.onload = () => {
      this.imagePreview = reader.result;
    }
    reader.readAsDataURL(this.selectedFile);
  }


}
