import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenreListComponent } from './genre-list';

describe('GenreList', () => {
  let component: GenreListComponent;
  let fixture: ComponentFixture<GenreListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenreListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GenreListComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
