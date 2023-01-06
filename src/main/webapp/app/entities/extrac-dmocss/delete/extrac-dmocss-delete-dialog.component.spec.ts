jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ExtracDMOCSSService } from '../service/extrac-dmocss.service';

import { ExtracDMOCSSDeleteDialogComponent } from './extrac-dmocss-delete-dialog.component';

describe('ExtracDMOCSS Management Delete Component', () => {
  let comp: ExtracDMOCSSDeleteDialogComponent;
  let fixture: ComponentFixture<ExtracDMOCSSDeleteDialogComponent>;
  let service: ExtracDMOCSSService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ExtracDMOCSSDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(ExtracDMOCSSDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ExtracDMOCSSDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ExtracDMOCSSService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete('ABC');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('ABC');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
