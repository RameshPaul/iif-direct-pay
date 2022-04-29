import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReceipientRecurringDetailComponent } from './receipient-recurring-detail.component';

describe('ReceipientRecurring Management Detail Component', () => {
  let comp: ReceipientRecurringDetailComponent;
  let fixture: ComponentFixture<ReceipientRecurringDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReceipientRecurringDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ receipientRecurring: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ReceipientRecurringDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReceipientRecurringDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load receipientRecurring on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.receipientRecurring).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
