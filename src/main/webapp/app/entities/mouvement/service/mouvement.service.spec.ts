import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IMouvement } from '../mouvement.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../mouvement.test-samples';

import { MouvementService, RestMouvement } from './mouvement.service';

const requireRestSample: RestMouvement = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.format(DATE_FORMAT),
};

describe('Mouvement Service', () => {
  let service: MouvementService;
  let httpMock: HttpTestingController;
  let expectedResult: IMouvement | IMouvement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MouvementService);
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

    it('should create a Mouvement', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const mouvement = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(mouvement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Mouvement', () => {
      const mouvement = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(mouvement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Mouvement', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Mouvement', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Mouvement', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMouvementToCollectionIfMissing', () => {
      it('should add a Mouvement to an empty array', () => {
        const mouvement: IMouvement = sampleWithRequiredData;
        expectedResult = service.addMouvementToCollectionIfMissing([], mouvement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mouvement);
      });

      it('should not add a Mouvement to an array that contains it', () => {
        const mouvement: IMouvement = sampleWithRequiredData;
        const mouvementCollection: IMouvement[] = [
          {
            ...mouvement,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMouvementToCollectionIfMissing(mouvementCollection, mouvement);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Mouvement to an array that doesn't contain it", () => {
        const mouvement: IMouvement = sampleWithRequiredData;
        const mouvementCollection: IMouvement[] = [sampleWithPartialData];
        expectedResult = service.addMouvementToCollectionIfMissing(mouvementCollection, mouvement);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mouvement);
      });

      it('should add only unique Mouvement to an array', () => {
        const mouvementArray: IMouvement[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const mouvementCollection: IMouvement[] = [sampleWithRequiredData];
        expectedResult = service.addMouvementToCollectionIfMissing(mouvementCollection, ...mouvementArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mouvement: IMouvement = sampleWithRequiredData;
        const mouvement2: IMouvement = sampleWithPartialData;
        expectedResult = service.addMouvementToCollectionIfMissing([], mouvement, mouvement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mouvement);
        expect(expectedResult).toContain(mouvement2);
      });

      it('should accept null and undefined values', () => {
        const mouvement: IMouvement = sampleWithRequiredData;
        expectedResult = service.addMouvementToCollectionIfMissing([], null, mouvement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mouvement);
      });

      it('should return initial array if no Mouvement is added', () => {
        const mouvementCollection: IMouvement[] = [sampleWithRequiredData];
        expectedResult = service.addMouvementToCollectionIfMissing(mouvementCollection, undefined, null);
        expect(expectedResult).toEqual(mouvementCollection);
      });
    });

    describe('compareMouvement', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMouvement(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareMouvement(entity1, entity2);
        const compareResult2 = service.compareMouvement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareMouvement(entity1, entity2);
        const compareResult2 = service.compareMouvement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareMouvement(entity1, entity2);
        const compareResult2 = service.compareMouvement(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
