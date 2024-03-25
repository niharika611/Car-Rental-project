import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth/services/storage/storage.service';

const BASIC_URL=["http://localhost:8080"];
@Injectable({
  providedIn: 'root'
})
export class CustomerServiceService {

  constructor(private http:HttpClient) { }

  getAllCars():Observable<any>{
    return this.http.get(BASIC_URL+"/api/customer/cars");
  }

  getCarById(carId:number):Observable<any>{
    return this.http.get(BASIC_URL+"/api/customer/car/"+carId);
  }

  bookCar(bookCarDto:any):Observable<any>{
    return this.http.post(BASIC_URL+"/api/customer/cars/book",bookCarDto);
  }

  getBookingsByUserId():Observable<any>{
    return this.http.get(BASIC_URL+"/api/customer/car/bookings/"+StorageService.getUserId());
  }

  createAuthorizationHeader(): HttpHeaders {
    let authHeaders:HttpHeaders=new HttpHeaders();
    console.log(StorageService.getToken());
    return authHeaders.set(
      'Authorization',
      'Bearer '+StorageService.getToken()
    );
  }

  searchcar(searchCarDto: any):Observable<any>{
    return this.http.post(BASIC_URL+"/api/customer/car/search",searchCarDto);
  }
  
}
