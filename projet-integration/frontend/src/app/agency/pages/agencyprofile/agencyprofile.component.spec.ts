import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgencyprofileComponent } from './agencyprofile.component';

describe('AgencyprofileComponent', () => {
  let component: AgencyprofileComponent;
  let fixture: ComponentFixture<AgencyprofileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgencyprofileComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AgencyprofileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
