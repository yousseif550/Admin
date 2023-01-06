import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ExtracDMOCSSDetailComponent } from './extrac-dmocss-detail.component';

describe('ExtracDMOCSS Management Detail Component', () => {
  let comp: ExtracDMOCSSDetailComponent;
  let fixture: ComponentFixture<ExtracDMOCSSDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExtracDMOCSSDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ extracDMOCSS: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ExtracDMOCSSDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ExtracDMOCSSDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load extracDMOCSS on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.extracDMOCSS).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
