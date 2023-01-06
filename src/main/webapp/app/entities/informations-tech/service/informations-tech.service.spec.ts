import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IInformationsTech } from '../informations-tech.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../informations-tech.test-samples';

import { InformationsTechService } from './informations-tech.service';

const requireRestSample: IInformationsTech = {
  ...sampleWithRequiredData,
};

describe('InformationsTech Service', () => {
  let service: InformationsTechService;
  let httpMock: HttpTestingController;
  let expectedResult: IInformationsTech | IInformationsTech[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InformationsTechService);
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

    it('should create a InformationsTech', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const informationsTech = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(informationsTech).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InformationsTech', () => {
      const informationsTech = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(informationsTech).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a InformationsTech', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of InformationsTech', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a InformationsTech', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInformationsTechToCollectionIfMissing', () => {
      it('should add a InformationsTech to an empty array', () => {
        const informationsTech: IInformationsTech = sampleWithRequiredData;
        expectedResult = service.addInformationsTechToCollectionIfMissing([], informationsTech);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(informationsTech);
      });

      it('should not add a InformationsTech to an array that contains it', () => {
        const informationsTech: IInformationsTech = sampleWithRequiredData;
        const informationsTechCollection: IInformationsTech[] = [
          {
            ...informationsTech,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInformationsTechToCollectionIfMissing(informationsTechCollection, informationsTech);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InformationsTech to an array that doesn't contain it", () => {
        const informationsTech: IInformationsTech = sampleWithRequiredData;
        const informationsTechCollection: IInformationsTech[] = [sampleWithPartialData];
        expectedResult = service.addInformationsTechToCollectionIfMissing(informationsTechCollection, informationsTech);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(informationsTech);
      });

      it('should add only unique InformationsTech to an array', () => {
        const informationsTechArray: IInformationsTech[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const informationsTechCollection: IInformationsTech[] = [sampleWithRequiredData];
        expectedResult = service.addInformationsTechToCollectionIfMissing(informationsTechCollection, ...informationsTechArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const informationsTech: IInformationsTech = sampleWithRequiredData;
        const informationsTech2: IInformationsTech = sampleWithPartialData;
        expectedResult = service.addInformationsTechToCollectionIfMissing([], informationsTech, informationsTech2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(informationsTech);
        expect(expectedResult).toContain(informationsTech2);
      });

      it('should accept null and undefined values', () => {
        const informationsTech: IInformationsTech = sampleWithRequiredData;
        expectedResult = service.addInformationsTechToCollectionIfMissing([], null, informationsTech, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(informationsTech);
      });

      it('should return initial array if no InformationsTech is added', () => {
        const informationsTechCollection: IInformationsTech[] = [sampleWithRequiredData];
        expectedResult = service.addInformationsTechToCollectionIfMissing(informationsTechCollection, undefined, null);
        expect(expectedResult).toEqual(informationsTechCollection);
      });
    });

    describe('compareInformationsTech', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInformationsTech(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareInformationsTech(entity1, entity2);
        const compareResult2 = service.compareInformationsTech(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareInformationsTech(entity1, entity2);
        const compareResult2 = service.compareInformationsTech(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareInformationsTech(entity1, entity2);
        const compareResult2 = service.compareInformationsTech(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
