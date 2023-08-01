import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ContactFieldsComponent } from 'src/app/components/contact-fields/contact-fields.component';
import { Contact } from 'src/app/models/contact';
import { ContactService } from 'src/app/services/contact.service';

@Component({
  selector: 'app-update-contact',
  templateUrl: './update-contact.component.html',
  styleUrls: ['./update-contact.component.scss']
})
export class UpdateContactComponent implements OnInit {

  
  @ViewChild(ContactFieldsComponent)
  contactFields?: ContactFieldsComponent;

  contact?: Contact

  constructor(private contactService: ContactService, private router: Router, private route:ActivatedRoute) {}

  ngOnInit(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!);
    this.contactService.get(id)
      .subscribe(contact => this.contact = contact);
  }

  update(){
    this.contactService.update(this.contact!.id!, this.contactFields?.getContact()!)
      .subscribe(_ => {
        this.router.navigate(['contacts'])
      })
  }

}
