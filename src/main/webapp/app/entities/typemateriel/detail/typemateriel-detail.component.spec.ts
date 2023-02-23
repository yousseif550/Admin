import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TypematerielDetailComponent } from './typemateriel-detail.component';

describe('Typemateriel Management Detail Component', () => {
  let comp: TypematerielDetailComponent;
  let fixture: ComponentFixture<TypematerielDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TypematerielDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ typemateriel: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(TypematerielDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TypematerielDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load typemateriel on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.typemateriel).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
