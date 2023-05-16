import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';

export interface IProjet {
  id: string;
  nom?: string | null;
  stucture?: string | null;
  informations?: string | null;
  cp?: Pick<ICollaborateurs, 'id' | 'nom'> | null;
  dp?: Pick<ICollaborateurs, 'id' | 'nom'> | null;
  collaborateurs?: Pick<ICollaborateurs, 'id'>[] | null;
}

export type NewProjet = Omit<IProjet, 'id'> & { id: null };
