import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  formGroup: FormGroup

  constructor(fb: FormBuilder, private authService: AuthenticationService, private router: Router) {
    this.formGroup = fb.group({
      email: [null, [Validators.email, Validators.required]],
      password: [null, Validators.required]
    })
  }

  login() {
    const email = this.formGroup.controls['email'].value;
    const password = this.formGroup.controls['password'].value
    this.authService.authenticate(email, password).subscribe(_ => {
      this.router.navigate(['contacts']);
    });
  }
}
