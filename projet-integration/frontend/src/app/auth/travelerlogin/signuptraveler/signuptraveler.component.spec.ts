import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignuptravelerComponent } from './signuptraveler.component';

describe('SignuptravelerComponent', () => {
  let component: SignuptravelerComponent;
  let fixture: ComponentFixture<SignuptravelerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SignuptravelerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SignuptravelerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
