import { Moment } from 'moment';

export interface IReservation {
    id?: number;
    comment?: string;
    status?: string;
    startTime?: string;
    endTime?: string;
    reserverDate?: Moment;
    updatedDate?: Moment;
    staffId?: number;
    bookTableId?: number;
    customerId?: number;
}

export class Reservation implements IReservation {
    constructor(
        public id?: number,
        public comment?: string,
        public status?: string,
        public startTime?: string,
        public endTime?: string,
        public reserverDate?: Moment,
        public updatedDate?: Moment,
        public staffId?: number,
        public bookTableId?: number,
        public customerId?: number
    ) {}
}
