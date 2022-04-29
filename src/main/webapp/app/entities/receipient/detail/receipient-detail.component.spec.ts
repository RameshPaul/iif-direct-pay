import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReceipientDetailComponent } from './receipient-detail.component';

describe('Receipient Management Detail Component', () => {
  let comp: ReceipientDetailComponent;
  let fixture: ComponentFixture<ReceipientDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReceipientDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ receipient: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ReceipientDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ReceipientDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load receipient on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.receipient).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
