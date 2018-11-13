import { Moment } from 'moment';

export interface IRating {
    id?: number;
    comment?: string;
    dateCreated?: Moment;
    dateUpdated?: Moment;
    star?: number;
}

export class Rating implements IRating {
    constructor(
        public id?: number,
        public comment?: string,
        public dateCreated?: Moment,
        public dateUpdated?: Moment,
        public star?: number
    ) {}
}
