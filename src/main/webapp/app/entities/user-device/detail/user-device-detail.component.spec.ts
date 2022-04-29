import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserDeviceDetailComponent } from './user-device-detail.component';

describe('UserDevice Management Detail Component', () => {
  let comp: UserDeviceDetailComponent;
  let fixture: ComponentFixture<UserDeviceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserDeviceDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userDevice: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserDeviceDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserDeviceDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userDevice on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userDevice).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
