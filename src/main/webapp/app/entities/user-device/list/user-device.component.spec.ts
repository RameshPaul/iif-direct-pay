import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { UserDeviceService } from '../service/user-device.service';

import { UserDeviceComponent } from './user-device.component';

describe('UserDevice Management Component', () => {
  let comp: UserDeviceComponent;
  let fixture: ComponentFixture<UserDeviceComponent>;
  let service: UserDeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [UserDeviceComponent],
    })
      .overrideTemplate(UserDeviceComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UserDeviceComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(UserDeviceService);

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
    expect(comp.userDevices?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
