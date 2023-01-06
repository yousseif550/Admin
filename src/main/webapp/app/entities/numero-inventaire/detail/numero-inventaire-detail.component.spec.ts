import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NumeroInventaireDetailComponent } from './numero-inventaire-detail.component';

describe('NumeroInventaire Management Detail Component', () => {
  let comp: NumeroInventaireDetailComponent;
  let fixture: ComponentFixture<NumeroInventaireDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NumeroInventaireDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ numeroInventaire: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(NumeroInventaireDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NumeroInventaireDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load numeroInventaire on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.numeroInventaire).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
