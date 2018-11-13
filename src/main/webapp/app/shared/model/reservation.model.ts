import { Moment } from 'moment';

export interface IReservation {
    id?: number;
    comment?: string;
    reserverDate?: Moment;
    updatedDate?: Moment;
    status?: string;
    startTime?: string;
    endTime?: string;
    staffId?: number;
    bookTableId?: number;
    customerId?: number;
}

export class Reservation implements IReservation {
    constructor(
        public id?: number,
        public comment?: string,
        public reserverDate?: Moment,
        public updatedDate?: Moment,
        public status?: string,
        public startTime?: string,
        public endTime?: string,
        public staffId?: number,
        public bookTableId?: number,
        public customerId?: number
    ) {}
}
