import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient, private router: Router) { }

  public authenticate(email: string, password: string): Observable<{token: string}>{
    return this.http.post<{token: string}>('/api/auth', {email, password})
      .pipe(map(response => {
        this.token = response.token
        return response;
      }));
  }

  get token(): string | null{
    return localStorage.getItem('token');
  }

  set token(value: string | null){
    if(value){
      localStorage.setItem('token', value);
    }
    else {
      localStorage.removeItem('token');
    }
  }

  get isAuthenticated(): boolean {
    return this.token != null && this.token != undefined;
  }

  logout() {
    this.token = null;
    this.router.navigate(['login']);
  }

}
