import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Contact } from 'src/app/models/contact';

@Component({
  selector: 'app-contact-fields',
  templateUrl: './contact-fields.component.html',
  styleUrls: ['./contact-fields.component.scss']
})
export class ContactFieldsComponent implements OnInit, OnChanges {
  formGroup: FormGroup;

  @Input('contact')
  contact?: Contact;

  constructor(fb: FormBuilder){
    this.formGroup = fb.group({
      name: [null, Validators.required],
      phone: [null, Validators.required],
      email: [null, [Validators.required, Validators.email]]
    })
  }
  ngOnChanges(changes: SimpleChanges): void {
    this.update();
  }

  ngOnInit(): void {
    this.update();
  }

  private update(){
    if(this.contact){
      this.formGroup.controls['name'].setValue(this.contact.name);
      this.formGroup.controls['phone'].setValue(this.contact.phone);
      this.formGroup.controls['email'].setValue(this.contact.email);  
    }
  }

  isValid(): boolean{
    return this.formGroup.valid;
  }

  getContact(): Contact {
    return {
      name: this.formGroup.controls['name'].value,
      phone: this.formGroup.controls['phone'].value,
      email: this.formGroup.controls['email'].value
    }
  }
}
