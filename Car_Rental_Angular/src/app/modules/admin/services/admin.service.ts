import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth/services/storage/storage.service';

const BASIC_URL=["http://localhost:8080"];
@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  postCar(carDto:any):Observable<any>{
    return this.http.post(BASIC_URL+"/api/admin/car",carDto);
  }

  createAuthorizationHeader(): HttpHeaders {
    let authHeaders:HttpHeaders=new HttpHeaders();
    console.log(StorageService.getToken());
    return authHeaders.set(
      'Authorization',
      'Bearer '+StorageService.getToken()
    );
  }

  getAllCars():Observable<any>{
    return this.http.get(BASIC_URL+"/api/admin/cars");
  }

  getBookings():Observable<any>{
    return this.http.get(BASIC_URL+"/api/admin/car/bookings");
  }

  changeBookingStatus(id: number,status: string):Observable<any>{
    return this.http.get(BASIC_URL+ `/api/admin/car/booking/${id}/${status}`);
  }

  deleteCar(id: number):Observable<any>{
    return this.http.delete(BASIC_URL+"/api/admin/car/"+id);
  }

  getCarById(id: number):Observable<any>{
    return this.http.get(BASIC_URL+"/api/admin/car/"+id);
  }

  updateCar(id: number, carDto: any):Observable<any>{
    return this.http.put(BASIC_URL+"/api/admin/car/"+id,carDto);
  }

  searchcar(searchCarDto: any):Observable<any>{
    return this.http.post(BASIC_URL+"/api/admin/car/search",searchCarDto);
  }

}
