import dayjs from 'dayjs/esm';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { IMateriel } from 'app/entities/materiel/materiel.model';

export interface IHistorique {
  id: string;
  pc?: string | null;
  zone?: string | null;
  dateMouvement?: dayjs.Dayjs | null;
  ancienProprietaire?: Pick<ICollaborateurs, 'id' | 'nom'> | null;
  nouveauProprietaire?: Pick<ICollaborateurs, 'id' | 'nom'> | null;
  materiel?: Pick<IMateriel, 'id' | 'asset'> | null;
}

export type NewHistorique = Omit<IHistorique, 'id'> & { id: null };
