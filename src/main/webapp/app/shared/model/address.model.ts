export interface IAddress {
    id?: number;
    city?: string;
    street?: string;
    country?: string;
    supportEmail?: string;
    contactNumber?: string;
    restaurantId?: number;
}

export class Address implements IAddress {
    constructor(
        public id?: number,
        public city?: string,
        public street?: string,
        public country?: string,
        public supportEmail?: string,
        public contactNumber?: string,
        public restaurantId?: number
    ) {}
}
