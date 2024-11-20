import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgencyhomeComponent } from './agencyhome.component';

describe('AgencyhomeComponent', () => {
  let component: AgencyhomeComponent;
  let fixture: ComponentFixture<AgencyhomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgencyhomeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AgencyhomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
