import { Moment } from 'moment';

export interface IHappyOrder {
    id?: number;
    baseTotal?: number;
    dateCreated?: Moment;
    dateUpdated?: Moment;
    totalPrice?: number;
    orderStatusId?: number;
    customerId?: number;
    couponId?: number;
    restaurantId?: number;
    staffStaffCode?: string;
    staffId?: number;
}

export class HappyOrder implements IHappyOrder {
    constructor(
        public id?: number,
        public baseTotal?: number,
        public dateCreated?: Moment,
        public dateUpdated?: Moment,
        public totalPrice?: number,
        public orderStatusId?: number,
        public customerId?: number,
        public couponId?: number,
        public restaurantId?: number,
        public staffStaffCode?: string,
        public staffId?: number
    ) {}
}
