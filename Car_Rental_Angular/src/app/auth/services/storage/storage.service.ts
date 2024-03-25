import { Injectable } from '@angular/core';

const TOKEN = "token";
const USER = "user";

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  static saveToken(token:string):void{
    console.log("saveToken");
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN, token);
  }

  static saveUser(user:any):void{
    console.log("saveUser");
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER, JSON.stringify(user));
  }

  static getToken(){
    if (typeof window !== 'undefined') {
      return window.localStorage.getItem('token');
    } else {
      return null;
    }
  }

  static getUser(){
    const temp= localStorage.getItem(USER);
    if(temp!=null)
    return JSON.parse(temp);
    else
    return null;
  }

  static getUserId():string{
    const user= this.getUser();
    if(user!=null)
    return user.id;
    else
    return '';
  }

  static getUserRole():string{
    const user=this.getUser();
    if(user==null) return "";
    return user.role;
  }

  static isAdminLoggedIn():boolean{
    if(this.getToken()==null)
    return false;
    const role=this.getUserRole();
    return role=="ADMIN";
  }

  static isCustomerLoggedIn():boolean{
    if(this.getToken()==null)
    return false;
    const role=this.getUserRole();
    return role=="CUSTOMER";
  }

  static logout(): void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
  }

}
