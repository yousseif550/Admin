import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SuiviDetailComponent } from './suivi-detail.component';

describe('Suivi Management Detail Component', () => {
  let comp: SuiviDetailComponent;
  let fixture: ComponentFixture<SuiviDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SuiviDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ suivi: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(SuiviDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SuiviDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load suivi on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.suivi).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
