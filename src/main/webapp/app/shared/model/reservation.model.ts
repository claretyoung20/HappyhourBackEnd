import { Moment } from 'moment';

export interface IReservation {
    id?: number;
    comment?: string;
    status?: string;
    reserverDate?: Moment;
    updatedDate?: Moment;
    period?: string;
    staffId?: number;
    bookTableId?: number;
    customerId?: number;
}

export class Reservation implements IReservation {
    constructor(
        public id?: number,
        public comment?: string,
        public status?: string,
        public reserverDate?: Moment,
        public updatedDate?: Moment,
        public period?: string,
        public staffId?: number,
        public bookTableId?: number,
        public customerId?: number
    ) {}
}
