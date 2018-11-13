import { Moment } from 'moment';

export interface IContactUs {
    id?: number;
    fullName?: string;
    dateCreated?: Moment;
    email?: string;
    phoneNumber?: string;
    comment?: string;
    restaurantId?: number;
}

export class ContactUs implements IContactUs {
    constructor(
        public id?: number,
        public fullName?: string,
        public dateCreated?: Moment,
        public email?: string,
        public phoneNumber?: string,
        public comment?: string,
        public restaurantId?: number
    ) {}
}
