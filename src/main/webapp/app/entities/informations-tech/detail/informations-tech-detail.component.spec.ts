import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InformationsTechDetailComponent } from './informations-tech-detail.component';

describe('InformationsTech Management Detail Component', () => {
  let comp: InformationsTechDetailComponent;
  let fixture: ComponentFixture<InformationsTechDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InformationsTechDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ informationsTech: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(InformationsTechDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InformationsTechDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load informationsTech on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.informationsTech).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
