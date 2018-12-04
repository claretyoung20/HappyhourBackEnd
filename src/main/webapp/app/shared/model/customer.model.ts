import { Moment } from 'moment';

export interface ICustomer {
    id?: number;
    address?: string;
    dateCreated?: Moment;
    dateUpdated?: Moment;
    dateOfBirth?: Moment;
    phoneNumber?: string;
    status?: boolean;
    restaurantId?: number;
    userLogin?: string;
    userId?: number;
}

export class Customer implements ICustomer {
    constructor(
        public id?: number,
        public address?: string,
        public dateCreated?: Moment,
        public dateUpdated?: Moment,
        public dateOfBirth?: Moment,
        public phoneNumber?: string,
        public status?: boolean,
        public restaurantId?: number,
        public userLogin?: string,
        public userId?: number
    ) {
        this.status = this.status || false;
    }
}
