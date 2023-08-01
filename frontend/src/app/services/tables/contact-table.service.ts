import { Injectable } from '@angular/core';
import { AbstractTableService } from '../abstractions/abstract-table.service';
import { Contact } from 'src/app/models/contact';
import { ContactService } from '../contact.service';
import { ToastrService } from '../toastr.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';

@Injectable({
  providedIn: 'root'
})
export class ContactTableService extends AbstractTableService<Contact> {

  constructor(service: ContactService, toastr: ToastrService, router: Router, dialog: MatDialog) {
    super(service, toastr, router, dialog)
  }

  public override get displayedColumns(): string[] {
    return ['name', 'email', 'phone', 'actions']
  }

  public override get pageSizeOptions(): number[] {
    return [5, 10, 25, 100];
  }

  override edit(id: number): void {
    this.router.navigate(['contacts', id, 'update'])
  }

}
