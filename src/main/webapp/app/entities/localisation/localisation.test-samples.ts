import { ILocalisation, NewLocalisation } from './localisation.model';

export const sampleWithRequiredData: ILocalisation = {
  id: 'ad0543c7-c339-4562-a9ce-484bd4ba9b56',
};

export const sampleWithPartialData: ILocalisation = {
  id: '4d1321bc-6140-4bc1-a4bd-66499df78604',
  bureau: 'help-desk Christmas',
  ville: 'Implementation structure',
};

export const sampleWithFullData: ILocalisation = {
  id: 'b7274ebd-7092-4c37-9dff-32390ce75d98',
  batiment: 'redundant navigating',
  bureau: 'Iowa calculating',
  site: 'Practical',
  ville: 'mission-critical Borders Pike',
};

export const sampleWithNewData: NewLocalisation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
