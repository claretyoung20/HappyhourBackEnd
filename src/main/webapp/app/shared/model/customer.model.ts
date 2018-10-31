import { Moment } from 'moment';

export interface ICustomer {
    id?: number;
    address?: string;
    dateCreated?: Moment;
    dateUpdated?: Moment;
    dateOfBirth?: Moment;
    email?: string;
    firstName?: string;
    imgUrl?: string;
    lastName?: string;
    password?: string;
    phoneNumber?: string;
    resetDate?: Moment;
    reset_Key?: string;
    status?: boolean;
    restaurantId?: number;
}

export class Customer implements ICustomer {
    constructor(
        public id?: number,
        public address?: string,
        public dateCreated?: Moment,
        public dateUpdated?: Moment,
        public dateOfBirth?: Moment,
        public email?: string,
        public firstName?: string,
        public imgUrl?: string,
        public lastName?: string,
        public password?: string,
        public phoneNumber?: string,
        public resetDate?: Moment,
        public reset_Key?: string,
        public status?: boolean,
        public restaurantId?: number
    ) {
        this.status = this.status || false;
    }
}
