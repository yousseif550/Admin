import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IExtracDMOCSS } from '../extrac-dmocss.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../extrac-dmocss.test-samples';

import { ExtracDMOCSSService, RestExtracDMOCSS } from './extrac-dmocss.service';

const requireRestSample: RestExtracDMOCSS = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.format(DATE_FORMAT),
};

describe('ExtracDMOCSS Service', () => {
  let service: ExtracDMOCSSService;
  let httpMock: HttpTestingController;
  let expectedResult: IExtracDMOCSS | IExtracDMOCSS[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ExtracDMOCSSService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ExtracDMOCSS', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const extracDMOCSS = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(extracDMOCSS).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ExtracDMOCSS', () => {
      const extracDMOCSS = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(extracDMOCSS).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ExtracDMOCSS', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ExtracDMOCSS', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ExtracDMOCSS', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addExtracDMOCSSToCollectionIfMissing', () => {
      it('should add a ExtracDMOCSS to an empty array', () => {
        const extracDMOCSS: IExtracDMOCSS = sampleWithRequiredData;
        expectedResult = service.addExtracDMOCSSToCollectionIfMissing([], extracDMOCSS);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(extracDMOCSS);
      });

      it('should not add a ExtracDMOCSS to an array that contains it', () => {
        const extracDMOCSS: IExtracDMOCSS = sampleWithRequiredData;
        const extracDMOCSSCollection: IExtracDMOCSS[] = [
          {
            ...extracDMOCSS,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addExtracDMOCSSToCollectionIfMissing(extracDMOCSSCollection, extracDMOCSS);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ExtracDMOCSS to an array that doesn't contain it", () => {
        const extracDMOCSS: IExtracDMOCSS = sampleWithRequiredData;
        const extracDMOCSSCollection: IExtracDMOCSS[] = [sampleWithPartialData];
        expectedResult = service.addExtracDMOCSSToCollectionIfMissing(extracDMOCSSCollection, extracDMOCSS);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(extracDMOCSS);
      });

      it('should add only unique ExtracDMOCSS to an array', () => {
        const extracDMOCSSArray: IExtracDMOCSS[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const extracDMOCSSCollection: IExtracDMOCSS[] = [sampleWithRequiredData];
        expectedResult = service.addExtracDMOCSSToCollectionIfMissing(extracDMOCSSCollection, ...extracDMOCSSArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const extracDMOCSS: IExtracDMOCSS = sampleWithRequiredData;
        const extracDMOCSS2: IExtracDMOCSS = sampleWithPartialData;
        expectedResult = service.addExtracDMOCSSToCollectionIfMissing([], extracDMOCSS, extracDMOCSS2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(extracDMOCSS);
        expect(expectedResult).toContain(extracDMOCSS2);
      });

      it('should accept null and undefined values', () => {
        const extracDMOCSS: IExtracDMOCSS = sampleWithRequiredData;
        expectedResult = service.addExtracDMOCSSToCollectionIfMissing([], null, extracDMOCSS, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(extracDMOCSS);
      });

      it('should return initial array if no ExtracDMOCSS is added', () => {
        const extracDMOCSSCollection: IExtracDMOCSS[] = [sampleWithRequiredData];
        expectedResult = service.addExtracDMOCSSToCollectionIfMissing(extracDMOCSSCollection, undefined, null);
        expect(expectedResult).toEqual(extracDMOCSSCollection);
      });
    });

    describe('compareExtracDMOCSS', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareExtracDMOCSS(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareExtracDMOCSS(entity1, entity2);
        const compareResult2 = service.compareExtracDMOCSS(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareExtracDMOCSS(entity1, entity2);
        const compareResult2 = service.compareExtracDMOCSS(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareExtracDMOCSS(entity1, entity2);
        const compareResult2 = service.compareExtracDMOCSS(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
