import { TestBed } from '@angular/core/testing';

import { ContactTableService } from './contact-table.service';

describe('ContactTableService', () => {
  let service: ContactTableService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContactTableService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
