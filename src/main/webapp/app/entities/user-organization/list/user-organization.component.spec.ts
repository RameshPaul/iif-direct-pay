import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { UserOrganizationService } from '../service/user-organization.service';

import { UserOrganizationComponent } from './user-organization.component';

describe('UserOrganization Management Component', () => {
  let comp: UserOrganizationComponent;
  let fixture: ComponentFixture<UserOrganizationComponent>;
  let service: UserOrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [UserOrganizationComponent],
    })
      .overrideTemplate(UserOrganizationComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserOrganizationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(UserOrganizationService);

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
    expect(comp.userOrganizations?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
