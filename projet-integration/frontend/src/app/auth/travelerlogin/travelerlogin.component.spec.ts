import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TravelerloginComponent } from './travelerlogin.component';

describe('TravelerloginComponent', () => {
  let component: TravelerloginComponent;
  let fixture: ComponentFixture<TravelerloginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TravelerloginComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TravelerloginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
