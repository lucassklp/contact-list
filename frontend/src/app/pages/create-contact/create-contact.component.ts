import { Component, ViewChild } from '@angular/core';
import { Route, Router } from '@angular/router';
import { ContactFieldsComponent } from 'src/app/components/contact-fields/contact-fields.component';
import { ContactService } from 'src/app/services/contact.service';

@Component({
  selector: 'app-create-contact',
  templateUrl: './create-contact.component.html',
  styleUrls: ['./create-contact.component.scss']
})
export class CreateContactComponent {

  constructor(private contactService: ContactService, private router: Router) {}

  @ViewChild(ContactFieldsComponent)
  contactFields?: ContactFieldsComponent;

  submit(){
    const contact = this.contactFields!.getContact();
    this.contactService.save(contact).subscribe(_=> {
      this.router.navigate(['contacts'])
    });
  }

}
