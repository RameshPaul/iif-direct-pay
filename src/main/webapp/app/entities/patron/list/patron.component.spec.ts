import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PatronService } from '../service/patron.service';

import { PatronComponent } from './patron.component';

describe('Patron Management Component', () => {
  let comp: PatronComponent;
  let fixture: ComponentFixture<PatronComponent>;
  let service: PatronService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PatronComponent],
    })
      .overrideTemplate(PatronComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PatronComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PatronService);

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
    expect(comp.patrons?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
