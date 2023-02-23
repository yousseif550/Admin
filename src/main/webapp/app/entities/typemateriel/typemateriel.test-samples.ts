import { ITypemateriel, NewTypemateriel } from './typemateriel.model';

export const sampleWithRequiredData: ITypemateriel = {
  id: '20513e28-9019-42a2-af5a-7a18709b6c97',
};

export const sampleWithPartialData: ITypemateriel = {
  id: '5dbbb76a-a892-41da-a640-27021526faa6',
};

export const sampleWithFullData: ITypemateriel = {
  id: '58c5f9e9-8465-4bdf-a25b-d5bd8950b2d2',
  type: 'SQL Hawaii microchip',
};

export const sampleWithNewData: NewTypemateriel = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
