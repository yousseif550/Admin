import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CollaborateursDetailComponent } from './collaborateurs-detail.component';

describe('Collaborateurs Management Detail Component', () => {
  let comp: CollaborateursDetailComponent;
  let fixture: ComponentFixture<CollaborateursDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CollaborateursDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ collaborateurs: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(CollaborateursDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CollaborateursDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load collaborateurs on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.collaborateurs).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
