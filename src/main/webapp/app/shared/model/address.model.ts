export interface IAddress {
    id?: number;
    city?: string;
    street?: string;
    country?: string;
    restaurantId?: number;
}

export class Address implements IAddress {
    constructor(public id?: number, public city?: string, public street?: string, public country?: string, public restaurantId?: number) {}
}
