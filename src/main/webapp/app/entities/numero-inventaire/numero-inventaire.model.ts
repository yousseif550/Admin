import dayjs from 'dayjs/esm';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';

export interface INumeroInventaire {
  id: string;
  disponible?: boolean | null;
  dateModification?: dayjs.Dayjs | null;
  commentaire?: string | null;
  materielActuel?: Pick<IMateriel, 'id'> | null;
  ancienMateriel?: Pick<IMateriel, 'id'> | null;
  ancienProprietaire?: Pick<ICollaborateurs, 'id'> | null;
  nouveauProprietaire?: Pick<ICollaborateurs, 'id'> | null;
}

export type NewNumeroInventaire = Omit<INumeroInventaire, 'id'> & { id: null };
