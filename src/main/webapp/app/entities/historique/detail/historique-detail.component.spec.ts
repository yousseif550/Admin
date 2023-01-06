import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HistoriqueDetailComponent } from './historique-detail.component';

describe('Historique Management Detail Component', () => {
  let comp: HistoriqueDetailComponent;
  let fixture: ComponentFixture<HistoriqueDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HistoriqueDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ historique: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(HistoriqueDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HistoriqueDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load historique on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.historique).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
