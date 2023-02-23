export interface ITypemateriel {
  id: string;
  type?: string | null;
}

export type NewTypemateriel = Omit<ITypemateriel, 'id'> & { id: null };
