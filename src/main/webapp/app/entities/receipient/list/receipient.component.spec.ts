import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ReceipientService } from '../service/receipient.service';

import { ReceipientComponent } from './receipient.component';

describe('Receipient Management Component', () => {
  let comp: ReceipientComponent;
  let fixture: ComponentFixture<ReceipientComponent>;
  let service: ReceipientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ReceipientComponent],
    })
      .overrideTemplate(ReceipientComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReceipientComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ReceipientService);

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
    expect(comp.receipients?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
