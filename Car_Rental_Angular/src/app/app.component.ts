import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from './auth/services/storage/storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Car_Rental_Angular';

  isCustomerLoggedIn: boolean = StorageService.isCustomerLoggedIn();
  isAdminLoggedin: boolean= StorageService.isAdminLoggedIn();

  constructor(private router: Router){

  }
  
  ngOnInit(){
    this.router.events.subscribe(event => {
      if(event.constructor.name==="NavigationEnd"){
        this.isAdminLoggedin=StorageService.isAdminLoggedIn();
        this.isCustomerLoggedIn=StorageService.isCustomerLoggedIn();
      }
    })
  }

  logout(){
    StorageService.logout();
    this.router.navigateByUrl("/login");
  }
}
