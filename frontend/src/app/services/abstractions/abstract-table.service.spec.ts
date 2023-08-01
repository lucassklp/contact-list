import { TestBed } from '@angular/core/testing';

import { AbstractTableService } from './abstract-table.service';

describe('AbstractTableService', () => {
  let service: AbstractTableService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AbstractTableService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
