import dayjs from 'dayjs/esm';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { IMateriel } from 'app/entities/materiel/materiel.model';

export interface IHistorique {
  id: string;
  pC?: string | null;
  zone?: string | null;
  dateMouvement?: dayjs.Dayjs | null;
  ancienProprietaire?: Pick<ICollaborateurs, 'id'> | null;
  nouveauProprietaire?: Pick<ICollaborateurs, 'id'> | null;
  materiel?: Pick<IMateriel, 'id'> | null;
}

export type NewHistorique = Omit<IHistorique, 'id'> & { id: null };
