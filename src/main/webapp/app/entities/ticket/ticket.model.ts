import dayjs from 'dayjs/esm';
import { ICollaborateurs } from 'app/entities/collaborateurs/collaborateurs.model';
import { Etat } from 'app/entities/enumerations/etat.model';

export interface ITicket {
  id: string;
  type?: string | null;
  statut?: Etat | null;
  dateCreation?: dayjs.Dayjs | null;
  dateFin?: dayjs.Dayjs | null;
  beneficiaire?: Pick<ICollaborateurs, 'id'> | null;
  proprietaire?: Pick<ICollaborateurs, 'id'> | null;
}

export type NewTicket = Omit<ITicket, 'id'> & { id: null };
