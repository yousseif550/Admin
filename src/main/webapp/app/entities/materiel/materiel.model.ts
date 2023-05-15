import dayjs from 'dayjs/esm';
import { ITypemateriel } from 'app/entities/typemateriel/typemateriel.model';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';

export interface IMateriel {
  id: string;
  utilisation?: string | null;
  modele?: string | null;
  asset?: string | null;
  actif?: boolean | null;
  dateAttribution?: dayjs.Dayjs | null;
  dateRendu?: dayjs.Dayjs | null;
  commentaire?: string | null;
  isHs?: boolean | null;
  objet?: Pick<ITypemateriel, 'id' | 'type'> | null;
  localisation?: Pick<ILocalisation, 'id' | 'batiment'> | null;
  collaborateur?: Pick<ICollaborateurs, 'id' | 'nom'> | null;
}

export type NewMateriel = Omit<IMateriel, 'id'> & { id: null };
