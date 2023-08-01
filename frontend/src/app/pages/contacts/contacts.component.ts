import { Component, OnInit } from '@angular/core';
import { ContactService } from 'src/app/services/contact.service';
import { ContactTableService } from 'src/app/services/tables/contact-table.service';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.scss']
})
export class ContactsComponent implements OnInit {
  constructor(private contactService: ContactService, public contactTable: ContactTableService) {}

  ngOnInit(): void {
    this.contactTable.update()
  }

}
