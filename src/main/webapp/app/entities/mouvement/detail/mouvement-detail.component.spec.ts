import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MouvementDetailComponent } from './mouvement-detail.component';

describe('Mouvement Management Detail Component', () => {
  let comp: MouvementDetailComponent;
  let fixture: ComponentFixture<MouvementDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MouvementDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ mouvement: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(MouvementDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MouvementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mouvement on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.mouvement).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
