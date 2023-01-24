import dayjs from 'dayjs/esm';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';

export interface IMateriel {
  id: string;
  type?: string | null;
  modele?: string | null;
  asset?: string | null;
  actif?: boolean | null;
  dateAttribution?: dayjs.Dayjs | null;
  dateRendu?: dayjs.Dayjs | null;
  commentaire?: string | null;
  isHs?: boolean | null;
  localisation?: Pick<ILocalisation, 'id'> | null;
  collaborateurs?: Pick<ICollaborateurs, 'id'> | null;
}

export type NewMateriel = Omit<IMateriel, 'id'> & { id: null };
