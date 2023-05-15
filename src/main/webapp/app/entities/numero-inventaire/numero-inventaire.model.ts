import dayjs from 'dayjs/esm';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';

export interface INumeroInventaire {
  id: string;
  disponible?: boolean | null;
  dateModification?: dayjs.Dayjs | null;
  commentaire?: string | null;
  materielActuel?: Pick<IMateriel, 'id' | 'asset'> | null;
  ancienMateriel?: Pick<IMateriel, 'id' | 'asset'> | null;
  ancienProprietaire?: Pick<ICollaborateurs, 'id' | 'nom'> | null;
  nouveauProprietaire?: Pick<ICollaborateurs, 'id' | 'nom'> | null;
}

export type NewNumeroInventaire = Omit<INumeroInventaire, 'id'> & { id: null };
