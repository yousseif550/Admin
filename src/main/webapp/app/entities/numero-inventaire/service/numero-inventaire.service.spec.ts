import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { INumeroInventaire } from '../numero-inventaire.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../numero-inventaire.test-samples';

import { NumeroInventaireService, RestNumeroInventaire } from './numero-inventaire.service';

const requireRestSample: RestNumeroInventaire = {
  ...sampleWithRequiredData,
  dateModification: sampleWithRequiredData.dateModification?.format(DATE_FORMAT),
};

describe('NumeroInventaire Service', () => {
  let service: NumeroInventaireService;
  let httpMock: HttpTestingController;
  let expectedResult: INumeroInventaire | INumeroInventaire[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NumeroInventaireService);
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

    it('should create a NumeroInventaire', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const numeroInventaire = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(numeroInventaire).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NumeroInventaire', () => {
      const numeroInventaire = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(numeroInventaire).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NumeroInventaire', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NumeroInventaire', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NumeroInventaire', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNumeroInventaireToCollectionIfMissing', () => {
      it('should add a NumeroInventaire to an empty array', () => {
        const numeroInventaire: INumeroInventaire = sampleWithRequiredData;
        expectedResult = service.addNumeroInventaireToCollectionIfMissing([], numeroInventaire);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(numeroInventaire);
      });

      it('should not add a NumeroInventaire to an array that contains it', () => {
        const numeroInventaire: INumeroInventaire = sampleWithRequiredData;
        const numeroInventaireCollection: INumeroInventaire[] = [
          {
            ...numeroInventaire,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNumeroInventaireToCollectionIfMissing(numeroInventaireCollection, numeroInventaire);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NumeroInventaire to an array that doesn't contain it", () => {
        const numeroInventaire: INumeroInventaire = sampleWithRequiredData;
        const numeroInventaireCollection: INumeroInventaire[] = [sampleWithPartialData];
        expectedResult = service.addNumeroInventaireToCollectionIfMissing(numeroInventaireCollection, numeroInventaire);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(numeroInventaire);
      });

      it('should add only unique NumeroInventaire to an array', () => {
        const numeroInventaireArray: INumeroInventaire[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const numeroInventaireCollection: INumeroInventaire[] = [sampleWithRequiredData];
        expectedResult = service.addNumeroInventaireToCollectionIfMissing(numeroInventaireCollection, ...numeroInventaireArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const numeroInventaire: INumeroInventaire = sampleWithRequiredData;
        const numeroInventaire2: INumeroInventaire = sampleWithPartialData;
        expectedResult = service.addNumeroInventaireToCollectionIfMissing([], numeroInventaire, numeroInventaire2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(numeroInventaire);
        expect(expectedResult).toContain(numeroInventaire2);
      });

      it('should accept null and undefined values', () => {
        const numeroInventaire: INumeroInventaire = sampleWithRequiredData;
        expectedResult = service.addNumeroInventaireToCollectionIfMissing([], null, numeroInventaire, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(numeroInventaire);
      });

      it('should return initial array if no NumeroInventaire is added', () => {
        const numeroInventaireCollection: INumeroInventaire[] = [sampleWithRequiredData];
        expectedResult = service.addNumeroInventaireToCollectionIfMissing(numeroInventaireCollection, undefined, null);
        expect(expectedResult).toEqual(numeroInventaireCollection);
      });
    });

    describe('compareNumeroInventaire', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNumeroInventaire(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareNumeroInventaire(entity1, entity2);
        const compareResult2 = service.compareNumeroInventaire(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareNumeroInventaire(entity1, entity2);
        const compareResult2 = service.compareNumeroInventaire(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareNumeroInventaire(entity1, entity2);
        const compareResult2 = service.compareNumeroInventaire(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
