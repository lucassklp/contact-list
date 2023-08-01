import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  formGroup: FormGroup;

  constructor(fb: FormBuilder, private router: Router, private userService: UserService) {
    this.formGroup = fb.group({
      name: [null, [Validators.required]],
      email: [null, [Validators.email, Validators.required]],
      password: [null, Validators.required]
    })
  }

  register() {
    const name = this.formGroup.controls['name'].value
    const email = this.formGroup.controls['email'].value;
    const password = this.formGroup.controls['password'].value
    this.userService.register({name, email, password}).subscribe(_ => {
      this.router.navigate(['login']);
    });
  }

}
