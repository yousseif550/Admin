import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';

export interface IInformationsTech {
  id: string;
  pcDom?: string | null;
  pcDGFiP?: string | null;
  adresseSSG?: string | null;
  adresseDGFiP?: string | null;
  collaborateur?: Pick<ICollaborateurs, 'id'> | null;
}

export type NewInformationsTech = Omit<IInformationsTech, 'id'> & { id: null };
