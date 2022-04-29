import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserLoginDetailComponent } from './user-login-detail.component';

describe('UserLogin Management Detail Component', () => {
  let comp: UserLoginDetailComponent;
  let fixture: ComponentFixture<UserLoginDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserLoginDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userLogin: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserLoginDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserLoginDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userLogin on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userLogin).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
