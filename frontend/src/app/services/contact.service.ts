import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Contact } from '../models/contact';
import { Observable } from 'rxjs';
import { AbstractService } from './abstractions/abstract.service';

@Injectable({
  providedIn: 'root'
})
export class ContactService extends AbstractService<Contact> {

  constructor(http: HttpClient) {
    super('contacts', http)
  }

  update(id: number, contact: Contact): Observable<void>{
    return this.http.put<void>(`/api/contacts/${id}`, contact);
  }

}
