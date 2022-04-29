import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserOrganizationDetailComponent } from './user-organization-detail.component';

describe('UserOrganization Management Detail Component', () => {
  let comp: UserOrganizationDetailComponent;
  let fixture: ComponentFixture<UserOrganizationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserOrganizationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ userOrganization: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UserOrganizationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UserOrganizationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load userOrganization on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.userOrganization).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
