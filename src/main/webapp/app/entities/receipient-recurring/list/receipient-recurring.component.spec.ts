import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ReceipientRecurringService } from '../service/receipient-recurring.service';

import { ReceipientRecurringComponent } from './receipient-recurring.component';

describe('ReceipientRecurring Management Component', () => {
  let comp: ReceipientRecurringComponent;
  let fixture: ComponentFixture<ReceipientRecurringComponent>;
  let service: ReceipientRecurringService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ReceipientRecurringComponent],
    })
      .overrideTemplate(ReceipientRecurringComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReceipientRecurringComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ReceipientRecurringService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.receipientRecurrings?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
