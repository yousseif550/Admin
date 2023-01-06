import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';

export interface IProjet {
  id: string;
  nom?: string | null;
  dP?: string | null;
  stucture?: string | null;
  informations?: string | null;
  collaborateurs?: Pick<ICollaborateurs, 'id'>[] | null;
}

export type NewProjet = Omit<IProjet, 'id'> & { id: null };
