import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AutocompleteTypes } from '@ionic/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AuthService } from '../../services/auth/auth.service';
import { StorageService } from '../../services/storage/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  isSpinning: boolean=false;
  loginForm!: FormGroup;

  constructor(private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private message: NzMessageService){

  }

  ngOnInit(){
    this.loginForm=this.fb.group({
      email:[null,[Validators.required, Validators.email]],
      password:[null,[Validators.required]],
    })
  }

  login(){
    this.authService.login(this.loginForm.value).subscribe((res)=>{
      console.log(res);
      if(res.userid!=null){
        const user = {
          id: res.userid,
          role: res.userRole,
        }
        StorageService.saveUser(user);
        StorageService.saveToken(res.jwt);
        if(StorageService.isAdminLoggedIn()){
          this.router.navigateByUrl("/admin/dashboard");
        }
        else if(StorageService.isCustomerLoggedIn()){
          this.router.navigateByUrl("/customer/dashboard");
        }
        else{
          this.message.error("Bad credentials",{nzDuration: 50000});
        }
      }
      (error: any) => {
        console.error("Login error:", error);
        this.message.error("Login failed", { nzDuration: 5000 });
       }
    });

  }
}
