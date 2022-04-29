import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { UserLoginService } from '../service/user-login.service';

import { UserLoginComponent } from './user-login.component';

describe('UserLogin Management Component', () => {
  let comp: UserLoginComponent;
  let fixture: ComponentFixture<UserLoginComponent>;
  let service: UserLoginService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [UserLoginComponent],
    })
      .overrideTemplate(UserLoginComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserLoginComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(UserLoginService);

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
    expect(comp.userLogins?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
