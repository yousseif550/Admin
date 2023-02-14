import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';

export interface IProjet {
  id: string;
  nom?: string | null;
  stucture?: string | null;
  informations?: string | null;
  cP?: Pick<ICollaborateurs, 'id'> | null;
  dP?: Pick<ICollaborateurs, 'id'> | null;
  collaborateurs?: Pick<ICollaborateurs, 'id'>[] | null;
}

export type NewProjet = Omit<IProjet, 'id'> & { id: null };
