import dayjs from 'dayjs/esm';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { ILocalisation } from 'app/entities/localisation/localisation.model';

export interface IMouvement {
  id: string;
  date?: dayjs.Dayjs | null;
  type?: string | null;
  source?: string | null;
  destination?: string | null;
  user?: string | null;
  commentaire?: string | null;
  materiel?: Pick<IMateriel, 'id' | 'asset'> | null;
  localisation?: Pick<ILocalisation, 'id' | 'batiment'> | null;
}

export type NewMouvement = Omit<IMouvement, 'id'> & { id: null };
